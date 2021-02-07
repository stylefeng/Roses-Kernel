package cn.stylefeng.roses.kernel.timer.api.exception;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.timer.api.constants.TimerConstants;

/**
 * 定时器任务的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class TimerException extends ServiceException {

    public TimerException(AbstractExceptionEnum exception) {
        super(TimerConstants.TIMER_MODULE_NAME, exception);
    }

    public TimerException(AbstractExceptionEnum exception, String userTip) {
        super(TimerConstants.TIMER_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
