package cn.stylefeng.roses.kernel.system.modular.organization.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业员工表，用户-组织机构的关联
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_employee")
public class SysEmployee extends BaseEntity {

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 所属机构id
     */
    @TableField("organization_id")
    private Long organizationId;

    /**
     * 职位id集合，用逗号隔开
     */
    @TableField("position_ids")
    private String positionIds;

    /**
     * 是否是主要部门，Y-是，N-否，一个人只能有一个主要部门
     */
    @TableField("main_dept_flag")
    private String mainDeptFlag;

    /**
     * 员工编号
     */
    @TableField("employee_no")
    private String employeeNo;

}
