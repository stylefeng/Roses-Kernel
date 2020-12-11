package cn.stylefeng.roses.kernel.system.exception;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;

/**
 * 系统管理模块的异常
 *
 * @author fengshuonan
 * @date 2020/11/4 15:50
 */
public class SystemModularException extends ServiceException {

    public SystemModularException(AbstractExceptionEnum exception, String userTip) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public SystemModularException(AbstractExceptionEnum exception) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception);
    }

}
