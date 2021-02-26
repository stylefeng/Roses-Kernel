package cn.stylefeng.roses.kernel.system.api.pojo.menu;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.flag.FlagValue;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @FlagValue(message = "是否可见格式错误，正确格式应该Y或者N", groups = {add.class, edit.class}, required = false)
    private String visible;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal menuSort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单的路径（适用于layui-beetl版本）
     */
    private String layuiPath;

    /**
     * 菜单的图标（适用于layui-beetl版本）
     */
    private String layuiIcon;

    /**
     * 路由地址，浏览器显示的URL，例如/menu（适用于antd vue版本）
     */
    private String antdvRouter;

    /**
     * 图标（适用于antd vue版本）
     */
    private String antdvIcon;

    /**
     * 前端组件名（适用于antd vue版本）
     */
    private String antdvComponent;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链（适用于antd vue版本）
     */
    private Integer antdvLinkOpenType;

    /**
     * 外部链接地址（适用于antd vue版本）
     */
    private String antdvLinkUrl;

    /**
     * 获取主页左侧菜单列表（适配Antd Vue的版本）
     */
    public @interface getAppMenusAntdVue {
    }

}
