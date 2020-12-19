package cn.stylefeng.roses.kernel.resource.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源表
 *
 * @author fengshuonan
 * @date 2020/11/23 22:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_resource")
public class SysResource extends BaseEntity {

    /**
     * 资源id
     */
    @TableId("resource_id")
    private Long resourceId;

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 资源编码
     */
    @TableField("resource_code")
    private String resourceCode;

    /**
     * 资源名称
     */
    @TableField("resource_name")
    private String resourceName;

    /**
     * 项目编码
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 资源模块编码
     */
    @TableField("modular_code")
    private String modularCode;

    /**
     * 资源模块名称
     */
    @TableField("modular_name")
    private String modularName;

    /**
     * 资源初始化的服务器ip地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 资源url
     */
    @TableField("url")
    private String url;

    /**
     * http请求方法
     */
    @TableField("http_method")
    private String httpMethod;

    /**
     * 是否需要登录：Y-是，N-否
     */
    @TableField("required_login_flag")
    private String requiredLoginFlag;

    /**
     * 是否需要鉴权：Y-是，N-否
     */
    @TableField("required_permission_flag")
    private String requiredPermissionFlag;

    /**
     * 需要进行参数校验的分组
     * <p>
     * json形式存储
     */
    @TableField("validate_groups")
    private String validateGroups;

    /**
     * 接口参数的字段描述
     * <p>
     * json形式存储
     */
    @TableField("param_field_descriptions")
    private String paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     * <p>
     * json形式存储
     */
    @TableField("response_field_descriptions")
    private String responseFieldDescriptions;

    /**
     * 应用名称
     */
    private transient String appName;

}
