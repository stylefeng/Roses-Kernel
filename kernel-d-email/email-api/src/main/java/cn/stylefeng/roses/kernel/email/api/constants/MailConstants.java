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
package cn.stylefeng.roses.kernel.email.api.constants;

/**
 * 邮件发送模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/23 17:33
 */
public interface MailConstants {

    /**
     * 邮件模块的名称
     */
    String MAIL_MODULE_NAME = "kernel-d-email";

    /**
     * 异常枚举的步进值
     */
    String MAIL_EXCEPTION_STEP_CODE = "08";

    /**
     * 默认smtp的host
     */
    String DEFAULT_SMTP_HOST = "smtp.126.com";

    /**
     * 默认smtp的端口
     */
    Integer DEFAULT_SMTP_PORT = 465;

    /**
     * 默认邮件发送账号，这里不要修改，在系统配置表改
     */
    String DEFAULT_SMTP_USERNAME = "xxx@126.com";

    /**
     * 默认邮件发送密码或授权码，这里不要修改，在系统配置表改
     */
    String DEFAULT_SMTP_PASSWORD = "xxx";

    /**
     * 默认开启tls
     */
    Boolean DEFAULT_SMTP_TLS_ENABLE = true;

    /**
     * 是否开启账号密码验证
     */
    Boolean DEFAULT_SMTP_AUTH_ENABLE = true;

    /**
     * 默认邮件的发送方
     */
    String DEFAULT_SMTP_SEND_FROM = DEFAULT_SMTP_USERNAME;

    /**
     * 超时时间10秒
     */
    Long TIMEOUT_MILLISECOND = 10000L;

}
