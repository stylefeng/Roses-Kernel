package cn.stylefeng.roses.kernel.log.api.exception;

import cn.stylefeng.roses.kernel.log.api.constants.LogConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 日志异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class LogException extends ServiceException {

    public LogException(AbstractExceptionEnum exception) {
        super(LogConstants.LOG_MODULE_NAME, exception);
    }

}
