package cn.stylefeng.roses.kernel.validator.exception;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants;

/**
 * 计数器校验异常
 *
 * @author fengshuonan
 * @date 2020/11/14 17:53
 */
public class CountValidateException extends ServiceException {

    public CountValidateException(AbstractExceptionEnum exception, String userTip) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public CountValidateException(AbstractExceptionEnum exception) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception);
    }

}
