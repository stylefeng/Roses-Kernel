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
import cn.stylefeng.roses.kernel.message.api.expander.WebSocketConfigExpander;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.system.LoginLogServiceApi;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.expander.SystemConfigExpander;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.validator.CaptchaApi;
import cn.stylefeng.roses.kernel.validator.exception.enums.ValidatorExceptionEnum;
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
        return loginAction(new LoginRequest(), false);
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

        // 3. 如果token校验通过，获取token的payload，以及是否开启了记住我功能
        DefaultJwtPayload defaultPayload = JwtContext.me().getDefaultPayload(token);
        Boolean rememberMe = defaultPayload.getRememberMe();

        // 4. 获取用户的当前会话信息
        LoginUser loginUser = sessionManagerApi.getSession(token);

        // 5. 如果开了记住我，但是会话为空，则创建一次会话信息
        if (rememberMe && loginUser == null) {
            UserLoginInfoDTO userLoginInfo = userServiceApi.getUserLoginInfo(defaultPayload.getAccount());
            sessionManagerApi.createSession(token, userLoginInfo.getLoginUser());
        }

        // 6. 如果会话信息为空，则判定此次校验失败
        if (loginUser == null) {
            throw new AuthException(AUTH_EXPIRED_ERROR);
        }

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

        synchronized (SESSION_OPERATE_LOCK) {

            // 8.1 获取ws-url 保存到用户信息中
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 9. 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser);

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
