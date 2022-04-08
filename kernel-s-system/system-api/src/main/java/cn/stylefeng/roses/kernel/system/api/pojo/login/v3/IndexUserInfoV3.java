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
package cn.stylefeng.roses.kernel.system.api.pojo.login.v3;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 最新Antd3版本的首页登录用户信息需要的数据
 *
 * @author fengshuonan
 * @date 2022/4/8 14:56
 */
@Data
public class IndexUserInfoV3 {

    /**
     * 用户主键id
     */
    @ChineseDescription("用户主键id")
    private Long userId;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    private String username;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickname;

    /**
     * 用户头像（url）
     */
    @ChineseDescription("用户头像（url）")
    private String avatar;

    /**
     * 性别（字典）
     */
    @ChineseDescription("性别（字典）")
    private String sex;

    /**
     * 电话
     */
    @ChineseDescription("电话")
    private String phone;

    /**
     * 邮箱
     */
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 出生日期
     */
    @ChineseDescription("出生日期")
    private Date birthday;

    /**
     * 组织机构id
     */
    @ChineseDescription("组织机构id")
    private Long organizationId;

    /**
     * 用户状态
     */
    @ChineseDescription("状态")
    private Integer status;

    /**
     * 性别名称
     */
    @ChineseDescription("性别名称")
    private String sexName;

    /**
     * 组织机构名称
     */
    @ChineseDescription("组织机构名称")
    private String organizationName;

    /**
     * 角色信息
     */
    @ChineseDescription("角色信息")
    private List<IndexRoleInfo> roles;

    /**
     * 权限信息
     */
    @ChineseDescription("权限信息")
    private List<IndexMenuInfo> authorities;

    /**
     * 登录人的ws-url，用来获取右上角的消息
     */
    @ChineseDescription("登录人的ws-url")
    private String wsUrl;

    /**
     * 权限标识编码
     */
    @ChineseDescription("权限标识编码")
    private Set<String> authCodes;

}
