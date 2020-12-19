package cn.stylefeng.roses.kernel.resource.api.pojo.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * API资源的包装类
 *
 * @author fengshuonan
 * @date 2018-01-03-下午3:27
 */
@Data
public class ResourceDefinition implements Serializable {

    /**
     * 应用的标识
     */
    private String appCode;

    /**
     * 资源的标识
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    private String projectCode;

    /**
     * 控制器类名称
     */
    private String className;

    /**
     * 控制器中的方法名称
     */
    private String methodName;

    /**
     * 资源所属模块
     */
    private String modularCode;

    /**
     * 模块中文名称
     */
    private String modularName;

    /**
     * 初始化资源的机器的ip地址
     */
    private String ipAddress;

    /**
     * 资源的请求路径
     */
    private String url;

    /**
     * http请求方法
     */
    private String httpMethod;

    /**
     * 是否需要登录
     */
    private Boolean requiredLoginFlag;

    /**
     * 是否需要鉴权
     */
    private Boolean requiredPermissionFlag;

    /**
     * 需要进行参数校验的分组
     */
    private Set<String> validateGroups;

    /**
     * 接口参数的字段描述
     */
    private Set<FieldMetadata> paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     */
    private Set<FieldMetadata> responseFieldDescriptions;

    /**
     * 资源添加日期
     */
    private Date createTime;

}
