package cn.stylefeng.roses.kernel.system.api.pojo.organization.layui;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import cn.stylefeng.roses.kernel.rule.tree.xmtree.base.AbstractXmSelectNode;
import lombok.Data;

import java.util.List;

/**
 * Layui 机构树
 *
 * @author chenjinlong
 * @date 2020/12/27 18:36
 */
@Data
public class LayuiOrganizationTreeNode implements AbstractTreeNode, AbstractXmSelectNode {

    /**
     * 父id，一级节点父id是0
     */
    private Long parentId;

    /**
     * 节点名称
     */
    private String title;

    /**
     * 节点值
     */
    private Long id;

    /**
     * 是否展开状态 不展开-false 展开-true
     */
    private boolean spread = true;

    /**
     * 子节点的集合
     */
    private List<LayuiOrganizationTreeNode> children;

    @Override
    public String getNodeId() {
        return this.id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public Boolean getSelected() {
        return false;
    }

    @Override
    public Boolean getDisabled() {
        return false;
    }

    @Override
    public List getChildren() {
        return this.children;
    }

}
