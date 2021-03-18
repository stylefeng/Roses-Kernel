/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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

    @Override
    public boolean haveButton(String buttonCode) {
        LoginUser loginUser = getLoginUser();
        if (loginUser.getButtonCodes() == null) {
            return false;
        } else {
            return loginUser.getButtonCodes().contains(buttonCode);
        }
    }

}
