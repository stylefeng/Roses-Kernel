package cn.stylefeng.roses.kernel.system.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;

/**
 * 系统管理模块的异常
 *
 * @author fengshuonan
 * @date 2020/11/4 15:50
 */
public class SystemModularException extends ServiceException {

    public SystemModularException(AbstractExceptionEnum exception, Object... params) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SystemModularException(AbstractExceptionEnum exception) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception);
    }

}
