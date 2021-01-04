package cn.stylefeng.roses.kernel.auth.api.cookie;

import javax.servlet.http.Cookie;

/**
 * cookie的创建器，用在session创建时，给httpServletResponse添加cookie
 * <p>
 * 每个公司情况不一样，所以预留拓展接口
 *
 * @author fengshuonan
 * @date 2020/12/27 13:28
 */
public abstract class SessionCookieCreator {

    /**
     * 创建cookie的操作
     * <p>
     * 这里不要重写这个方法，重写后名称对不上可能导致登录后权限校验失败
     *
     * @param cookieName            cookie的名称
     * @param cookieValue           cookie的值
     * @param sessionExpiredSeconds cookie过期时间
     * @author fengshuonan
     * @date 2020/12/27 13:29
     */
    public Cookie createCookie(String cookieName, String cookieValue, Integer sessionExpiredSeconds) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(sessionExpiredSeconds);
        this.expandCookieProp(cookie);
        return cookie;
    }

    /**
     * 拓展cookie的配置
     *
     * @author fengshuonan
     * @date 2020/12/27 13:41
     */
    public abstract void expandCookieProp(Cookie cookie);

}
