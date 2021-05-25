/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.auth.api.expander;

import cn.hutool.core.util.RandomUtil;
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
     * 用于auth校验的jwt的秘钥
     *
     * @author fengshuonan
     * @date 2021/1/2 18:52
     */
    public static String getAuthJwtSecret() {
        String sysJwtSecret = ConfigContext.me().getConfigValueNullable("SYS_AUTH_JWT_SECRET", String.class);

        // 没配置就返回一个随机密码
        if (sysJwtSecret == null) {
            return RandomUtil.randomString(20);
        } else {
            return sysJwtSecret;
        }
    }

    /**
     * 用于auth模块权限校验的jwt失效时间
     * <p>
     * 这个时间也是“记住我”功能的过期时间，默认为7天
     * <p>
     * 如果登录的时候开启了“记住我”，则用户7天内免登录
     *
     * @author fengshuonan
     * @date 2021/1/2 18:53
     */
    public static Long getAuthJwtTimeoutSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_JWT_TIMEOUT_SECONDS", Long.class, DEFAULT_AUTH_JWT_TIMEOUT_SECONDS);
    }

    /**
     * 获取session过期时间，默认3600秒
     * <p>
     * 在这个时段内不操作，会将用户踢下线，从新登陆
     * <p>
     * 如果开启了记住我功能，在session过期后会从新创建session
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
     * 获取携带token的param传参的名称
     *
     * @author fengshuonan
     * @date 2020/10/22 14:11
     */
    public static String getAuthTokenParamName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_PARAM_NAME", String.class, DEFAULT_AUTH_PARAM_NAME);
    }

    /**
     * 会话保存在cookie中时，cooke的name
     *
     * @author fengshuonan
     * @date 2020/12/27 13:18
     */
    public static String getSessionCookieName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SESSION_COOKIE_NAME", String.class, DEFAULT_AUTH_HEADER_NAME);
    }

    /**
     * 默认解析jwt的秘钥（用于解析sso传过来的token）
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoJwtSecret() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_JWT_SECRET", String.class, SYS_AUTH_SSO_JWT_SECRET);
    }

    /**
     * 默认解析sso加密的数据的秘钥
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoDataDecryptSecret() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_DECRYPT_DATA_SECRET", String.class, SYS_AUTH_SSO_DECRYPT_DATA_SECRET);
    }

    /**
     * 获取是否开启sso远程会话校验，当系统对接sso后，如需同时校验sso的会话是否存在则开启此开关
     *
     * @return true-开启远程校验，false-关闭远程校验
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static Boolean getSsoSessionValidateSwitch() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH", Boolean.class, SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH);
    }

    /**
     * sso会话远程校验，redis的host
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoSessionValidateRedisHost() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST", String.class, SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST);
    }

    /**
     * sso会话远程校验，redis的端口
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static Integer getSsoSessionValidateRedisPort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT", Integer.class, SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT);
    }

    /**
     * sso会话远程校验，redis的密码
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoSessionValidateRedisPassword() {
        return ConfigContext.me().getConfigValueNullable("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PASSWORD", String.class);
    }

    /**
     * sso会话远程校验，redis的db
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static Integer getSsoSessionValidateRedisDbIndex() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX", Integer.class, SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX);
    }

    /**
     * sso会话远程校验，redis的缓存前缀
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoSessionValidateRedisCachePrefix() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX", String.class, SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX);
    }

    /**
     * 获取SSO服务器的地址
     *
     * @author fengshuonan
     * @date 2021/5/25 22:39
     */
    public static String getSsoUrl() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_AUTH_SSO_HOST", String.class, SYS_AUTH_SSO_HOST);
    }

}
