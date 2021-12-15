package cn.stylefeng.roses.kernel.system.api.pojo.title;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 标题图片配置信息参数
 *
 * @author xixiaowei
 * @date 2021/12/13 17:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTitleRequest extends BaseRequest {

    /**
     * 标题id
     */
    @NotNull(message = "titleId不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("标题id")
    private Long titleId;

    /**
     * 标题名称
     */
    @NotNull(message = "标题名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("标题名称")
    private String titleName;

    /**
     * 公司名称
     */
    @NotNull(message = "公司名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("公司名称")
    private String companyName;

    /**
     * 平台名称
     */
    @NotNull(message = "平台名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("平台名称")
    private String platformName;

    /**
     * 平台英文名称
     */
    @NotNull(message = "平台英文名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("平台英文名称")
    private String platformEnglishName;

    /**
     * 背景图片
     */
    @NotNull(message = "背景图片不能为空", groups = {add.class, edit.class})
    @ChineseDescription("背景图片")
    private MultipartFile backgroundImage;

    /**
     * 公司logo
     */
    @NotNull(message = "公司logo不能为空", groups = {add.class, edit.class})
    @ChineseDescription("公司logo")
    private MultipartFile companyLogo;

    /**
     * 浏览器icon
     */
    @NotNull(message = "浏览器icon不能为空", groups = {add.class, edit.class})
    @ChineseDescription("浏览器icon")
    private MultipartFile browserIcon;

    /**
     * 首页名称
     */
    @NotNull(message = "首页名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("首页名称")
    private String pageName;

    /**
     * 首页图标
     */
    @NotNull(message = "首页图标不能为空", groups = {add.class, edit.class})
    @ChineseDescription("首页图标")
    private MultipartFile pageImage;
}
