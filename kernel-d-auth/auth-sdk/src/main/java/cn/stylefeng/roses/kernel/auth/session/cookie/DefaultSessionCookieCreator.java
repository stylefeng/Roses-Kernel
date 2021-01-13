package cn.stylefeng.roses.kernel.auth.session.cookie;

import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;

import javax.servlet.http.Cookie;

/**
 * 默认的cookie创建
 * <p>
 * 这里预留了expandCookieProp的接口可以拓展cookie的属性
 *
 * @author fengshuonan
 * @date 2020/12/27 13:29
 */
public class DefaultSessionCookieCreator extends SessionCookieCreator {

    @Override
    public void expandCookieProp(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setPath("/");
    }

}
