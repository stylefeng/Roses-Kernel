package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 树排序请求封装
 *
 * @author majianguo
 * @date 2021/5/28 下午5:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TreeSortRequest extends BaseRequest {

    /**
     * 节点主键
     */
    @ChineseDescription("节点主键")
    @NotNull(message = "节点主键不能为空", groups = {edit.class})
    private Long nodeId;

    /**
     * 节点类型:1-叶子节点,2-数据节点
     */
    @ChineseDescription("节点类型:1-叶子节点,2-数据节点")
    @NotNull(message = "节点类型不能为空", groups = {edit.class})
    private String nodeType;

    /**
     * 节点父ID
     */
    @ChineseDescription("节点父ID")
    @NotNull(message = "节点父ID不能为空", groups = {edit.class})
    private Long nodePid;

    /**
     * 节点排序
     */
    @ChineseDescription("节点排序")
    @NotNull(message = "节点排序不能为空", groups = {edit.class})
    private BigDecimal nodeSort;
}
