package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 用于渲染api资源树（layui插件）
 *
 * @author fengshuonan
 * @date 2021/1/14 21:51
 */
@Data
public class LayuiApiResourceTreeNode implements AbstractTreeNode {

    /**
     * 资源的上级编码
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String title;

    /**
     * 资源的编码
     */
    private String id;

    /**
     * 是否展开状态 不展开-false 展开-true
     */
    private Boolean spread = false;

    /**
     * 是否是资源标识
     * <p>
     * true-是资源标识
     * false-虚拟节点，不是一个具体资源
     */
    private Boolean resourceFlag;

    /**
     * 子节点的集合
     */
    private List<LayuiApiResourceTreeNode> children;

    @Override
    public String getNodeId() {
        return this.id;
    }

    @Override
    public String getNodeParentId() {
        return this.parentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

}
