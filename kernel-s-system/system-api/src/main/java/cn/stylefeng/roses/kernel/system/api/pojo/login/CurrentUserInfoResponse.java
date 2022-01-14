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
package cn.stylefeng.roses.kernel.system.api.pojo.login;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 登录人详细信息
 *
 * @author fengshuonan
 * @date 2021/3/22 21:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserInfoResponse {

    /**
     * 用户主键id
     */
    @ChineseDescription("用户主键id")
    private Long userId;

    /**
     * 公司/组织id
     */
    @ChineseDescription("公司/组织id")
    private Long organizationId;

    /**
     * 登录人的ws-url
     */
    @ChineseDescription("登录人的ws-url")
    private String wsUrl;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickname;

    /**
     * 用户姓名
     */
    @ChineseDescription("用户姓名")
    private String realName;

    /**
     * 用户头像（url）
     */
    @ChineseDescription("用户头像（url）")
    private String avatar;

    /**
     * 用户拥有的资源权限
     */
    @ChineseDescription("用户拥有的资源权限")
    private Set<String> authorities;

    /**
     * 用户拥有的角色编码
     */
    @ChineseDescription("用户拥有的角色编码")
    private Set<String> roles;

}
