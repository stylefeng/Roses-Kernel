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
 * 外部资源请求类
 *
 * @author majianguo
 * @date 2021/6/8 下午2:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalResourceRequest extends BaseRequest {

    /**
     * 资源所属应用code
     */
    @NotNull(message = "资源所属应用CODE不能为空", groups = {add.class})
    @ChineseDescription("资源所属应用CODE")
    private String appCode;

    /**
     * 资源列表
     */
    @Valid
    @NotNull(message = "资源列表不能为空", groups = {add.class})
    @ChineseDescription("资源列表")
    private List<ResourceRequest> resourceRequestList;

}