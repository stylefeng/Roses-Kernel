package cn.stylefeng.roses.kernel.jwt.api.exception;

import cn.hutool.core.util.StrUtil;
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

    public JwtException(AbstractExceptionEnum exception, Object... params) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
