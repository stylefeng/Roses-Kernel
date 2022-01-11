package cn.stylefeng.roses.kernel.system.modular.theme.pojo;

import lombok.Data;

import java.util.Map;

/**
 * Guns默认主题控制的字段
 *
 * @author fengshuonan
 * @date 2022/1/10 18:30
 */
@Data
public class DefaultTheme {

    /**
     * 平台名称
     */
    private String gunsMgrName;

    /**
     * 登录页背景图片
     */
    private String gunsMgrLoginBackgroundImg;

    /**
     * 平台LOGO
     */
    private String gunsMgrLogo;

    /**
     * 浏览器Icon
     */
    private String gunsMgrFavicon;

    /**
     * 页脚文字
     */
    private String gunsMgrFooterText;

    /**
     * 备案号
     */
    private String gunsMgrBeiNo;

    /**
     * 备案号跳转链接
     */
    private String gunsMgrBeiUrl;

    /**
     * 其他的主题配置
     */
    private Map<String, String> otherConfigs;

}
