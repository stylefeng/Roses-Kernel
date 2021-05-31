package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 接口分组树节点包装类
 *
 * @author majianguo
 * @date 2021/5/22 上午11:03
 */
@Data
public class ApiGroupTreeWrapper implements AbstractTreeNode<ApiGroupTreeWrapper> {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点父ID
     */
    private Long pid;

    /**
     * 节点是否可选择
     */
    private Boolean selectable = true;

    /**
     * 类型（1：节点；2：资源）
     */
    private String type;

    /**
     * 节点数据
     */
    private Object data;

    /**
     * 排序
     */
    private BigDecimal sort;

    /**
     * 节点URL
     */
    private String url;

    /**
     * 图标
     */
    private Map<String, String> slots = new HashMap<>();

    /**
     * tree子节点
     */
    private List<ApiGroupTreeWrapper> children = Collections.synchronizedList(new ArrayList<>());

    public void setSlotsValue() {
        if ("1".equals(type)) {
            slots.put("icon", "group");
        } else {
            slots.put("icon", "resource");
        }
    }

    @Override
    public String getNodeId() {
        if (this.id == null) {
            return null;
        } else {
            return this.id.toString();
        }
    }

    @Override
    public String getNodeParentId() {
        if (this.pid == null) {
            return null;
        } else {
            return this.pid.toString();
        }
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

    /**
     * 把本节点的所有子节点排序了
     *
     * @author majianguo
     * @date 2021/3/16 14:04
     */
    public void sortChildren() {
        if (ObjectUtil.isNotEmpty(children)) {
            this.children = children.stream().sorted(Comparator.comparing(ApiGroupTreeWrapper::getSort)).collect(Collectors.toList());
            // 让所有子节点也进行该操作
            for (ApiGroupTreeWrapper apiGroupTreeWrapper : this.children) {
                apiGroupTreeWrapper.sortChildren();
            }
        }
    }
}
