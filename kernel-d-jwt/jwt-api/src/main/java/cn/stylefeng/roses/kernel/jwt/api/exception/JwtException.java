package cn.stylefeng.roses.kernel.jwt.api.exception;

import cn.stylefeng.roses.kernel.jwt.api.constants.JwtConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * jwt异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class JwtException extends ServiceException {

    public JwtException(AbstractExceptionEnum exception, String userTip) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
