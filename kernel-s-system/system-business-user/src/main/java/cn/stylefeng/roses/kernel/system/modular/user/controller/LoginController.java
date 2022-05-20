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

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.AuthServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginWithTokenRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.login.CurrentUserInfoResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.login.ValidateTokenRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexUserInfoV3;
import cn.stylefeng.roses.kernel.system.modular.user.factory.UserLoginInfoFactory;
import cn.stylefeng.roses.kernel.system.modular.user.service.IndexUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private IndexUserInfoService indexUserInfoService;

    @Resource(name = "caClientTokenCacheApi")
    private CacheOperatorApi<String> caClientTokenCacheApi;

    /**
     * 用户登陆
     *
     * @author fengshuonan
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆", path = "/login", requiredLogin = false, requiredPermission = false)
    public ResponseData<String> login(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(true);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData<>(loginResponse.getToken());
    }

    /**
     * 用户登陆(提供给分离版用的接口，不会写cookie)
     *
     * @author fengshuonan
     * @date 2021/3/17 17:23
     */
    @PostResource(name = "登陆（分离版）", path = "/loginApi", requiredLogin = false, requiredPermission = false)
    public ResponseData<LoginResponse> loginApi(@RequestBody @Validated LoginRequest loginRequest) {
        loginRequest.setCreateCookie(false);
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData<>(loginResponse);
    }

    /**
     * 基于token登录，适用于单点登录，将caToken请求过来，进行解析，并创建本系统可以识别的token
     *
     * @author fengshuonan
     * @date 2021/5/25 22:36
     */
    @PostResource(name = "适用于单点登录", path = "/loginWithToken", requiredLogin = false, requiredPermission = false)
    public ResponseData<String> loginWithToken(@RequestBody @Validated LoginWithTokenRequest loginWithTokenRequest) {
        LoginResponse loginResponse = authServiceApi.LoginWithToken(loginWithTokenRequest);
        return new SuccessResponseData<>(loginResponse.getToken());
    }

    /**
     * 单点退出，基于CaClientToken的单点退出
     *
     * @param caClientToken token是单点登录回调本系统时候的token
     * @author fengshuonan
     * @date 2021/3/17 17:24
     */
    @ApiResource(name = "单点退出", path = "/logoutByCaClientToken", requiredLogin = false, requiredPermission = false, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData<?> ssoLogout(@RequestParam("caClientToken") String caClientToken) {

        // 获取CaClientToken对应的本地用户
        String currentSystemToken = caClientTokenCacheApi.get(caClientToken);

        if (StrUtil.isNotBlank(currentSystemToken)) {
            // 移除本系统中token
            authServiceApi.logoutWithToken(currentSystemToken);
            caClientTokenCacheApi.remove(caClientToken);
        }

        return new SuccessResponseData<>();
    }

    /**
     * 用户登出
     *
     * @author fengshuonan
     * @date 2021/3/17 17:24
     */
    @ApiResource(name = "登出", path = "/logoutAction", requiredPermission = false, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData<?> logoutAction() {
        authServiceApi.logout();
        return new SuccessResponseData<>();
    }

    /**
     * 获取当前用户的用户信息
     *
     * @author fengshuonan
     * @date 2021/3/17 17:37
     */
    @GetResource(name = "获取当前用户的用户信息", path = "/getCurrentLoginUserInfo", requiredPermission = false)
    public ResponseData<CurrentUserInfoResponse> getCurrentLoginUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 转化返回结果
        CurrentUserInfoResponse currentUserInfoResponse = UserLoginInfoFactory.parseUserInfo(loginUser);

        return new SuccessResponseData<>(currentUserInfoResponse);
    }

    /**
     * 校验token是否正确
     *
     * @author fengshuonan
     * @date 2021/6/18 15:26
     */
    @PostResource(name = "校验token是否正确", path = "/validateToken", requiredPermission = false, requiredLogin = false)
    public ResponseData<Boolean> validateToken(@RequestBody @Valid ValidateTokenRequest validateTokenRequest) {
        boolean haveSessionFlag = sessionManagerApi.haveSession(validateTokenRequest.getToken());
        return new SuccessResponseData<>(haveSessionFlag);
    }

    /**
     * 取消帐号冻结
     *
     * @author xixiaowei
     * @date 2022/1/22 16:40
     */
    @PostResource(name = "取消帐号冻结", path = "/cancelFreeze")
    public ResponseData<?> cancelFreeze(@RequestBody LoginRequest loginRequest) {
        authServiceApi.cancelFreeze(loginRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 新版Antdv3版本的用户信息获取
     *
     * @author fengshuonan
     * @date 2022/4/8 15:31
     */
    @GetResource(name = "新版Antdv3版本的用户信息获取", path = "/v3/userInfo", requiredPermission = false)
    public ResponseData<IndexUserInfoV3> userInfoV3(Integer menuFrontType) {
        return new SuccessResponseData<>(indexUserInfoService.userInfoV3(menuFrontType));
    }

}
