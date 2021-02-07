package cn.stylefeng.roses.kernel.rule.tree.xmtree;

import cn.stylefeng.roses.kernel.rule.tree.xmtree.base.AbstractXmSelectNode;
import lombok.Data;

import java.util.List;

/**
 * 默认的xm-select的节点结构
 *
 * @author fengshuonan
 * @date 2021/1/31 19:02
 */
@Data
public class DefaultXmSelectNode implements AbstractXmSelectNode {

    /**
     * 树节点显示的名称
     */
    private String name;

    /**
     * 树节点的取值
     */
    private String value;

    /**
     * 是否选中
     */
    private Boolean selected;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 子节点列表，用于树结构渲染
     */
    private List<?> children;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Boolean getSelected() {
        return this.selected;
    }

    @Override
    public Boolean getDisabled() {
        return this.disabled;
    }

    @Override
    public List<?> getChildren() {
        return this.children;
    }

}
