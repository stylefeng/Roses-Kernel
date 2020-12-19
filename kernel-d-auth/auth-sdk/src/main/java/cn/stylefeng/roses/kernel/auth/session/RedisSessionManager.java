package cn.stylefeng.roses.kernel.auth.session;

import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;
import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_USERID_PREFIX;

/**
 * 基于redis的会话管理
 *
 * @author fengshuonan
 * @date 2019-09-28-14:43
 */
public class RedisSessionManager implements SessionManagerApi {

    /**
     * 登录用户缓存
     * <p>
     * key是 LOGGED_TOKEN_PREFIX + 用户的token
     */
    private final RedisTemplate<String, LoginUser> loginUserRedisTemplate;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     * <p>
     * 没开启单端限制的话，一个用户可能有多个token，因为一个用户可能在多个地点或打开多个浏览器访问系统
     * <p>
     * key是 LOGGED_USERID_PREFIX + 用户id
     * <p>
     * 这个缓存应该定时刷新下，因为有过期token的用户，所以这个里边的值set得清理
     */
    private final RedisTemplate<String, Set<String>> loginTokenRedisTemplate;

    /**
     * session的超时时间
     */
    private final Long sessionExpiredSeconds;

    public RedisSessionManager(RedisTemplate<String, LoginUser> loginUserRedisTemplate, RedisTemplate<String, Set<String>> loginTokenRedisTemplate, Long sessionExpiredSeconds) {
        this.loginUserRedisTemplate = loginUserRedisTemplate;
        this.loginTokenRedisTemplate = loginTokenRedisTemplate;
        this.sessionExpiredSeconds = sessionExpiredSeconds;
    }

    @Override
    public void createSession(String token, LoginUser loginUser) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

        // 装配用户信息的缓存
        loginUserRedisTemplate.opsForValue().set(getTokenKey(token), loginUser, sessionExpiredSeconds, TimeUnit.SECONDS);

        // 装配用户token的缓存
        String userIdKey = getUserIdKey(loginUser.getUserId());
        Set<String> theUserTokens = loginTokenRedisTemplate.opsForValue().get(userIdKey);
        if (theUserTokens == null) {
            HashSet<String> tempUserTokens = new HashSet<>();
            tempUserTokens.add(token);
            loginTokenRedisTemplate.opsForValue().set(userIdKey, tempUserTokens);
        } else {
            theUserTokens.add(token);
        }
    }

    @Override
    public LoginUser getSession(String token) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return loginUserRedisTemplate.opsForValue().get(getTokenKey(token));
    }

    @Override
    public void removeSession(String token) {

        String tokenKey = getTokenKey(token);
        LoginUser loginUser = loginUserRedisTemplate.opsForValue().get(tokenKey);

        // 删除用户id对应token的缓存
        if (loginUser != null) {
            Long userId = loginUser.getUserId();
            Set<String> userTokens = loginTokenRedisTemplate.opsForValue().get(getUserIdKey(userId));
            if (userTokens != null) {
                userTokens.remove(token);

                // 如果删除后size为0，则把整个key删掉
                if (userTokens.size() == 0) {
                    loginUserRedisTemplate.delete(getUserIdKey(userId));
                }
            }
        }

        // 删除用户信息的缓存
        loginUserRedisTemplate.delete(getTokenKey(token));
    }

    @Override
    public void removeSessionExcludeToken(String token) {

        // 获取token对应的会话
        LoginUser session = this.getSession(token);

        // 如果会话为空，直接返回
        if (session == null) {
            return;
        }

        // 获取用户id
        Long userId = session.getUserId();

        // 设置用户id对应的token列表为参数token
        HashSet<String> tokenSet = new HashSet<>();
        tokenSet.add(token);
        loginTokenRedisTemplate.opsForValue().set(getUserIdKey(userId), tokenSet);
    }

    @Override
    public boolean haveSession(String token) {
        Boolean flag = loginUserRedisTemplate.hasKey(getTokenKey(token));
        if (flag == null) {
            return false;
        } else {
            return flag;
        }
    }

    @Override
    public void refreshSession(String token) {
        LoginUser loginUser = loginUserRedisTemplate.boundValueOps(getTokenKey(token)).get();
        if (loginUser != null) {
            loginUserRedisTemplate.boundValueOps(getTokenKey(token)).expire(sessionExpiredSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取token的缓存key
     *
     * @author fengshuonan
     * @date 2020/10/21 15:09
     */
    private String getTokenKey(String token) {
        return LOGGED_TOKEN_PREFIX + token;
    }

    /**
     * 获取用户id的缓存key
     *
     * @author fengshuonan
     * @date 2020/10/21 15:10
     */
    private String getUserIdKey(Long userId) {
        return LOGGED_USERID_PREFIX + userId;
    }

}
