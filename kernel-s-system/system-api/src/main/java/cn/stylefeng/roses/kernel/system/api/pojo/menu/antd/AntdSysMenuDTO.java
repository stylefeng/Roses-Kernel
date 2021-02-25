package cn.stylefeng.roses.kernel.system.api.pojo.menu.antd;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * 封装antd vue需要的菜单信息，service对外输出的对象
 *
 * @author majianguo
 * @date 2021/1/7 15:18
 */
@Data
public class AntdSysMenuDTO {

    /**
     * 主键
     */
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    private Long menuParentId;

    /**
     * 菜单的名称
     */
    private String menuName;

    /**
     * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
     */
    private String antdvRouter;

    /**
     * 图标，适用于antd vue版本
     */
    private String antdvIcon;

    /**
     * 是否显示，Y-显示，N-不显示
     */
    private String visible;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    private List children;

    /**
     * 菜单可以被那些角色访问
     */
    private List<SimpleRoleInfo> roles;

}
