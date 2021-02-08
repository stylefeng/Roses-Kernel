package cn.stylefeng.roses.kernel.system.pojo.menu.antd;

import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单树节点，用在新增和修改菜单，下拉选父级时候
 *
 * @author fengshuonan
 * @date 2020/4/5 12:03
 */
@Data
public class AntdMenuSelectTreeNode implements AbstractTreeNode {

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
    private BigDecimal weight;

    /**
     * 子节点
     */
    private List children;

    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return this.parentId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }
}
