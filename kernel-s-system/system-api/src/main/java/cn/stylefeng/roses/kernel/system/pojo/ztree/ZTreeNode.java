package cn.stylefeng.roses.kernel.system.pojo.ztree;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * jquery ztree 插件的节点封装
 *
 * @author fengshuonan
 * @date 2021/1/6 21:47
 */
@ToString
@EqualsAndHashCode
public class ZTreeNode implements AbstractTreeNode {

    /**
     * 节点id
     */
    @Getter
    @Setter
    private Long id;

    /**
     * 父节点id
     */
    private Long pId;

    /**
     * 节点名称
     */
    @Getter
    @Setter
    private String name;

    /**
     * 是否打开节点
     */
    @Getter
    @Setter
    private Boolean open;

    /**
     * 是否被选中
     */
    @Getter
    @Setter
    private Boolean checked;

    /**
     * 节点图标  single or group
     */
    @Getter
    @Setter
    private String iconSkin;

    /**
     * 子节点集合
     */
    @Getter
    @Setter
    private List children;

    /**
     * 创建ztree的父级节点
     *
     * @author fengshuonan
     * @date 2021/1/6 21:47
     */
    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId(SystemConstants.DEFAULT_PARENT_ID);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setpId(SystemConstants.VIRTUAL_ROOT_PARENT_ID);
        return zTreeNode;
    }


    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return pId.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getpId() {
        return pId;
    }
}
