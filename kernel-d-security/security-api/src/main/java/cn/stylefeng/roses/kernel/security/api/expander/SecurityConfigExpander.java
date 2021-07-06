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

    /**
     * 获取AES秘钥
     *
     * @return {@link String}
     * @author majianguo
     * @date 2021/7/5 10:15
     **/
    public static String getEncryptSecretKey() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ENCRYPT_SECRET_KEY", String.class, "Ux1dqQ22KxVjSYootgzMe776em8vWEGE");
    }

    /**
     * 获取验证码的开关
     *
     * @author fengshuonan
     * @date 2020/12/27 17:22
     */
    public static Boolean getCaptchaOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_CAPTCHA_OPEN", Boolean.class, SecurityConstants.DEFAULT_CAPTCHA_OPEN);
    }

    /**
     * 获取拖拽验证码的开关
     *
     * @author fengshuonan
     * @date 2020/12/27 17:22
     */
    public static Boolean getDragCaptchaOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRAG_CAPTCHA_OPEN", Boolean.class, SecurityConstants.DEFAULT_CAPTCHA_OPEN);
    }

}
