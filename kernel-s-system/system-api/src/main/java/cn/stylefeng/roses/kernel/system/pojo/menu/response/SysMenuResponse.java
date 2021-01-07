package cn.stylefeng.roses.kernel.system.pojo.menu.response;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 系统菜单
 *
 * @author majianguo
 * @date 2021/1/7 15:18
 */
@Data
public class SysMenuResponse implements AbstractTreeNode {

    /**
     * 主键
     */
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    private Long menuParentId;

    /**
     * 父id集合，中括号包住，逗号分隔
     */
    private String menuPids;

    /**
     * 菜单的名称
     */
    private String menuName;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 是否可见：Y-是，N-否
     */
    private String visible;

    /**
     * 路由地址，浏览器显示的URL，例如/menu，适用于antd vue版本
     */
    private String antdvRouter;

    /**
     * 前端组件名，适用于antd vue版本
     */
    private String antdvComponent;

    /**
     * 图标，适用于antd vue版本
     */
    private String antdvIcon;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链，适用于antd vue版本
     */
    private Integer antdvLinkOpenType;

    /**
     * 外部链接地址
     */
    private String antdvLinkUrl;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    private List children;

    /**
     * 菜单可以被那些角色访问
     */
    private List<SimpleRoleInfo> roles;

    @Override
    public String getNodeId() {
        return menuId.toString();
    }

    @Override
    public String getNodeParentId() {
        return menuParentId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }
}
