package cn.stylefeng.roses.kernel.security.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.security.api.constants.SecurityConstants;

/**
 * XSS过滤异常
 *
 * @author fengshuonan
 * @date 2021/1/13 23:22
 */
public class XssFilterException extends ServiceException {

    public XssFilterException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public XssFilterException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
