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
package cn.stylefeng.roses.kernel.sms.api.expander;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.sms.api.constants.SmsConstants;

/**
 * 短信相关的配置拓展
 *
 * @author fengshuonan
 * @date 2020/10/26 22:09
 */
public class SmsConfigExpander {

    /**
     * 获取短信验证码失效时间（单位：秒）
     * <p>
     * 默认300秒
     *
     * @author fengshuonan
     * @date 2020/10/26 22:09
     */
    public static Integer getSmsValidateExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SMS_VALIDATE_EXPIRED_SECONDS", Integer.class, SmsConstants.DEFAULT_SMS_INVALID_SECONDS);
    }

    /**
     * 阿里云短信的accessKeyId
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeyId() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_ACCESS_KEY_ID", String.class, StrUtil.EMPTY);
    }

    /**
     * 阿里云短信的accessKeySecret
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeySecret() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_ACCESS_KEY_SECRET", String.class, StrUtil.EMPTY);
    }

    /**
     * 阿里云短信的签名
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsSignName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_ALIYUN_SMS_SIGN_NAME", String.class, StrUtil.EMPTY);
    }

}
