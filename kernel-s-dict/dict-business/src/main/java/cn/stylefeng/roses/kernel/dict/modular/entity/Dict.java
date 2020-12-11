package cn.stylefeng.roses.kernel.dict.modular.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
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
public class Dict extends BaseEntity {

    /**
     * 字典id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
     * 上级字典的id
     * <p>
     * 字典列表是可以有树形结构的，但是字典类型没有树形结构
     * <p>
     * 如果没有上级字典id，则为-1
     */
    @TableField("parent_dict_id")
    private Long parentDictId;

    /**
     * 状态(1:启用,2:禁用)，参考 StatusEnum
     */
    @TableField("dict_status")
    private Integer dictStatus;

    /**
     * 删除标记 Y-已删除，N-未删除，参考 YesOrNotEnum
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 排序，带小数点
     */
    @TableField(value = "dict_sort")
    private BigDecimal dictSort;

    /**
     * 字典类型的名称
     */
    private transient String dictTypeName;

}
