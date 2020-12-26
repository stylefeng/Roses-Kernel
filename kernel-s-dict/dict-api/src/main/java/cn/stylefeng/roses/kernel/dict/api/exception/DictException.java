package cn.stylefeng.roses.kernel.dict.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 字典模块的异常
 *
 * @author fengshuonan
 * @date 2020/10/29 11:57
 */
public class DictException extends ServiceException {

    public DictException(AbstractExceptionEnum exception, Object... params) {
        super(DictConstants.DICT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public DictException(AbstractExceptionEnum exception) {
        super(DictConstants.DICT_MODULE_NAME, exception);
    }

}
