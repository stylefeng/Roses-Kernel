package cn.stylefeng.roses.kernel.system.pojo.menu.antd;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * 系统菜单
 *
 * @author majianguo
 * @date 2021/1/7 15:18
 */
@Data
public class AntdSysMenuResponse {

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
     * 前端用路径，适用于antdvue版本
     */
    private String antdvPath;

    /**
     * 图标，适用于antd vue版本
     */
    private String antdvIcon;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    private List children;

    /**
     * 菜单可以被那些角色访问
     */
    private List<SimpleRoleInfo> roles;

}
