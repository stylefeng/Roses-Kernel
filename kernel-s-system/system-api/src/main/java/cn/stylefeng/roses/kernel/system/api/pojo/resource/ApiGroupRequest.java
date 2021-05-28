package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
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
    private Long groupId;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空", groups = {add.class, edit.class})
    private String groupName;

    /**
     * 分组父ID
     */
    @NotNull(message = "分组名称不能为空", groups = {add.class, edit.class})
    private Long groupPid;

    /**
     * 分组父ID集合
     */
    private String groupPids;

    /**
     * 分组排序
     */
    @NotNull(message = "分组名称不能为空", groups = {add.class, edit.class})
    private java.math.BigDecimal groupSort;

    @Valid
    @NotNull(message = "分组名称不能为空", groups = {treeSort.class})
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