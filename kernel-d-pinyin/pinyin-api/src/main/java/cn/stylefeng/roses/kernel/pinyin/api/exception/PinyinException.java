package cn.stylefeng.roses.kernel.pinyin.api.exception;

import cn.stylefeng.roses.kernel.pinyin.api.constants.PinyinConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import lombok.Getter;

/**
 * 拼音异常
 *
 * @author fengshuonan
 * @date 2020/12/3 18:10
 */
@Getter
public class PinyinException extends ServiceException {

    public PinyinException(AbstractExceptionEnum exceptionEnum, String userTip) {
        super(PinyinConstants.PINYIN_MODULE_NAME, exceptionEnum.getErrorCode(), userTip);
    }

    public PinyinException(AbstractExceptionEnum exceptionEnum) {
        super(PinyinConstants.PINYIN_MODULE_NAME, exceptionEnum);
    }

}
