package cn.stylefeng.roses.kernel.sms.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.sms.api.constants.SmsConstants;

/**
 * 短信发送的异常
 *
 * @author fengshuonan
 * @date 2020/10/26 16:53
 */
public class SmsException extends ServiceException {

    public SmsException(AbstractExceptionEnum exception) {
        super(SmsConstants.SMS_MODULE_NAME, exception);
    }

    public SmsException(AbstractExceptionEnum exception, Object... params) {
        super(SmsConstants.SMS_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
