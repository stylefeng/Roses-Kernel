package cn.stylefeng.roses.kernel.wrapper.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.wrapper.api.constants.WrapperConstants;

/**
 * Wrapper异常
 *
 * @author fengshuonan
 * @date 2021/1/19 22:24
 */
public class WrapperException extends ServiceException {

    public WrapperException(AbstractExceptionEnum exception, Object... params) {
        super(WrapperConstants.WRAPPER_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public WrapperException(AbstractExceptionEnum exception) {
        super(WrapperConstants.WRAPPER_MODULE_NAME, exception);
    }

}
