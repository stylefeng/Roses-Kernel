package cn.stylefeng.roses.kernel.auth.api.expander;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;

import java.util.ArrayList;
import java.util.List;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.*;

/**
 * 权限相关配置快速获取
 *
 * @author fengshuonan
 * @date 2020/10/17 16:10
 */
public class AuthConfigExpander {

    /**
     * 获取不被权限控制的url
     *
     * @author fengshuonan
     * @date 2020/10/17 16:12
     */
    public static List<String> getNoneSecurityConfig() {
        String noneSecurityUrls = ConfigContext.me().getSysConfigValueWithDefault("SYS_NONE_SECURITY_URLS", String.class, "");
        if (StrUtil.isEmpty(noneSecurityUrls)) {
            return new ArrayList<>();
        } else {
            return StrUtil.split(noneSecurityUrls, ',');
        }
    }

    /**
     * 获取session过期时间，默认3600秒
     * <p>
     * 在这个时段内不操作，会将用户踢下线，从新登陆
     * <p>
     * 关于记住我功能，如果开启了记住我功能，这个参数
     *
     * @author fengshuonan
     * @date 2020/10/20 9:32
     */
    public static Long getSessionExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SESSION_EXPIRED_SECONDS", Long.class, 3600L);
    }

    /**
     * 获取单账号单端登录的开关
     * <p>
     * 单账号单端登录为限制一个账号多个浏览器登录
     *
     * @return true-开启单端限制，false-关闭单端限制
     * @author fengshuonan
     * @date 2020/10/21 14:31
     */
    public static boolean getSingleAccountLoginFlag() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SINGLE_ACCOUNT_LOGIN_FLAG", Boolean.class, false);
    }

    /**
     * 获取携带token的header头的名称
     *
     * @author fengshuonan
     * @date 2020/10/22 14:11
     */
    public static String getAuthTokenHeaderName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_HEADER_NAME", String.class, DEFAULT_AUTH_HEADER_NAME);
    }

    /**
     * 获取携带token的header头的名称
     *
     * @author fengshuonan
     * @date 2020/10/22 14:11
     */
    public static String getAuthTokenParamName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_PARAM_NAME", String.class, DEFAULT_AUTH_PARAM_NAME);
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
