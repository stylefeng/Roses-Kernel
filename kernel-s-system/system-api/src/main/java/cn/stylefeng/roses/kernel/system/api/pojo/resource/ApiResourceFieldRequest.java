package cn.stylefeng.roses.kernel.system.api.pojo.resource;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接口字段信息封装类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResourceFieldRequest extends BaseRequest {

    /**
     * 字段主键
     */
    @NotNull(message = "字段主键不能为空", groups = {delete.class})
    private Long fieldId;

    /**
     * 资源编码
     */
    @NotNull(message = "资源编码不能为空", groups = {add.class,list.class})
    private Long apiResourceId;

    /**
     * 参数位置：request-请求参数，response-响应参数
     */
    @NotBlank(message = "参数位置不能为空",groups = {add.class,edit.class})
    private String fieldLocation;

    /**
     * 字段名称，例如：邮箱
     */
    @NotBlank(message = "参数位置不能为空",groups = {add.class,edit.class})
    private String fieldName;

    /**
     * 字段编码，例如：email
     */
    @NotBlank(message = "参数位置不能为空",groups = {add.class,edit.class})
    private String fieldCode;

    /**
     * 字段类型：string或file
     */
    @NotBlank(message = "参数位置不能为空",groups = {add.class,edit.class})
    private String fieldType;

    /**
     * 是否必填：Y-是，N-否
     */
    @NotBlank(message = "参数位置不能为空",groups = {add.class,edit.class})
    private String fieldRequired;

    /**
     * 字段其他校验信息，后端校验注解内容
     */
    private String fieldValidationMsg;

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

}