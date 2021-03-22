package cn.stylefeng.roses.kernel.mongodb.api.exception;

import cn.stylefeng.roses.kernel.mongodb.api.constants.MongodbConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2021/13/17 23:59
 */
public class MongodbException extends ServiceException {

    public MongodbException(AbstractExceptionEnum exception) {
        super(MongodbConstants.MONGODB_MODULE_NAME, exception);
    }

}
