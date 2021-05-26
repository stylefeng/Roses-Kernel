package cn.stylefeng.roses.kernel.system.modular.resource.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 接口字段信息实例类
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@TableName("api_resource_field")
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiResourceField extends BaseEntity {

    /**
     * 字段主键
     */
    @TableId(value = "field_id", type = IdType.ASSIGN_ID)
    private Long fieldId;

    /**
     * 资源编码
     */
    @TableField(value = "api_resource_id")
    private Long apiResourceId;

    /**
     * 参数位置：request-请求参数，response-响应参数
     */
    @TableField("field_location")
    private String fieldLocation;

    /**
     * 字段名称，例如：邮箱
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段编码，例如：email
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 字段类型：string或file
     */
    @TableField("field_type")
    private String fieldType;

    /**
     * 是否必填：Y-是，N-否
     */
    @TableField("field_required")
    private String fieldRequired;

    /**
     * 字段其他校验信息，后端校验注解内容
     */
    @TableField("field_validation_msg")
    private String fieldValidationMsg;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private Long updateUser;

}