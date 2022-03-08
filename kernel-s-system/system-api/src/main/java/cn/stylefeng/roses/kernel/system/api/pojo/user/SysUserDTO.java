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
package cn.stylefeng.roses.kernel.system.api.pojo.user;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 系统用户结果
 *
 * @author fengshuonan
 * @date 2020/4/2 9:19
 */
@Data
public class SysUserDTO {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long userId;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    private String account;

    /**
     * 昵称
     */
    @ChineseDescription("昵称")
    private String nickName;

    /**
     * 姓名
     */
    @ChineseDescription("姓名")
    private String realName;

    /**
     * 头像
     */
    @ChineseDescription("头像")
    private Long avatar;

    /**
     * 生日
     */
    @ChineseDescription("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（M-男，F-女）
     */
    @ChineseDescription("性别（M-男，F-女）")
    private String sex;

    /**
     * 邮箱
     */
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 手机
     */
    @ChineseDescription("手机")
    private String phone;

    /**
     * 密码
     */
    @ChineseDescription("密码")
    private String password;

    /**
     * 电话
     */
    @ChineseDescription("电话")
    private String tel;

    /**
     * 用户所属机构
     */
    @ChineseDescription("用户所属机构")
    private Long orgId;

    /**
     * 用户所属机构名称
     */
    @ChineseDescription("用户所属机构名称")
    private String orgName;

    /**
     * 用户所属机构的职务
     */
    @ChineseDescription("用户所属机构的职务")
    private Long positionId;

    /**
     * 职务名称
     */
    @ChineseDescription("职务名称")
    private String positionName;

    /**
     * 状态
     */
    @ChineseDescription("状态")
    private Integer statusFlag;

    /**
     * 用户角色id
     */
    @ChineseDescription("用户角色id")
    private List<Long> grantRoleIdList;

    /**
     * 是否是超级管理员，超级管理员可以拥有所有权限（Y-是，N-否）
     */
    @ChineseDescription("是否是超级管理员，超级管理员可以拥有所有权限（Y-是，N-否）")
    private String superAdminFlag;

}
