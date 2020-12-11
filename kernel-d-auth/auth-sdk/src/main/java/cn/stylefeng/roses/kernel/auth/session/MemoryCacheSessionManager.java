package cn.stylefeng.roses.kernel.auth.session;


import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;
import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_USERID_PREFIX;

/**
 * 基于内存的会话管理
 *
 * @author fengshuonan
 * @date 2019-09-28-14:43
 */
public class MemoryCacheSessionManager implements SessionManagerApi {

    /**
     * 登录用户缓存
     * <p>
     * key是 LOGGED_TOKEN_PREFIX + 用户的token
     */
    private final TimedCache<String, LoginUser> loginUserCache;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     * <p>
     * 没开启单端限制的话，一个用户可能有多个token，因为一个用户可能在多个地点或打开多个浏览器访问系统
     * <p>
     * key是 LOGGED_USERID_PREFIX + 用户id
     * <p>
     * 这个缓存应该定时刷新下，因为有过期token的用户，所以这个里边的值set得清理
     */
    private final Map<String, Set<String>> loginTokenCache = new HashMap<>();

    public MemoryCacheSessionManager(TimedCache<String, LoginUser> loginUserCache) {
        this.loginUserCache = loginUserCache;
    }

    @Override
    public void createSession(String token, LoginUser loginUser) {

        // 装配用户信息的缓存
        loginUserCache.put(getTokenKey(token), loginUser);

        // 装配用户token的缓存
        String userIdKey = getUserIdKey(loginUser.getId());
        Set<String> theUserTokens = loginTokenCache.get(userIdKey);
        if (theUserTokens == null) {
            HashSet<String> tempUserTokens = new HashSet<>();
            tempUserTokens.add(token);
            loginTokenCache.put(userIdKey, tempUserTokens);
        } else {
            theUserTokens.add(token);
        }
    }

    @Override
    public LoginUser getSession(String token) {
        return loginUserCache.get(getTokenKey(token));
    }

    @Override
    public void removeSession(String token) {

        String tokenKey = getTokenKey(token);
        LoginUser loginUser = loginUserCache.get(tokenKey);

        // 删除用户id对应token的缓存
        if (loginUser != null) {
            Long userId = loginUser.getId();
            Set<String> userTokens = loginTokenCache.get(getUserIdKey(userId));
            if (userTokens != null) {
                userTokens.remove(token);

                // 如果删除后size为0，则把整个key删掉
                if (userTokens.size() == 0) {
                    loginTokenCache.remove(getUserIdKey(userId));
                }
            }
        }

        // 删除用户信息的缓存
        loginUserCache.remove(getTokenKey(token));
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
        Long userId = session.getId();

        // 设置用户id对应的token列表为参数token
        HashSet<String> tokenSet = new HashSet<>();
        tokenSet.add(token);
        loginTokenCache.put(getUserIdKey(userId), tokenSet);
    }

    @Override
    public boolean haveSession(String token) {
        return loginUserCache.containsKey(getTokenKey(token));
    }

    @Override
    public void refreshSession(String token) {
        LoginUser loginUser = loginUserCache.get(getTokenKey(token));
        if (loginUser != null) {
            loginUserCache.put(LOGGED_TOKEN_PREFIX + token, loginUser, loginUserCache.timeout());
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
