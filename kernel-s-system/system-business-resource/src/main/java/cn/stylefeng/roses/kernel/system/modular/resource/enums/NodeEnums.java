package cn.stylefeng.roses.kernel.system.modular.resource.enums;

import lombok.Getter;

/**
 * 分组树节点枚举
 *
 * @author majianguo
 * @date 2021/5/24 下午12:04
 */
@Getter
public enum NodeEnums {

    /**
     * 根节点
     */
    ROOT_NODE(1000000000000000000L, "根节点");

    /**
     * 主键
     */
    private final Long id;

    /**
     * 名称
     */
    private final String name;

    NodeEnums(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
