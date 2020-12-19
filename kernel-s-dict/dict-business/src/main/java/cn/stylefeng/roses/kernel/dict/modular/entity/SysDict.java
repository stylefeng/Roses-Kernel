package cn.stylefeng.roses.kernel.dict.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典实体
 *
 * @author fengshuonan
 * @date 2020/10/30 9:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    /**
     * 字典id
     */
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典类型的编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典编码，字典编码的前缀会添加字典类型编码+下划线
     */
    @TableField("dict_code")
    private String dictCode;

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
     * 上级字典的id(如果没有上级字典id，则为0)
     */
    @TableField("parent_dict_id")
    private Long parentDictId;

    /**
     * 排序，带小数点
     */
    @TableField(value = "dict_sort")
    private BigDecimal dictSort;

    /**
     * 状态：1-启用，2-禁用，参考 StatusEnum
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 字典类型的名称
     */
    private transient String dictTypeName;

}
