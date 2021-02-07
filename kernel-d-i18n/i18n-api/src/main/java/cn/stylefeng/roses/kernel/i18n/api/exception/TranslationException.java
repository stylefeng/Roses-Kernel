package cn.stylefeng.roses.kernel.i18n.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.i18n.api.constants.TranslationConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 多语言翻译的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class TranslationException extends ServiceException {

    public TranslationException(AbstractExceptionEnum exception, Object... params) {
        super(TranslationConstants.I18N_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public TranslationException(AbstractExceptionEnum exception) {
        super(TranslationConstants.I18N_MODULE_NAME, exception);
    }

}
