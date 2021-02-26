package cn.stylefeng.roses.kernel.email.jdk;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.stylefeng.roses.kernel.email.api.MailSenderApi;
import cn.stylefeng.roses.kernel.email.api.exception.MailException;
import cn.stylefeng.roses.kernel.email.api.exception.enums.EmailExceptionEnum;
import cn.stylefeng.roses.kernel.email.api.pojo.SendMailParam;

/**
 * 邮件发送器
 *
 * @author fengshuonan
 * @date 2020/6/9 22:54
 */
public class JavaMailSender implements MailSenderApi {

    /**
     * 邮件配置
     */
    private final MailAccount mailAccount;

    public JavaMailSender(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(mailAccount, CollUtil.newArrayList(sendMailParam.getTo()), sendMailParam.getTitle(), sendMailParam.getContent(), false);
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(mailAccount, CollUtil.newArrayList(sendMailParam.getTo()), sendMailParam.getTitle(), sendMailParam.getContent(), true);
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

        if (ObjectUtil.isEmpty(sendMailParam.getTo())) {
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
