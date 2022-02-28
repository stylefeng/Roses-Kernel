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
import cn.stylefeng.roses.kernel.rule.tree.xmtree.base.AbstractXmSelectNode;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统职位表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hr_position")
public class HrPosition extends BaseEntity implements AbstractXmSelectNode {

    /**
     * 主键
     */
    @TableId("position_id")
    @ChineseDescription("主键")
    private Long positionId;

    /**
     * 职位名称
     */
    @TableField("position_name")
    @ChineseDescription("职位名称")
    private String positionName;

    /**
     * 职位编码
     */
    @TableField("position_code")
    @ChineseDescription("职位编码")
    private String positionCode;

    /**
     * 排序
     */
    @TableField("position_sort")
    @ChineseDescription("排序")
    private BigDecimal positionSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 职位备注
     */
    @TableField("position_remark")
    @ChineseDescription("职位备注")
    private String positionRemark;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("删除标记：Y-已删除，N-未删除")
    private String delFlag;

    @Override
    public String getName() {
        return this.positionName;
    }

    @Override
    public String getValue() {
        return String.valueOf(positionId);
    }

    @Override
    public Boolean getSelected() {
        return false;
    }

    @Override
    public Boolean getDisabled() {
        return false;
    }

    @Override
    public List<?> getChildren() {
        return null;
    }
}
