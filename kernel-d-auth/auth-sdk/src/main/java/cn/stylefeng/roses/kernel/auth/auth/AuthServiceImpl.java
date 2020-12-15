package cn.stylefeng.roses.kernel.auth.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.jwt.api.exception.JwtException;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import cn.stylefeng.roses.kernel.system.ResourceServiceApi;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    private ResourceServiceApi resourceServiceApi;

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
        logoutWithToken(token);
    }

    @Override
    public void logoutWithToken(String token) {
        // 清除token缓存的用户信息
        sessionManagerApi.removeSession(token);
    }

    @Override
    public void validateToken(String token) throws AuthException {
        try {
            JwtContext.me().validateTokenWithException(token);
        } catch (JwtException e) {
            throw new AuthException(e.getErrorCode(), e.getUserTip());
        }
    }

    @Override
    public boolean getTokenFlag(String token) {
        return JwtContext.me().validateToken(token);
    }

    @Override
    public void checkAuth(String token, String requestUrl) {

        // 1. 获取url对应的资源信息ResourceDefinition
        ResourceUrlParam resourceUrlReq = new ResourceUrlParam();
        resourceUrlReq.setUrl(requestUrl);
        ResourceDefinition resourceDefinition = resourceServiceApi.getResourceByUrl(resourceUrlReq);

        // 2. 如果此接口不需要权限校验或者查询到资源为空，则放开过滤
        if (resourceDefinition == null || !resourceDefinition.getRequiredLogin()) {
            return;
        }

        // 3. 如果当前接口需要鉴权，则校验用户token是否正确，校验失败会抛出异常
        if (resourceDefinition.getRequiredLogin()) {
            this.validateToken(token);
        }

        // 4. 如果token校验通过，获取token的payload，以及是否开启了记住我功能
        DefaultJwtPayload defaultPayload = JwtContext.me().getDefaultPayload(token);
        Boolean rememberMe = defaultPayload.getRememberMe();

        // 5. 获取用户的当前会话信息
        LoginUser loginUser = sessionManagerApi.getSession(token);

        // 6. 如果开了记住我，但是会话为空，则创建一次会话信息
        if (rememberMe && loginUser == null) {
            UserLoginInfoDTO userLoginInfo = userServiceApi.getUserLoginInfo(defaultPayload.getAccount());
            sessionManagerApi.createSession(token, userLoginInfo.getLoginUser());
        }

        // 7. 如果会话信息为空，则判定此次校验失败
        if (loginUser == null) {
            throw new AuthException(AuthExceptionEnum.AUTH_ERROR);
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

        // 2.获取用户密码的加密值和用户的状态
        UserLoginInfoDTO userValidateInfo = userServiceApi.getUserLoginInfo(loginRequest.getAccount());

        // 3.校验用户密码是否正确(BCrypt算法)
        if (validatePassword) {
            if (ObjectUtil.isEmpty(userValidateInfo.getUserPasswordHexed()) || !BCrypt.checkpw(loginRequest.getPassword(), userValidateInfo.getUserPasswordHexed())) {
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        }

        // 4.校验用户是否异常（不是正常状态）
        if (!UserStatusEnum.ENABLE.getCode().equals(userValidateInfo.getUserStatus())) {
            String userTip = StrUtil.format(AuthExceptionEnum.USER_STATUS_ERROR.getErrorCode(), UserStatusEnum.getCodeMessage(userValidateInfo.getUserStatus()));
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR.getErrorCode(), userTip);
        }

        // 5.获取LoginUser，用于用户的缓存
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 6.生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getId(), loginUser.getAccount(), loginRequest.getRememberMe());
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);

        synchronized (SESSION_OPERATE_LOCK) {

            // 7.缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser);

            // 8.如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }

        }

        // 9.更新用户登录时间和ip
        String ip = HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest());
        userServiceApi.updateUserLoginInfo(loginUser.getId(), new Date(), ip);

        // 10.组装返回结果
        return new LoginResponse(loginUser, jwtToken);
    }

}
