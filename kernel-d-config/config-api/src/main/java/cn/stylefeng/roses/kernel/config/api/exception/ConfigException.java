package cn.stylefeng.roses.kernel.config.api.exception;

import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class ConfigException extends ServiceException {

    public ConfigException(String errorCode, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, errorCode, userTip);
    }

    public ConfigException(AbstractExceptionEnum exception) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception);
    }

    public ConfigException(AbstractExceptionEnum exception, String userTip) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
