package cn.stylefeng.roses.kernel.system.pojo.menu.tree;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 通用树节点
 *
 * @author fengshuonan
 * @date 2020/3/26 14:29
 */
@Data
public class CommonBaseTreeNode implements AbstractTreeNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 节点父id
     */
    private Long pid;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 子节点集合
     */
    private List children;

    @Override
    public String getNodeId() {
        return this.id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.pid.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = children;
    }

}
