package cn.stylefeng.roses.kernel.system.modular.user.controller;

import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.login.LoginDetailsResponse;
import cn.stylefeng.roses.kernel.system.modular.user.factory.LoginResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录登出控制器
 *
 * @author fengshuonan
 * @date 2021/3/17 17:23
 */
@RestController
@Slf4j
@ApiResource(name = "登陆登出管理")
public class LoginController {

    @Resource
    private AuthServiceApi authServiceApi;

    /**
     * 用户登陆
     *
     * @author fengshuonan
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆", path = "/login", requiredLogin = false, requiredPermission = false)
    public ResponseData doAuth(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData(loginResponse.getToken());
    }

    /**
     * 用户登出
     *
     * @author fengshuonan
     * @date 2021/3/17 17:24
     */
    @GetResource(name = "登出", path = "/logout", requiredPermission = false)
    public ResponseData logoutPage() {
        authServiceApi.logout();
        return new SuccessResponseData();
    }

    /**
     * 用户登陆并返回详情
     *
     * @author fengshuonan
     * @date 2021/3/17 17:24
     */
    @PostResource(name = "用户登陆并返回详情", path = "/loginWithDetail", requiredLogin = false, requiredPermission = false)
    public ResponseData loginWithDetail(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        LoginDetailsResponse loginDetail = LoginResponseFactory.createLoginDetail(loginResponse);
        return new SuccessResponseData(loginDetail);
    }

    /**
     * 获取当前用户的用户信息
     *
     * @author fengshuonan
     * @date 2021/3/17 17:37
     */
    @GetResource(name = "获取当前用户的用户信息", path = "/getCurrentLoginUserInfo", requiredPermission = false)
    public ResponseData getCurrentLoginUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return new SuccessResponseData(loginUser);
    }

}
