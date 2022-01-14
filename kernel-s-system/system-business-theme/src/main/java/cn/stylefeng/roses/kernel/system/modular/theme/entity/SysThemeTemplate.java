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
 * 系统主题模板表
 *
 * @author xixiaowei
 * @date 2021/12/17 9:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_theme_template")
public class SysThemeTemplate extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键ID")
    private Long templateId;

    /**
     * 主题模板名称
     */
    @TableField("template_name")
    @ChineseDescription("主题模板名称")
    private String templateName;

    /**
     * 主题模板编码
     */
    @TableField("template_code")
    @ChineseDescription("主题模板编码")
    private String templateCode;

    /**
     * 主题模板类型：1-系统类型，2-业务类型
     */
    @TableField("template_type")
    @ChineseDescription("主题模板类型")
    private Short templateType;

    /**
     * 主题模板启用状态：Y-启用，N-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("主题模板启用状态")
    private Character statusFlag;
}
