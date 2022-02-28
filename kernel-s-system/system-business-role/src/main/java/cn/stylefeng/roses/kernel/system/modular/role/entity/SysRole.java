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
package cn.stylefeng.roses.kernel.system.modular.role.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统角色表
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    @ChineseDescription("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("role_code")
    @ChineseDescription("角色编码")
    private String roleCode;

    /**
     * 排序
     */
    @TableField("role_sort")
    @ChineseDescription("排序")
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据
     */
    @TableField("data_scope_type")
    @ChineseDescription("数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据")
    private Integer dataScopeType;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    @ChineseDescription("备注")
    private String remark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    @ChineseDescription("删除标记（Y-已删除，N-未删除）")
    private String delFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    @TableField("role_system_flag")
    @ChineseDescription("是否是系统角色：Y-是，N-否")
    private String roleSystemFlag;

    /**
     * 角色类型
     */
    @TableField("role_type_code")
    @ChineseDescription("角色类型")
    private String roleTypeCode;

}
