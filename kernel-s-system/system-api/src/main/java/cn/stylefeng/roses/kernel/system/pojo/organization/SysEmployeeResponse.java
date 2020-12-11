package cn.stylefeng.roses.kernel.system.pojo.organization;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户员工信息
 *
 * @author fengshuonan
 * @date 2020/4/2 20:22
 */
@Data
public class SysEmployeeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 所属机构id
     */
    private Long organizationId;

    /**
     * 所属机构名称
     */
    private String organizationName;

    /**
     * 职位id集合，用逗号隔开
     */
    private String positionIds;

    /**
     * 职位id名称集合，用逗号隔开
     */
    private String positionNames;

    /**
     * 是否是主要部门，Y-是，N-否，一个人只能有一个主要部门
     */
    private String mainDeptFlag;

    /**
     * 员工编号
     */
    private String employeeNo;

}