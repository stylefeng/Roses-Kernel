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
 * 业务拓展实例类
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@TableName("sys_expand")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysExpand extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "expand_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long expandId;

    /**
     * 拓展业务名称
     */
    @TableField("expand_name")
    @ChineseDescription("拓展业务名称")
    private String expandName;

    /**
     * 拓展业务唯一编码
     */
    @TableField("expand_code")
    @ChineseDescription("拓展业务唯一编码")
    private String expandCode;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("expand_status")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer expandStatus;

    /**
     * 主业务表，例如：sys_user
     */
    @TableField("primary_table_name")
    @ChineseDescription("主业务表，例如：sys_user")
    private String primaryTableName;

    /**
     * 业务主键id字段名，例如：user_id
     */
    @TableField("primary_field_name")
    @ChineseDescription("业务主键id字段名，例如：user_id")
    private String primaryFieldName;

    /**
     * 业务主键id字段名驼峰法，例如：userId
     */
    @TableField("primary_field_camel")
    @ChineseDescription("业务主键id字段名驼峰法，例如：userId")
    private String primaryFieldCamel;


}
