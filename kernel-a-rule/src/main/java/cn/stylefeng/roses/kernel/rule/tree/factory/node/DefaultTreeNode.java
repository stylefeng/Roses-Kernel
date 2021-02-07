package cn.stylefeng.roses.kernel.rule.tree.factory.node;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 默认树节点的封装
 * <p>
 * 默认的根节点id是-1，名称是根节点
 *
 * @author fengshuonan
 * @date 2020/10/15 14:39
 */
@Data
public class DefaultTreeNode implements AbstractTreeNode {

    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点id
     */
    private String pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否打开节点
     */
    private Boolean open;

    /**
     * 是否被选中
     */
    private Boolean checked = false;

    /**
     * 排序，越小越靠前
     */
    private BigDecimal sort;

    /**
     * 子节点
     */
    private List<DefaultTreeNode> children;

    @Override
    public String getNodeId() {
        return id;
    }

    @Override
    public String getNodeParentId() {
        return pId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

}
