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
package cn.stylefeng.roses.kernel.email.jdk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.stylefeng.roses.kernel.email.api.MailSenderApi;
import cn.stylefeng.roses.kernel.email.api.exception.MailException;
import cn.stylefeng.roses.kernel.email.api.exception.enums.EmailExceptionEnum;
import cn.stylefeng.roses.kernel.email.api.expander.EmailConfigExpander;
import cn.stylefeng.roses.kernel.email.api.pojo.SendMailParam;

/**
 * 邮件发送器
 *
 * @author fengshuonan
 * @date 2020/6/9 22:54
 */
public class JavaMailSender implements MailSenderApi {

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), false, sendMailParam.getFiles());
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(this.getConfigAccountInfo(), sendMailParam.getTos(), sendMailParam.getCcsTos(), sendMailParam.getBccsTos(), sendMailParam.getTitle(), sendMailParam.getContent(), sendMailParam.getImageMap(), true, sendMailParam.getFiles());
    }

    /**
     * 获取配置账号信息
     *
     * @return {@link MailAccount}
     * @author majianguo
     * @date 2021/8/16 13:57
     **/
    private MailAccount getConfigAccountInfo() {
        MailAccount mailAccount = new MailAccount();
        // 配置默认都从系统配置表获取
        mailAccount.setHost(EmailConfigExpander.getSmtpHost());
        mailAccount.setPort(EmailConfigExpander.getSmtpPort());
        mailAccount.setAuth(EmailConfigExpander.getSmtpAuthEnable());
        mailAccount.setUser(EmailConfigExpander.getSmtpUser());
        mailAccount.setPass(EmailConfigExpander.getSmtpPass());
        mailAccount.setFrom(EmailConfigExpander.getSmtpFrom());
        mailAccount.setStarttlsEnable(EmailConfigExpander.getStartTlsEnable());
        mailAccount.setSslEnable(EmailConfigExpander.getSSLEnable());
        mailAccount.setSocketFactoryPort(EmailConfigExpander.getSocketFactoryPort());
        mailAccount.setTimeout(EmailConfigExpander.getTimeout());
        mailAccount.setConnectionTimeout(EmailConfigExpander.getConnectionTimeout());
        return mailAccount;
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @author fengshuonan
     * @date 2018/7/8 下午6:41
     */
    private void assertSendMailParams(SendMailParam sendMailParam) {
        if (sendMailParam == null) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTos())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "收件人邮箱");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTitle())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件标题");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(sendMailParam.getContent())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件内容");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }
    }
}
