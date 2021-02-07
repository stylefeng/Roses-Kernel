package cn.stylefeng.roses.kernel.system.pojo.menu.antd;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * antd通用前端树节点
 *
 * @author fengshuonan
 * @date 2020/6/9 12:42
 */
@Data
public class AntdBaseTreeNode implements AbstractTreeNode {

    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String title;

    /**
     * 值
     */
    private String value;

    /**
     * 排序，越小优先级越高
     */
    private Integer weight;

    /**
     * 子节点
     */
    private List children;

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
        this.children = children;
    }

}
