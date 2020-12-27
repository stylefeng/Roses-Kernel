package cn.stylefeng.roses.kernel.auth.auth;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.LoginUserApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.TOKEN_GET_ERROR;

/**
 * 当前登陆用户的接口实现
 *
 * @author fengshuonan
 * @date 2020/10/21 18:09
 */
@Service
public class LoginUserImpl implements LoginUserApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public String getToken() {

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
        if (AuthConfigExpander.getSessionAddToCookie()) {
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
        }

        // 获取不到token，直接告诉用户
        throw new AuthException(TOKEN_GET_ERROR);
    }

    @Override
    public LoginUser getLoginUser() throws AuthException {

        // 获取用户的token
        String token = getToken();

        // 获取session中该token对应的用户
        LoginUser session = sessionManagerApi.getSession(token);

        // session为空抛出异常
        if (session == null) {
            throw new AuthException(TOKEN_GET_ERROR);
        }

        return session;
    }

    @Override
    public LoginUser getLoginUserNullable() {

        // 获取用户的token
        String token = null;
        try {
            token = getToken();
        } catch (Exception e) {
            return null;
        }

        // 获取session中该token对应的用户
        return sessionManagerApi.getSession(token);

    }

    @Override
    public boolean getSuperAdminFlag() {
        LoginUser loginUser = getLoginUser();
        return loginUser.getSuperAdmin();
    }

    @Override
    public boolean hasLogin() {

        // 获取用户的token
        String token = null;
        try {
            token = getToken();
        } catch (Exception e) {
            return false;
        }

        // 获取是否在会话中有
        return sessionManagerApi.haveSession(token);
    }

}
