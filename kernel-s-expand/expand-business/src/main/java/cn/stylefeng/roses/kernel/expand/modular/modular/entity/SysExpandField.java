package cn.stylefeng.roses.kernel.expand.modular.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务拓展-字段信息实例类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@TableName("sys_expand_field")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysExpandField extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "field_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long fieldId;

    /**
     * 对应拓展业务的主键id
     */
    @TableField("expand_id")
    @ChineseDescription("对应拓展业务的主键id")
    private Long expandId;

    /**
     * 字段中文名称，例如：身份证号
     */
    @TableField("field_name")
    @ChineseDescription("字段中文名称，例如：身份证号")
    private String fieldName;

    /**
     * 字段英文名称，例如：idCard
     */
    @TableField("field_code")
    @ChineseDescription("字段英文名称，例如：idCard")
    private String fieldCode;

    /**
     * 字段类型：1-字符串类型，2-数字类型，3-字典类型
     */
    @TableField("field_type")
    @ChineseDescription("字段类型：1-字符串类型，2-数字类型，3-字典类型")
    private Integer fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @TableField("field_required")
    @ChineseDescription("是否必填：Y-必填，N-非必填")
    private String fieldRequired;

    /**
     * 属性值长度，用于数字类型
     */
    @TableField("field_length")
    @ChineseDescription("属性值长度，用于数字类型")
    private Integer fieldLength;

    /**
     * 字典类型编码，用于字典类型
     */
    @TableField("field_dict_type_code")
    @ChineseDescription("字典类型编码，用于字典类型")
    private String fieldDictTypeCode;

    /**
     * 列表是否显示：Y-显示，N-不显示
     */
    @TableField("list_show_flag")
    @ChineseDescription("列表是否显示：Y-显示，N-不显示")
    private String listShowFlag;

}
