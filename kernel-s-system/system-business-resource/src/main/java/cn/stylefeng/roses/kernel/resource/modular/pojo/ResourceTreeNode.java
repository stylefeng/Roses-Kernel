package cn.stylefeng.roses.kernel.resource.modular.pojo;

import lombok.Data;

import java.util.List;

/**
 * 资源树节点的描述
 *
 * @author fengshuonan
 * @date 2020/3/26 14:29
 */
@Data
public class ResourceTreeNode {

    /**
     * 资源id
     */
    private String code;

    /**
     * 父级资源id
     */
    private String parentCode;

    /**
     * 资源名称
     */
    private String nodeName;

    /**
     * 是否是资源标识
     * <p>
     * true-是资源标识
     * false-虚拟节点，不是一个具体资源
     */
    private Boolean resourceFlag;

    /**
     * 能否选择
     */
    private Boolean checked;

    /**
     * 子节点集合
     */
    private List children;

}
