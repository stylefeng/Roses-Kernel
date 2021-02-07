package cn.stylefeng.roses.kernel.rule.tree.factory.base;

import java.util.List;

/**
 * 树构建的抽象类，定义构建tree的基本步骤
 *
 * @author fengshuonan
 * @date 2018/7/25 下午5:59
 */
public interface AbstractTreeBuildFactory<T> {

    /**
     * 树节点构建整体过程
     *
     * @param nodes 被处理的节点集合
     * @return 被处理后的节点集合（带树形结构了）
     * @author fengshuonan
     * @date 2018/7/26 上午9:45
     */
    List<T> doTreeBuild(List<T> nodes);

}