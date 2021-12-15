package cn.stylefeng.roses.kernel.system.modular.title.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标题图片配置信息表
 *
 * @author xixiaowei
 * @date 2021/12/13 15:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_title")
public class SysTitle extends BaseEntity {

    /**
     * 标题id
     */
    @TableId(value = "title_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("标题id")
    private Long titleId;

    /**
     * 标题名称
     */
    @TableField("title_name")
    @ChineseDescription("标题名称")
    private String titleName;

    /**
     * 公司名称
     */
    @TableField("company_name")
    @ChineseDescription("公司名称")
    private String companyName;

    /**
     * 平台名称
     */
    @TableField("platform_name")
    @ChineseDescription("平台名称")
    private String platformName;

    /**
     * 平台英文名称
     */
    @TableField("platform_english_name")
    @ChineseDescription("平台英文名称")
    private String platformEnglishName;

    /**
     * 背景图片
     */
    @TableField("background_image")
    @ChineseDescription("背景图片")
    private String backgroundImage;

    /**
     * 公司logo
     */
    @TableField("company_logo")
    @ChineseDescription("公司logo")
    private String companyLogo;

    /**
     * 浏览器icon
     */
    @TableField("browser_icon")
    @ChineseDescription("浏览器icon")
    private String browserIcon;

    /**
     * 首页名称
     */
    @TableField("page_name")
    @ChineseDescription("首页名称")
    private String pageName;

    /**
     * 首页图标
     */
    @TableField("page_image")
    @ChineseDescription("首页图标")
    private String pageImage;

    /**
     * 启用状态：Y、启用  N、未启用
     */
    @TableField("status")
    @ChineseDescription("启用状态")
    private Character status;
}
