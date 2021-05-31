package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 接口分组封装类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiGroupRequest extends BaseRequest {

    /**
     * 资源分组主键
     */
    @NotNull(message = "资源分组主键不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("资源分组主键")
    private Long groupId;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("分组名称")
    private String groupName;

    /**
     * 分组父ID
     */
    @NotNull(message = "分组名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("分组父ID")
    private Long groupPid;

    /**
     * 分组父ID集合
     */
    @ChineseDescription("分组父ID集合")
    private String groupPids;

    /**
     * 分组排序
     */
    @NotNull(message = "分组名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("分组排序")
    private java.math.BigDecimal groupSort;

    @Valid
    @NotNull(message = "树节点排序不能为空", groups = {treeSort.class})
    @ChineseDescription("树节点排序")
    private List<TreeSortRequest> treeSortRequestList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 树排序
     */
    public @interface treeSort {

    }
}