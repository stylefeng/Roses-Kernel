package cn.stylefeng.roses.kernel.auth.session;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于redis的会话管理
 *
 * @author fengshuonan
 * @date 2019-09-28-14:43
 */
public class DefaultSessionManager implements SessionManagerApi {

    /**
     * 登录用户缓存
     * <p>
     * key是 LOGGED_TOKEN_PREFIX + 用户的token
     */
    private final CacheOperatorApi<LoginUser> loginUserCache;

    /**
     * 用户token的缓存，这个缓存用来存储一个用户的所有token
     * <p>
     * 没开启单端限制的话，一个用户可能有多个token，因为一个用户可能在多个地点或打开多个浏览器访问系统
     * <p>
     * key是 LOGGED_USERID_PREFIX + 用户id
     * <p>
     * 这个缓存应该定时刷新下，因为有过期token的用户，所以这个里边的值set得清理
     */
    private final CacheOperatorApi<Set<String>> allPlaceLoginTokenCache;

    /**
     * session的超时时间
     */
    private final Long sessionExpiredSeconds;

    /**
     * cookie的创建器，用在session创建时，给response添加cookie
     */
    private final SessionCookieCreator sessionCookieCreator;

    public DefaultSessionManager(CacheOperatorApi<LoginUser> loginUserCache,
                                 CacheOperatorApi<Set<String>> allPlaceLoginTokenCache,
                                 Long sessionExpiredSeconds,
                                 SessionCookieCreator sessionCookieCreator) {
        this.loginUserCache = loginUserCache;
        this.allPlaceLoginTokenCache = allPlaceLoginTokenCache;
        this.sessionExpiredSeconds = sessionExpiredSeconds;
        this.sessionCookieCreator = sessionCookieCreator;
    }

    @Override
    public void createSession(String token, LoginUser loginUser) {

        // 装配用户信息的缓存
        loginUserCache.put(token, loginUser, sessionExpiredSeconds);

        // 装配用户token的缓存
        Set<String> theUserTokens = allPlaceLoginTokenCache.get(loginUser.getUserId().toString());
        if (theUserTokens == null) {
            theUserTokens = new HashSet<>();
        }
        theUserTokens.add(token);
        allPlaceLoginTokenCache.put(loginUser.getUserId().toString(), theUserTokens);

        // 如果开启了cookie存储会话信息，则需要给HttpServletResponse添加一个cookie
        if (AuthConfigExpander.getSessionAddToCookie()) {
            String sessionCookieName = AuthConfigExpander.getSessionCookieName();
            Cookie cookie = sessionCookieCreator.createCookie(sessionCookieName, token, Convert.toInt(AuthConfigExpander.getAuthJwtTimeoutSeconds()));
            HttpServletResponse response = HttpServletUtil.getResponse();
            response.addCookie(cookie);
        }

    }

    @Override
    public LoginUser getSession(String token) {
        return loginUserCache.get(token);
    }

    @Override
    public void removeSession(String token) {

        LoginUser loginUser = loginUserCache.get(token);

        // 删除本token用户信息的缓存
        loginUserCache.remove(token);

        // 删除多端登录信息
        if (loginUser != null) {
            Long userId = loginUser.getUserId();
            Set<String> userTokens = allPlaceLoginTokenCache.get(userId.toString());
            if (userTokens != null) {

                // 清除对应的token的信息
                userTokens.remove(token);
                allPlaceLoginTokenCache.put(userId.toString(), userTokens);

                // 如果删除后size为0，则把整个key删掉
                if (userTokens.size() == 0) {
                    allPlaceLoginTokenCache.remove(userId.toString());
                }
            }
        }
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

        // 获取这个用户多余的几个登录信息
        Set<String> thisUserTokens = allPlaceLoginTokenCache.get(userId.toString());
        for (String thisUserToken : thisUserTokens) {
            if (!thisUserToken.equals(token)) {
                loginUserCache.remove(thisUserToken);
            }
        }

        // 设置用户id对应的token列表为参数token
        HashSet<String> tokenSet = new HashSet<>();
        tokenSet.add(token);
        allPlaceLoginTokenCache.put(userId.toString(), tokenSet);
    }

    @Override
    public boolean haveSession(String token) {
        return loginUserCache.contains(token);
    }

    @Override
    public void refreshSession(String token) {
        LoginUser loginUser = loginUserCache.get(token);
        if (loginUser != null) {
            loginUserCache.expire(token, sessionExpiredSeconds);
        }
    }

    @Override
    public void destroySessionCookie() {
        // 如果开启了cookie存储会话信息，则需要给HttpServletResponse添加一个cookie
        if (AuthConfigExpander.getSessionAddToCookie()) {
            String sessionCookieName = AuthConfigExpander.getSessionCookieName();
            Cookie cookie = sessionCookieCreator.createCookie(sessionCookieName, null, 0);
            HttpServletResponse response = HttpServletUtil.getResponse();
            response.addCookie(cookie);
        }
    }

}
