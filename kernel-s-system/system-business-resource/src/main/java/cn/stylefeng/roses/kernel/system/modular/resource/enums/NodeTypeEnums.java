package cn.stylefeng.roses.kernel.system.modular.resource.enums;

import lombok.Getter;

/**
 * 分组树节点类型枚举
 *
 * @author majianguo
 * @date 2021/5/24 下午2:47
 */
@Getter
public enum NodeTypeEnums {

    /**
     * 叶子节点
     */
    LEAF_NODE("1", "叶子节点"),

    /**
     * 数据节点
     */
    DATA_NODE("2", "数据节点"),

    ;

    /**
     * 类型
     */
    private final String type;

    /**
     * 名称
     */
    private final String name;

    NodeTypeEnums(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
