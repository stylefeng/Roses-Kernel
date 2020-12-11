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
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查name参数", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "名称存在重复，请检查name参数",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "name")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空，请检查code参数", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "编码存在重复，请检查code参数",
            groups = {add.class, edit.class},
            tableName = "sys_app",
            columnName = "code")
    private String code;

    /**
     * 是否默认激活（Y-是，N-否）,只能有一个系统默认激活
     * 用户登录后默认展示此系统菜单
     */
    @NotBlank(message = "是否默认激活不能为空，请检查active参数", groups = {add.class, edit.class})
    @FlagValue(message = "是否默认激活格式错误，正确格式应该Y或者N，请检查active参数", groups = {add.class, edit.class})
    private String activeFlag;

}
