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
package cn.stylefeng.roses.kernel.email.api.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.email.api.constants.MailConstants;

/**
 * 邮件相关的配置
 *
 * @author fengshuonan
 * @date 2020/12/1 11:45
 */
public class EmailConfigExpander {

    /**
     * smtp服务器地址，默认用126的邮箱
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static String getSmtpHost() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_HOST", String.class, MailConstants.DEFAULT_SMTP_HOST);
    }

    /**
     * smtp服务端口
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Integer getSmtpPort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_PORT", Integer.class, MailConstants.DEFAULT_SMTP_PORT);
    }

    /**
     * 是否启用账号密码验证
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Boolean getSmtpAuthEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_ENABLE_AUTH", Boolean.class, MailConstants.DEFAULT_SMTP_AUTH_ENABLE);
    }

    /**
     * 邮箱的账号
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static String getSmtpUser() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_ACCOUNT", String.class, MailConstants.DEFAULT_SMTP_USERNAME);
    }

    /**
     * 邮箱的密码或者授权码
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static String getSmtpPass() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_PASSWORD", String.class, MailConstants.DEFAULT_SMTP_PASSWORD);
    }

    /**
     * 邮箱的发送方邮箱
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static String getSmtpFrom() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SEND_FROM", String.class, MailConstants.DEFAULT_SMTP_SEND_FROM);
    }

    /**
     * 使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS或SSL）， 而不是使用一个单独的加密通信端口。
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Boolean getStartTlsEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_START_TLS_ENABLE", Boolean.class, MailConstants.DEFAULT_SMTP_TLS_ENABLE);
    }

    /**
     * 使用 SSL安全连接
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Boolean getSSLEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_TLS_ENABLE", Boolean.class, MailConstants.DEFAULT_SMTP_TLS_ENABLE);
    }

    /**
     * 指定的端口连接到在使用指定的套接字工厂
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Integer getSocketFactoryPort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SOCKET_FACTORY_PORT", Integer.class, MailConstants.DEFAULT_SMTP_PORT);
    }

    /**
     * SMTP超时时长，单位毫秒
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Long getTimeout() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_SMTP_TIMEOUT", Long.class, MailConstants.TIMEOUT_MILLISECOND);
    }

    /**
     * Socket连接超时值，单位毫秒，缺省值不超时
     *
     * @author fengshuonan
     * @date 2020/12/1 11:50
     */
    public static Long getConnectionTimeout() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_EMAIL_CONNECTION_TIMEOUT", Long.class, MailConstants.TIMEOUT_MILLISECOND);
    }

}
