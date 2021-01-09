package cn.stylefeng.roses.kernel.system.pojo.menu;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.flag.FlagValue;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 系统菜单参数
 *
 * @author fengshuonan
 * @date 2020/3/26 20:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "menuId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long menuId;

    /**
     * 父id
     */
    @NotNull(message = "menuParentId不能为空", groups = {add.class, edit.class})
    private Long menuParentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "菜单名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_menu",
            columnName = "menu_name",
            idFieldName = "menu_id",
            excludeLogicDeleteItems = true)
    private String menuName;

    /**
     * 菜单的编码
     */
    @NotBlank(message = "菜单的编码不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "菜单的编码不能为空",
            groups = {add.class, edit.class},
            tableName = "sys_menu",
            columnName = "menu_code",
            idFieldName = "menu_id",
            excludeLogicDeleteItems = true)
    private String menuCode;

    /**
     * 应用分类（应用编码）
     */
    @NotBlank(message = "appCode不能为空", groups = {add.class, edit.class, getAppMenusAntdVue.class})
    private String appCode;

    /**
     * 是否可见（Y-是，N-否）
     */
    @FlagValue(message = "是否可见格式错误，正确格式应该Y或者N，请检查visible参数", groups = {add.class, edit.class}, required = false)
    private String visible;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal menuSort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String router;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 外部链接打开方式：1-内置外链，2-新页面外链
     */
    @Min(value = 1, message = "打开方式格式错误，请检查openType参数", groups = {add.class, edit.class})
    @Max(value = 2, message = "打开方式格式错误，请检查openType参数", groups = {add.class, edit.class})
    private Integer linkOpenType;

    /**
     * 外部链接地址
     */
    private String linkUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单的路径，适用于layui-beetl版本
     */
    private String layuiPath;

    /**
     * 菜单的图标，适用于layui-beetl版本
     */
    private String layuiIcon;

    /**
     * 获取主页左侧菜单列表（适配Antd Vue的版本）
     */
    public @interface getAppMenusAntdVue {
    }

}
