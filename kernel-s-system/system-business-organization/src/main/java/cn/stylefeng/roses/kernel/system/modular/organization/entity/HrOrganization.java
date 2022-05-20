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
package cn.stylefeng.roses.kernel.system.modular.organization.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统组织机构表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hr_organization")
public class HrOrganization extends BaseEntity {

    /**
     * 主键
     */
    @TableId("org_id")
    @ChineseDescription("主键")
    private Long orgId;

    /**
     * 父id，一级节点父id是0
     */
    @TableField("org_parent_id")
    @ChineseDescription("父id，一级节点父id是0")
    private Long orgParentId;

    /**
     * 父ids
     */
    @TableField("org_pids")
    @ChineseDescription("父ids")
    private String orgPids;

    /**
     * 组织名称
     */
    @TableField("org_name")
    @ChineseDescription("组织名称")
    private String orgName;

    /**
     * 组织编码
     */
    @TableField("org_code")
    @ChineseDescription("组织编码")
    private String orgCode;

    /**
     * 排序
     */
    @TableField("org_sort")
    @ChineseDescription("排序")
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField(value = "status_flag",fill = FieldFill.INSERT)
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 组织机构类型：1-公司，2-部门
     */
    @TableField(value = "org_type")
    @ChineseDescription("组织机构类型：1-公司，2-部门")
    private Integer orgType;

    /**
     * 组织机构描述
     */
    @TableField("org_remark")
    @ChineseDescription("组织机构描述")
    private String orgRemark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    @ChineseDescription("删除标记（Y-已删除，N-未删除）")
    private String delFlag;

}
