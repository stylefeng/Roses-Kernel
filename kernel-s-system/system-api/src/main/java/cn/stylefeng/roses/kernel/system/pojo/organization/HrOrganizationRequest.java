package cn.stylefeng.roses.kernel.system.pojo.organization;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.status.StatusValue;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 系统组织机构表
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HrOrganizationRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    private Long orgId;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空", groups = {add.class, edit.class})
    private Long orgParentId;

    /**
     * 父ids
     */
    @NotBlank(message = "父ids不能为空", groups = {add.class, edit.class})
    private String orgPids;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空", groups = {add.class, edit.class})
    private String orgName;

    /**
     * 组织编码
     */
    @NotBlank(message = "组织编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "组织编码存在重复，请检查code参数",
            groups = {add.class, edit.class},
            tableName = "sys_organization",
            columnName = "org_code")
    private String orgCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal orgSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    private Integer statusFlag;

    /**
     * 描述
     */
    private String remark;

}
