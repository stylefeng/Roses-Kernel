package cn.stylefeng.roses.kernel.monitor.api.exception;

import cn.stylefeng.roses.kernel.monitor.api.constants.MonitorConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 监控模块异常
 *
 * @author fengshuonan
 * @date 2021/1/31 22:35
 */
public class MonitorException extends ServiceException {

    public MonitorException(AbstractExceptionEnum exception) {
        super(MonitorConstants.MONITOR_MODULE_NAME, exception);
    }

}
