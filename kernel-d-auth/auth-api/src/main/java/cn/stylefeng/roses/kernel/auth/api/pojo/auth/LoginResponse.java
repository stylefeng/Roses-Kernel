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
package cn.stylefeng.roses.kernel.auth.api.pojo.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

/**
 * 登录操作的响应结果
 *
 * @author fengshuonan
 * @date 2020/10/19 14:17
 */
@Data
public class LoginResponse {

    /**
     * 登录人的信息
     */
    @ChineseDescription("登录人的信息")
    private LoginUser loginUser;

    /**
     * 登录人的token
     */
    @ChineseDescription("登录人的token")
    private String token;

    /**
     * 到期时间
     */
    @ChineseDescription("到期时间")
    private Long expireAt;

    /**
     * 使用单点登录
     */
    @ChineseDescription("使用单点登录")
    private Boolean ssoLogin;

    /**
     * 单点登录的loginCode
     */
    @ChineseDescription("单点登录的loginCode")
    private String ssoLoginCode;

    /**
     * 用于普通登录的组装
     *
     * @author fengshuonan
     * @date 2021/5/25 22:31
     */
    public LoginResponse(LoginUser loginUser, String token, Long expireAt) {
        this.loginUser = uselessFilter(loginUser);
        this.token = token;
        this.expireAt = expireAt;
    }

    /**
     * 用于单点登录，返回用户loginCode
     *
     * @author fengshuonan
     * @date 2021/5/25 22:31
     */
    public LoginResponse(String loginCode) {
        this.ssoLogin = true;
        this.ssoLoginCode = loginCode;
    }

    /**
     * 过滤无用的用户信息返回给登录用户
     *
     * @author fengshuonan
     * @date 2021/7/13 11:23
     */
    private LoginUser uselessFilter(LoginUser loginUser) {
        LoginUser tempUser = new LoginUser();
        BeanUtil.copyProperties(loginUser, tempUser);

        // 过滤一些内容
        tempUser.setDataScopeTypeEnums(null);
        tempUser.setResourceUrls(null);
        tempUser.setToken(null);
        return tempUser;
    }

}
