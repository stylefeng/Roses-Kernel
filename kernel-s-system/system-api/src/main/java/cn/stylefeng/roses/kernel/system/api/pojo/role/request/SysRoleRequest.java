package cn.stylefeng.roses.kernel.system.api.pojo.role.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;

/**
 * 系统角色参数
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "roleId不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class, grantResource.class, grantDataScope.class, grantMenuButton.class})
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = {add.class, edit.class})
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "角色编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_role",
            columnName = "role_code",
            idFieldName = "role_id",
            excludeLogicDeleteItems = true)
    private String roleCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据
     */
    @Null(message = "数据范围类型应该为空", groups = {add.class, edit.class})
    @NotNull(message = "数据范围类型不能为空", groups = {grantDataScope.class})
    private Integer dataScopeType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典 1正常 2停用）
     */
    private Integer statusFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    private String roleSystemFlag;

    /**
     * 角色类型
     */
    private String roleTypeCode;

    /**
     * 授权资源
     */
    @NotNull(message = "授权资源不能为空", groups = {grantResource.class})
    private List<String> grantResourceList;

    /**
     * 授权数据
     */
    private List<Long> grantOrgIdList;

    /**
     * 授权菜单
     */
    @NotNull(message = "授权菜单Id不能为空", groups = {grantMenuButton.class})
    private List<Long> grantMenuIdList;

    /**
     * 授权菜单按钮
     */
    @NotNull(message = "授权菜单按钮Id不能为空", groups = {grantMenuButton.class})
    private List<SysRoleMenuButtonRequest> grantMenuButtonIdList;

    /**
     * 参数校验分组：授权菜单和按钮
     */
    public @interface grantMenuButton {

    }

    /**
     * 参数校验分组：授权资源
     */
    public @interface grantResource {

    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantDataScope {
    }

}
