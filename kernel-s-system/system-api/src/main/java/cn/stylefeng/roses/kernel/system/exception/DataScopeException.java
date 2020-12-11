package cn.stylefeng.roses.kernel.system.exception;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;

/**
 * 数据范围异常，当前用户没有操作该数据的权限
 *
 * @author fengshuonan
 * @date 2020/11/4 15:50
 */
public class DataScopeException extends ServiceException {

    public DataScopeException(AbstractExceptionEnum exception,String userTip) {

        super(SystemConstants.SYSTEM_MODULE_NAME, exception.getErrorCode(), userTip);
    }

    public DataScopeException(AbstractExceptionEnum exception) {
        super(SystemConstants.SYSTEM_MODULE_NAME, exception);
    }

}
