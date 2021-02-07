package cn.stylefeng.roses.kernel.groovy.api.exception;

import cn.stylefeng.roses.kernel.groovy.api.constants.GroovyConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * groovy脚本执行异常
 *
 * @author fengshuonan
 * @date 2021/1/22 16:36
 */
public class GroovyException extends ServiceException {

    public GroovyException(AbstractExceptionEnum exception, String userTip) {
        super(GroovyConstants.GROOVY_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public GroovyException(AbstractExceptionEnum exception) {
        super(GroovyConstants.GROOVY_MODULE_NAME, exception);
    }

}
