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
package cn.stylefeng.roses.kernel.system.modular.user.controller;

import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginWithTokenRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.login.CurrentUserInfoResponse;
import cn.stylefeng.roses.kernel.system.modular.user.factory.UserLoginInfoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseData login(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(true);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData(loginResponse.getToken());
    }

    /**
     * 用户登陆(提供给分离版用的接口，不会写cookie)
     *
     * @author fengshuonan
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆（分离版）", path = "/loginApi", requiredLogin = false, requiredPermission = false)
    public ResponseData loginApi(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(false);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData(loginResponse);
    }

    /**
     * 基于token登录，适用于单点登录
     *
     * @author fengshuonan
     * @date 2021/5/25 22:36
     */
    @PostResource(name = "适用于单点登录", path = "/loginWithToken", requiredLogin = false, requiredPermission = false)
    public ResponseData loginWithToken(@RequestBody @Validated LoginWithTokenRequest loginWithTokenRequest) {
        LoginResponse loginResponse = authServiceApi.LoginWithToken(loginWithTokenRequest);
        return new SuccessResponseData(loginResponse.getToken());
    }

    /**
     * 用户登出
     *
     * @author fengshuonan
     * @date 2021/3/17 17:24
     */
    @ApiResource(name = "登出", path = "/logout", requiredPermission = false, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData logoutPage() {
        authServiceApi.logout();
        return new SuccessResponseData();
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

        // 转化返回结果
        CurrentUserInfoResponse currentUserInfoResponse = UserLoginInfoFactory.parseUserInfo(loginUser);

        return new SuccessResponseData(currentUserInfoResponse);
    }

}
