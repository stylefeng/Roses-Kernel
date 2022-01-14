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

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 登录的请求参数
 *
 * @author fengshuonan
 * @date 2020/10/19 14:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequest extends BaseRequest {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @ChineseDescription("账号")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ChineseDescription("密码")
    private String password;

    /**
     * 记住我，不传就是false
     */
    @ChineseDescription("记住我，不传就是false")
    private Boolean rememberMe = false;

    /**
     * 验证码图形对应的缓存key
     */
    @ChineseDescription("验证码图形对应的缓存key")
    private String verKey;

    /**
     * 用户输入的验证码的值
     */
    @ChineseDescription("用户输入的验证码的值")
    private String verCode;

    /**
     * 是否写入cookie会话信息
     */
    @ChineseDescription("是否写入cookie会话信息")
    private Boolean createCookie = false;

    /**
     * 租户编码
     */
    @ChineseDescription("租户编码")
    private String tenantCode;

}
