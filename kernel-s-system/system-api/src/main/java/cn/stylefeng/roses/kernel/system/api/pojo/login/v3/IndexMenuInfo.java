package cn.stylefeng.roses.kernel.system.api.pojo.login.v3;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 角色信息
 *
 * @author fengshuonan
 * @date 2022/4/8 15:06
 */
@Data
public class IndexMenuInfo {

    /**
     * 菜单id
     */
    @ChineseDescription("菜单id")
    private Long menuId;

    /**
     * 菜单父级id
     */
    @ChineseDescription("菜单父级id")
    private Long parentId;

    /**
     * 菜单的名称
     */
    @ChineseDescription("菜单的名称")
    private String title;

    /**
     * 路由地址(要以/开头)，必填
     */
    @ChineseDescription("路由地址(要以/开头)，必填")
    private String path;

    /**
     * 组件地址(组件要放在view目录下)，父级可以省略
     */
    @ChineseDescription("组件地址(组件要放在view目录下)，父级可以省略")
    private String component;

    /**
     * 菜单类型, 0菜单, 1按钮
     */
    @ChineseDescription("菜单类型, 0菜单, 1按钮")
    private Integer menuType;

    /**
     * 排序
     */
    @ChineseDescription("排序")
    private BigDecimal sortNumber;

    /**
     * 权限标识
     */
    @ChineseDescription("权限标识")
    private String authority;

    /**
     * 菜单的图标
     */
    @ChineseDescription("菜单的图标")
    private String icon;

    /**
     * 是否隐藏, 0否,1是(仅注册路由不显示左侧菜单)
     */
    @ChineseDescription("是否隐藏, 0否,1是(仅注册路由不显示左侧菜单)")
    private Boolean hide;

    /**
     * 路由元信息
     */
    @ChineseDescription("路由元信息")
    private String meta;

    /**
     * 子级
     */
    @ChineseDescription("子级")
    private List<IndexMenuInfo> children;

    /**
     * 配置选中的path地址，比如修改页面不在侧栏，打开后侧栏就没有选中，这个配置选中地址，非必须
     */
    @ChineseDescription("配置选中的path地址")
    private String active;

}
