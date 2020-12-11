package cn.stylefeng.roses.kernel.dsctn.api.exception;

import cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 数据源容器操作异常
 *
 * @author fengshuonan
 * @date 2020/10/31 22:10
 */
public class DatasourceContainerException extends ServiceException {

    public DatasourceContainerException(AbstractExceptionEnum exception) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception);
    }

    public DatasourceContainerException(AbstractExceptionEnum exception, String userTip) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
