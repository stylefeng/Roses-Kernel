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
package cn.stylefeng.roses.kernel.auth.api;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginRequest;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginWithTokenRequest;

/**
 * 认证服务的接口，包括基本的登录退出操作和校验token等操作
 *
 * @author fengshuonan
 * @date 2020/10/26 14:41
 */
public interface AuthServiceApi {

    /**
     * 常规登录操作
     *
     * @param loginRequest 登录的请求
     * @return token 一般为jwt token
     * @author fengshuonan
     * @date 2020/10/26 14:41
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 登录（直接用账号登录），一般用在第三方登录
     *
     * @param username 账号
     * @author fengshuonan
     * @date 2020/10/26 14:40
     */
    LoginResponse loginWithUserName(String username);

    /**
     * 登录（通过账号和sso后的token），一般用在单点登录
     *
     * @param username 账号
     * @param caToken  sso登录成功后的会话
     * @author fengshuonan
     * @date 2021/5/25 22:44
     */
    LoginResponse loginWithUserNameAndCaToken(String username, String caToken);

    /**
     * 通过token进行登录，一般用在单点登录服务
     *
     * @param loginWithTokenRequest 请求
     * @author fengshuonan
     * @date 2021/5/25 22:44
     */
    LoginResponse LoginWithToken(LoginWithTokenRequest loginWithTokenRequest);

    /**
     * 当前登录人退出登录
     *
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void logout();

    /**
     * 移除某个token，也就是退出某个用户
     *
     * @param token 某个用户的登录token
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void logoutWithToken(String token);

    /**
     * 校验jwt token的正确性，调用jwt工具类相关方法校验
     * <p>
     * 结果有三种，第一是jwt过期了，第二是用户随便写的错误token，第三种是token正确，token正确不会抛出异常
     *
     * @param token 某个用户的登录token
     * @throws AuthException 认证异常，如果token错误或过期，会有相关的异常抛出
     * @author fengshuonan
     * @date 2020/10/19 14:16
     */
    void validateToken(String token) throws AuthException;

    /**
     * 校验用户是否认证通过，认证是校验token的过程，校验失败会抛出异常
     *
     * @param token      用户登陆的token
     * @param requestUrl 被校验的url
     * @author fengshuonan
     * @date 2020/10/22 16:03
     */
    void checkAuth(String token, String requestUrl);

    /**
     * 取消冻结帐号
     *
     * @author xixiaowei
     * @date 2022/1/22 16:37
     */
    void cancelFreeze(LoginRequest loginRequest);
}
