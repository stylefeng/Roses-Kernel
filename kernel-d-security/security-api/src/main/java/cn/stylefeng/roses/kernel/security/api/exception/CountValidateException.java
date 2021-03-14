package cn.stylefeng.roses.kernel.security.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.security.api.constants.SecurityConstants;

/**
 * 计数器校验异常
 *
 * @author fengshuonan
 * @date 2020/11/14 17:53
 */
public class CountValidateException extends ServiceException {

    public CountValidateException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public CountValidateException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
