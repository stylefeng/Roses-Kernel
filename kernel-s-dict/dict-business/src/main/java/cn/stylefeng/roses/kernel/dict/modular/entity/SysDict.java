package cn.stylefeng.roses.kernel.dict.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典实体
 *
 * @author fengshuonan
 * @date 2020/12/26 22:37
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
@Data
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典名称首字母
     */
    @TableField("dict_name_pinyin")
    private String dictNamePinyin;

    /**
     * 字典编码
     */
    @TableField("dict_encode")
    private String dictEncode;

    /**
     * 字典类型的编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典简称
     */
    @TableField("dict_short_name")
    private String dictShortName;

    /**
     * 字典简称的编码
     */
    @TableField("dict_short_code")
    private String dictShortCode;

    /**
     * 上级字典的id(如果没有上级字典id，则为-1)
     */
    @TableField("dict_parent_id")
    private Long dictParentId;

    /**
     * 状态：(1-启用,2-禁用),参考 StatusEnum
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 排序，带小数点
     */
    @TableField("dict_sort")
    private BigDecimal dictSort;

    /**
     * 父id集合
     */
    @TableField("dict_pids")
    private String dictPids;

    /**
     * 是否删除，Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

    /**
     * 字典类型的名称
     */
    private transient String dictTypeName;

    /**
     * 字典类型的名称
     */
    private transient String parentName;

}
