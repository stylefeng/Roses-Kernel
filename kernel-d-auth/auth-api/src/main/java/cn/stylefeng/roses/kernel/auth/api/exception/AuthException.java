package cn.stylefeng.roses.kernel.auth.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 认证类异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class AuthException extends ServiceException {

    public AuthException(AbstractExceptionEnum exception, Object... params) {
        super(AuthConstants.AUTH_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public AuthException(AbstractExceptionEnum exception) {
        super(AuthConstants.AUTH_MODULE_NAME, exception);
    }

}
