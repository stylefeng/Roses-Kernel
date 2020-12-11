package cn.stylefeng.roses.kernel.system.pojo.resource.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 资源请求封装
 *
 * @author fengshuonan
 * @since 2019-09-10
 */
@Data
public class ResourceRequest implements Serializable {

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
     * 资源地址
     */
    private String url;

    /**
     * 是否是菜单（Y-是，N-否）
     */
    private String menuFlag;

}
