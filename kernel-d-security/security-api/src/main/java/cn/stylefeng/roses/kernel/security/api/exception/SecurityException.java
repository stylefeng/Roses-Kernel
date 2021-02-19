package cn.stylefeng.roses.kernel.security.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.security.api.constants.SecurityConstants;

/**
 * 安全模块异常
 *
 * @author fengshuonan
 * @date 2021/2/19 8:48
 */
public class SecurityException extends ServiceException {

    public SecurityException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SecurityException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
