package cn.stylefeng.roses.kernel.rule.pojo.tree;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.enums.TreeNodeEnum;
import lombok.Data;

import java.util.List;

/**
 * 用户选择树节点封装
 * <p>
 * 默认的根节点id是-1，名称是根节点
 *
 * @author liuhanqing
 * @date 2021/1/15 13:36
 */
@Data
public class UserSelectTreeNode extends DefaultTreeNode {

    /**
     * 节点类型：org: 机构  user: 用户
     */
    private String nodeType;

    /**
     * 节点值
     */
    private String value;

    /**
     * 是否被选中
     */
    private Boolean selected = false;

    /**
     * 是否禁用
     */
    private Boolean disabled = false;

    public Boolean getDisabled() {
        if (this.disabled) {
            return true;
        }
        if (TreeNodeEnum.ORG.getCode().equals(nodeType) && IterUtil.isEmpty(this.getChildren())) {
            return true;
        }
        return this.disabled;
    }

}
