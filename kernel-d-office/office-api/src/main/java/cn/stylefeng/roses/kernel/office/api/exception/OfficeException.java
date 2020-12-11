package cn.stylefeng.roses.kernel.office.api.exception;

import cn.stylefeng.roses.kernel.office.api.constants.OfficeConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * Office模块异常
 *
 * @author luojie
 * @date 2020/11/4 10:15
 */
public class OfficeException extends ServiceException {

    public OfficeException(AbstractExceptionEnum exception) {
        super(OfficeConstants.OFFICE_MODULE_NAME, exception);
    }

    public OfficeException(String errorCode, String userTip) {
        super(OfficeConstants.OFFICE_MODULE_NAME, errorCode, userTip);
    }

}
