package cn.stylefeng.roses.kernel.scanner.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 资源持久化的请求参数封装
 *
 * @author fengshuonan
 * @date 2020/10/19 21:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReportResourceParam extends BaseRequest {

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     */
    private String projectCode;

    /**
     * 资源集合
     */
    private Map<String, Map<String, ResourceDefinition>> resourceDefinitions;

    public ReportResourceParam(String projectCode, Map<String, Map<String, ResourceDefinition>> resourceDefinitions) {
        this.projectCode = projectCode;
        this.resourceDefinitions = resourceDefinitions;
    }

}
