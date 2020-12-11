package cn.stylefeng.roses.kernel.validator.exception;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants;

/**
 * 参数校验异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class ParamValidateException extends ServiceException {

    public ParamValidateException(AbstractExceptionEnum exception, String userTip) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public ParamValidateException(AbstractExceptionEnum exception) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception);
    }

}
