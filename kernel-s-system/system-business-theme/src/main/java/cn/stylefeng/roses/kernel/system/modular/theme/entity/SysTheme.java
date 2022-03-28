package cn.stylefeng.roses.kernel.system.modular.theme.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.file.api.pojo.AntdvFileInfo;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 系统主题表
 *
 * @author xixiaowei
 * @date 2021/12/17 9:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_theme")
public class SysTheme extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "theme_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键ID")
    private Long themeId;

    /**
     * 主题名称
     */
    @TableField("theme_name")
    @ChineseDescription("主题名称")
    private String themeName;

    /**
     * 主题属性(JSON格式)
     */
    @TableField("theme_value")
    @ChineseDescription("主题属性")
    private String themeValue;

    /**
     * 主题模板ID
     */
    @TableField("template_id")
    @ChineseDescription("主题模板ID")
    private Long templateId;

    /**
     * 启用状态：Y-启用，N-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("启用状态")
    private Character statusFlag;

    /**
     * 动态表单的key-value属性
     */
    private transient Map<String, Object> dynamicForm;

    /**
     * 用于编辑界面渲染antdv的临时文件展示
     */
    private transient Map<String, AntdvFileInfo[]> tempFileList;

}
