package cn.stylefeng.roses.kernel.system.pojo.app.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.flag.FlagValue;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统应用参数
 *
 * @author fengshuonan
 * @date 2020/3/24 20:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAppRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "appId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long appId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "app_name",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    private String appName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "app_code",
            idFieldName = "app_id",
            excludeLogicDeleteItems = true)
    private String appCode;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @NotBlank(message = "是否默认激活不能为空", groups = {add.class, edit.class})
    @FlagValue(message = "是否默认激活格式错误，正确格式应该Y或者N", groups = {add.class, edit.class})
    private String activeFlag;

}
