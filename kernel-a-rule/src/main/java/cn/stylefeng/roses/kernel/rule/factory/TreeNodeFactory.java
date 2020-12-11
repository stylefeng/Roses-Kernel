package cn.stylefeng.roses.kernel.rule.factory;

import cn.stylefeng.roses.kernel.rule.pojo.tree.DefaultTreeNode;

import static cn.stylefeng.roses.kernel.rule.constants.TreeConstants.ROOT_TREE_NODE_ID;
import static cn.stylefeng.roses.kernel.rule.constants.TreeConstants.ROOT_TREE_NODE_NAME;

/**
 * 创建树节点的工厂类
 *
 * @author fengshuonan
 * @date 2020/10/15 15:51
 */
public class TreeNodeFactory {

    /**
     * 创建一个根节点
     *
     * @author fengshuonan
     * @date 2020/10/15 15:52
     */
    public static DefaultTreeNode createRootNode() {
        DefaultTreeNode root = new DefaultTreeNode();
        root.setChecked(false);
        root.setId(ROOT_TREE_NODE_ID);
        root.setName(ROOT_TREE_NODE_NAME);
        root.setOpen(true);
        root.setPId(null);
        return root;
    }

}
