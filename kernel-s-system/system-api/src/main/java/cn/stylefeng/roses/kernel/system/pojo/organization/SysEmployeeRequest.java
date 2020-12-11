package cn.stylefeng.roses.kernel.system.pojo.organization;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 企业员工表，用户-组织机构的关联
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEmployeeRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "主键id")
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = {add.class})
    private Long userId;

    /**
     * 所属机构id
     */
    @NotNull(message = "所属机构id不能为空", groups = {add.class})
    private Long organizationId;

    /**
     * 职位id集合，用逗号隔开
     */
    @NotBlank(message = "职位id集合，用逗号隔开不能为空", groups = add.class)
    private String positionIds;

    /**
     * 是否是主要部门，Y-是，N-否，一个人只能有一个主要部门
     */
    @NotBlank(message = "是否是主要部门，Y-是，N-否，一个人只能有一个主要部门不能为空", groups = {add.class})
    private String mainDeptFlag;

    /**
     * 员工编号
     */
    private String employeeNo;

    /**
     * 职位id，用在查询某个职位下的企业员工时候用
     */
    private Long positionId;

}
