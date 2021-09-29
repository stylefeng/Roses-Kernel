package cn.stylefeng.roses.kernel.auth.api.loginuser;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前登录用户的相关方法
 *
 * @author fengshuonan
 * @date 2021/9/28 17:46
 */
public class CommonLoginUserUtil {

    /**
     * 获取当前登录用户Token
     *
     * @author fengshuonan
     * @date 2021/9/28 17:46
     */
    public static String getToken() {

        // 获取当前http请求
        HttpServletRequest request = HttpServletUtil.getRequest();

        // 1. 优先从param参数中获取token
        String parameterToken = request.getParameter(AuthConfigExpander.getAuthTokenParamName());

        // 不为空则直接返回param的token
        if (StrUtil.isNotBlank(parameterToken)) {
            return parameterToken;
        }

        // 2. 从header中获取token
        String authToken = request.getHeader(AuthConfigExpander.getAuthTokenHeaderName());
        if (StrUtil.isNotBlank(authToken)) {
            return authToken;
        }

        // 3. 从cookie中获取token
        String sessionCookieName = AuthConfigExpander.getSessionCookieName();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {

                // 如果cookie有对应的值，并且不为空
                if (sessionCookieName.equals(cookie.getName())
                        && StrUtil.isNotBlank(cookie.getValue())) {
                    return cookie.getValue();
                }
            }
        }

        // 获取不到token，直接告诉用户
        throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
    }

}
