package cn.stylefeng.roses.kernel.system.modular.resource.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
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
    @ChineseDescription("字段主键")
    private Long fieldId;

    /**
     * 资源编码
     */
    @TableField(value = "api_resource_id")
    @ChineseDescription("资源编码")
    private Long apiResourceId;

    /**
     * 参数位置：request-请求参数，response-响应参数
     */
    @TableField("field_location")
    @ChineseDescription("参数位置：request-请求参数，response-响应参数")
    private String fieldLocation;

    /**
     * 字段名称，例如：邮箱
     */
    @TableField("field_name")
    @ChineseDescription("字段名称")
    private String fieldName;

    /**
     * 字段编码，例如：email
     */
    @TableField("field_code")
    @ChineseDescription("字段编码")
    private String fieldCode;

    /**
     * 字段类型：string或file、List、Object
     */
    @TableField("field_type")
    @ChineseDescription("字段类型")
    private String fieldType;

    /**
     * 是否必填：Y-是，N-否
     */
    @TableField("field_required")
    @ChineseDescription("是否必填")
    private String fieldRequired;

    /**
     * 字段其他校验信息，后端校验注解内容
     */
    @TableField("field_validation_msg")
    @ChineseDescription("字段其他校验信息，后端校验注解内容")
    private String fieldValidationMsg;

    /**
     * 字段子字段信息(Object和List会用到)
     */
    @TableField("field_sub_info")
    @ChineseDescription("字段子字段信息(Object和List会用到)")
    private String fieldSubInfo;

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