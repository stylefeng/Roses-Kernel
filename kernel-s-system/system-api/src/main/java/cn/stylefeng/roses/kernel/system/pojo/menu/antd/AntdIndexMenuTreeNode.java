package cn.stylefeng.roses.kernel.system.pojo.menu.antd;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 登录菜单
 *
 * @author fengshuonan
 * @date 2020/4/17 17:35
 */
@Data
public class AntdIndexMenuTreeNode implements AbstractTreeNode {

    /**
     * id
     */
    private Long id;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 菜单路由
     */
    private String path;

    /**
     * 路由元信息（路由附带扩展信息）
     */
    private Meta meta;

    /**
     * 子级节点
     */
    private List<AntdIndexMenuTreeNode> children;

    /**
     * 路由元信息内部类
     */
    @Data
    public static class Meta {

        /**
         * 菜单图标
         */
        public String icon;

        /**
         * 是否隐藏此菜单项, 默认 false，不隐藏
         */
        public Boolean invisible = false;

    }

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
        this.children = childrenNodes;
    }

}
