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
import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordTransferEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.jwt.api.exception.JwtException;
import cn.stylefeng.roses.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import cn.stylefeng.roses.kernel.log.api.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.message.api.expander.WebSocketConfigExpander;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.security.api.CaptchaApi;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.api.expander.SystemConfigExpander;
import cn.stylefeng.roses.kernel.system.api.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.AUTH_EXPIRED_ERROR;
import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.TOKEN_PARSE_ERROR;

/**
 * 认证服务的实现
 *
 * @author fengshuonan
 * @date 2020/10/20 10:25
 */
@Service
public class AuthServiceImpl implements AuthServiceApi {

    /**
     * 用于操作缓存时候加锁
     */
    private static final Object SESSION_OPERATE_LOCK = new Object();

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private PasswordTransferEncryptApi passwordTransferEncryptApi;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Resource
    private CaptchaApi captchaApi;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return loginAction(loginRequest, true);
    }

    @Override
    public LoginResponse loginWithUserName(String username) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount(username);
        return loginAction(loginRequest, false);
    }

    @Override
    public void logout() {
        String token = LoginContext.me().getToken();
        //退出日志
        if (StrUtil.isNotEmpty(token)) {
            loginLogServiceApi.loginOutSuccess(LoginContext.me().getLoginUser().getUserId());
        }
        logoutWithToken(token);
        sessionManagerApi.destroySessionCookie();

    }

    @Override
    public void logoutWithToken(String token) {
        // 清除token缓存的用户信息
        sessionManagerApi.removeSession(token);
    }

    @Override
    public void validateToken(String token) throws AuthException {
        try {
            // 1. 先校验jwt token本身是否有问题
            JwtContext.me().validateTokenWithException(token);

            // 2. 判断session里是否有这个token
            LoginUser session = sessionManagerApi.getSession(token);
            if (session == null) {
                throw new AuthException(AUTH_EXPIRED_ERROR);
            }
        } catch (JwtException jwtException) {
            // jwt token本身过期的话，返回 AUTH_EXPIRED_ERROR
            if (JwtExceptionEnum.JWT_EXPIRED_ERROR.getErrorCode().equals(jwtException.getErrorCode())) {
                throw new AuthException(AUTH_EXPIRED_ERROR);
            } else {
                // 其他情况为返回jwt解析错误
                throw new AuthException(TOKEN_PARSE_ERROR);
            }
        } catch (io.jsonwebtoken.JwtException jwtSelfException) {
            // 其他jwt解析错误
            throw new AuthException(TOKEN_PARSE_ERROR);
        }
    }

    @Override
    public void checkAuth(String token, String requestUrl) {

        // 1. 校验token是否传参
        if (StrUtil.isEmpty(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }

        // 2. 校验用户token是否正确，校验失败会抛出异常
        this.validateToken(token);

    }

    /**
     * 登录的真正业务逻辑
     *
     * @param loginRequest     登录参数
     * @param validatePassword 是否校验密码，true-校验密码，false-不会校验密码
     * @author fengshuonan
     * @date 2020/10/21 16:59
     */
    private LoginResponse loginAction(LoginRequest loginRequest, Boolean validatePassword) {

        // 1.参数为空校验
        if (validatePassword) {
            if (loginRequest == null || StrUtil.hasBlank(loginRequest.getAccount(), loginRequest.getPassword())) {
                throw new AuthException(AuthExceptionEnum.PARAM_EMPTY);
            }
        } else {
            if (loginRequest == null || StrUtil.hasBlank(loginRequest.getAccount())) {
                throw new AuthException(AuthExceptionEnum.ACCOUNT_IS_BLANK);
            }
        }

        // 2. 如果开启了验证码校验，则验证当前请求的验证码是否正确
        if (SystemConfigExpander.getCaptchaOpen()) {
            String verKey = loginRequest.getVerKey();
            String verCode = loginRequest.getVerCode();

            if (StrUtil.isEmpty(verKey) || StrUtil.isEmpty(verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!captchaApi.validateCaptcha(verKey, verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_ERROR);
            }
        }

        // 3. 解密密码的密文
        //        String decryptPassword = passwordTransferEncryptApi.decrypt(loginRequest.getPassword());

        // 4. 获取用户密码的加密值和用户的状态
        UserLoginInfoDTO userValidateInfo = userServiceApi.getUserLoginInfo(loginRequest.getAccount());

        // 5. 校验用户密码是否正确
        if (validatePassword) {
            Boolean checkResult = passwordStoredEncryptApi.checkPassword(loginRequest.getPassword(), userValidateInfo.getUserPasswordHexed());
            if (!checkResult) {
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        }

        // 6. 校验用户是否异常（不是正常状态）
        if (!UserStatusEnum.ENABLE.getCode().equals(userValidateInfo.getUserStatus())) {
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR, UserStatusEnum.getCodeMessage(userValidateInfo.getUserStatus()));
        }

        // 7. 获取LoginUser，用于用户的缓存
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 8. 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(), loginRequest.getRememberMe());
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        synchronized (SESSION_OPERATE_LOCK) {

            // 8.1 获取ws-url 保存到用户信息中
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 9. 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser, loginRequest.getCreateCookie());

            // 10. 如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        // 11. 更新用户登录时间和ip
        String ip = HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest());
        userServiceApi.updateUserLoginInfo(loginUser.getUserId(), new Date(), ip);

        // 12.登录成功日志
        loginLogServiceApi.loginSuccess(loginUser.getUserId());

        // 13. 组装返回结果
        return new LoginResponse(loginUser, jwtToken, defaultJwtPayload.getExpirationDate());
    }

}
