package cn.stylefeng.roses.kernel.email.api.exception;

import cn.stylefeng.roses.kernel.email.api.constants.MailConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import lombok.Getter;

/**
 * 邮件发送异常
 *
 * @author fengshuonan
 * @date 2018-07-06-下午3:00
 */
@Getter
public class MailException extends ServiceException {

    public MailException(String errorCode, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, errorCode, userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum, String userTip) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum.getErrorCode(), userTip);
    }

    public MailException(AbstractExceptionEnum exceptionEnum) {
        super(MailConstants.MAIL_MODULE_NAME, exceptionEnum);
    }

}
