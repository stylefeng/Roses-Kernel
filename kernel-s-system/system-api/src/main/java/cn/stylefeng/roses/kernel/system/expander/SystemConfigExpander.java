package cn.stylefeng.roses.kernel.system.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;

/**
 * 系统的一些基础信息
 *
 * @author fengshuonan
 * @date 2020/12/27 17:13
 */
public class SystemConfigExpander {

    /**
     * 获取系统发布的版本号（防止css和js的缓存）
     *
     * @author fengshuonan
     * @date 2020/12/27 17:14
     */
    public static String getReleaseVersion() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_RELEASE_VERSION", String.class, SystemConstants.DEFAULT_SYSTEM_VERSION);
    }

    /**
     * 获取租户是否开启的标识，默认是关的
     *
     * @author fengshuonan
     * @date 2020/12/27 17:21
     */
    public static Boolean getTenantOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_TENANT_OPEN", Boolean.class, SystemConstants.DEFAULT_TENANT_OPEN);
    }

    /**
     * 获取验证码的开关
     *
     * @author fengshuonan
     * @date 2020/12/27 17:22
     */
    public static Boolean getCaptchaOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_CAPTCHA_OPEN", Boolean.class, SystemConstants.DEFAULT_CAPTCHA_OPEN);
    }

}
