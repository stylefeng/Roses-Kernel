package cn.stylefeng.roses.kernel.db.api.exception;

import cn.stylefeng.roses.kernel.db.api.constants.DbConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 数据库操作异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class DaoException extends ServiceException {

    public DaoException(AbstractExceptionEnum exception) {
        super(DbConstants.DB_MODULE_NAME, exception);
    }

}
