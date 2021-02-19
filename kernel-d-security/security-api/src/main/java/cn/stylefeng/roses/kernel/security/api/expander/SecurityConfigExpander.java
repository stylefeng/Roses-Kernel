package cn.stylefeng.roses.kernel.security.api.expander;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.security.api.constants.SecurityConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全模块的配置
 *
 * @author fengshuonan
 * @date 2021/2/19 8:49
 */
public class SecurityConfigExpander {

    /**
     * 获取XSS过滤的url范围
     *
     * @author fengshuonan
     * @date 2021/1/13 23:21
     */
    public static String[] getUrlPatterns() {
        String xssUrlIncludes = ConfigContext.me().getSysConfigValueWithDefault("SYS_XSS_URL_INCLUDES", String.class, SecurityConstants.DEFAULT_XSS_PATTERN);
        List<String> split = StrUtil.split(xssUrlIncludes, ',');
        return ArrayUtil.toArray(split, String.class);
    }

    /**
     * 获取XSS排除过滤的url范围
     *
     * @author fengshuonan
     * @date 2021/1/13 23:21
     */
    public static List<String> getUrlExclusion() {
        String noneSecurityUrls = ConfigContext.me().getSysConfigValueWithDefault("SYS_XSS_URL_EXCLUSIONS", String.class, "");
        if (StrUtil.isEmpty(noneSecurityUrls)) {
            return new ArrayList<>();
        } else {
            return StrUtil.split(noneSecurityUrls, ',');
        }
    }

}
