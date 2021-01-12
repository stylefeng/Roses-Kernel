package cn.stylefeng.roses.kernel.system.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.DEFAULT_PASSWORD;

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

    /**
     * 获取系统名称
     *
     * @author fengshuonan
     * @date 2020/12/27 17:22
     */
    public static String getSystemName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SYSTEM_NAME", String.class, SystemConstants.DEFAULT_SYSTEM_NAME);
    }

    /**
     * 获取默认密码
     *
     * @author luojie
     * @date 2020/11/6 10:05
     */
    public static String getDefaultPassWord() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_PASSWORD", String.class, DEFAULT_PASSWORD);
    }

}
