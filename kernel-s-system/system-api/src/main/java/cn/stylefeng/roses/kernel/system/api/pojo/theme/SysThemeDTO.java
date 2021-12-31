package cn.stylefeng.roses.kernel.system.api.pojo.theme;

import lombok.Data;

import java.util.Date;

/**
 * 用于前端数据渲染
 *
 * @author xixiaowei
 * @date 2021/12/31 10:01
 */
@Data
public class SysThemeDTO {

    /**
     * 主键ID
     */
    private Long themeId;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 主题属性(JSON格式)
     */
    private String themeValue;

    /**
     * 主题模板ID
     */
    private Long templateId;

    /**
     * 启用状态：Y-启用，N-禁用
     */
    private Character statusFlag;

    /**
     * 模板名称，用于前端数据渲染
     */
    private String templateName;

    /**
     * 创建时间
     */
    private Date createTime;
}
