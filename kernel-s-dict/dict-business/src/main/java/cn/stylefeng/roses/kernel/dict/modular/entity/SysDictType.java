package cn.stylefeng.roses.kernel.dict.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典类型表，一个字典类型下有多个字典
 *
 * @author fengshuonan
 * @date 2020/10/30 10:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {

    /**
     * 字典类型id
     */
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_ID)
    private Long dictTypeId;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @TableField("dict_type_class")
    private Integer dictTypeClass;

    /**
     * 字典类型编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典类型业务编码
     */
    @TableField("dict_type_bus_code")
    private String dictTypeBusCode;

    /**
     * 字典类型名称
     */
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 字典类型名称拼音
     */
    @TableField("dict_type_name_pinyin")
    private String dictTypeNamePinyin;

    /**
     * 字典类型描述
     */
    @TableField("dict_type_desc")
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 删除标记 Y-已删除，N-未删除，参考 YesOrNotEnum
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

    /**
     * 排序，带小数点
     */
    @TableField(value = "dict_type_sort")
    private BigDecimal dictTypeSort;

}
