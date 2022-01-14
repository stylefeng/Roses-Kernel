package cn.stylefeng.roses.kernel.system.modular.theme.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统主题模板属性表
 *
 * @author xixiaowei
 * @date 2021/12/17 9:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_theme_template_field")
public class SysThemeTemplateField extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "field_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键ID")
    private Long fieldId;

    /**
     * 属性名称
     */
    @TableField("field_name")
    @ChineseDescription("属性名称")
    private String fieldName;

    /**
     * 属性编码
     */
    @TableField("field_code")
    @ChineseDescription("属性编码")
    private String fieldCode;

    /**
     * 属性展示类型(字典维护)
     */
    @TableField("field_type")
    @ChineseDescription("属性展示类型")
    private String fieldType;

    /**
     * 是否必填：Y-必填，N-非必填
     */
    @TableField("field_required")
    @ChineseDescription("是否必填")
    private Character fieldRequired;

    /**
     * 属性长度
     */
    @TableField("field_length")
    @ChineseDescription("属性长度")
    private Integer fieldLength;

    /**
     * 属性描述
     */
    @TableField("field_description")
    @ChineseDescription("属性描述")
    private String fieldDescription;
}
