package cn.stylefeng.roses.kernel.system.pojo.resource.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 资源请求封装
 *
 * @author fengshuonan
 * @since 2019-09-10
 */
@Data
public class ResourceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源编码
     */
    @NotBlank(message = "资源编码为空", groups = detail.class)
    private String resourceCode;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 是否是菜单（Y-是，N-否）
     */
    private String menuFlag;

}
