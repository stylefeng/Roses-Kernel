package cn.stylefeng.roses.kernel.resource.api.exception;

import cn.stylefeng.roses.kernel.resource.api.constants.ScannerConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 资源模块的异常
 *
 * @author fengshuonan
 * @date 2020/11/3 13:54
 */
public class ScannerException extends ServiceException {

    public ScannerException(String errorCode, String userTip) {
        super(ScannerConstants.RESOURCE_MODULE_NAME, errorCode, userTip);
    }

    public ScannerException(AbstractExceptionEnum exception) {
        super(ScannerConstants.RESOURCE_MODULE_NAME, exception);
    }

    public ScannerException(AbstractExceptionEnum exception, String userTip) {
        super(ScannerConstants.RESOURCE_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
