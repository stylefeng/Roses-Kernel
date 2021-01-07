package cn.stylefeng.roses.kernel.menu.modular.pojo;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 用于antdv的菜单响应
 *
 * @author fengshuonan
 * @date 2021/1/7 18:09
 */
@Data
public class AntdvMenuItem implements AbstractTreeNode {

    /**
     * 主键
     */
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    private Long menuParentId;

    /**
     * 路由信息
     */
    private String router;

    /**
     * 图标信息
     */
    private String icon;

    /**
     * 路径信息
     */
    private String path;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由信息
     */
    private AuthorityItem authority;

    /**
     * 子菜单集合
     */
    private List<AntdvMenuItem> children;

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
