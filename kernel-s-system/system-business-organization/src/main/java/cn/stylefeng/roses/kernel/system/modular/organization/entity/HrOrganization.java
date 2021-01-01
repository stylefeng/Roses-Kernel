package cn.stylefeng.roses.kernel.system.modular.organization.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Insert;

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
    private Long orgId;

    /**
     * 父id，一级节点父id是0
     */
    @TableField("org_parent_id")
    private Long orgParentId;

    /**
     * 父ids
     */
    @TableField("org_pids")
    private String orgPids;

    /**
     * 组织名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 组织编码
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 排序
     */
    @TableField("org_sort")
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 组织机构描述
     */
    @TableField("org_remark")
    private String orgRemark;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    private String delFlag;

}
