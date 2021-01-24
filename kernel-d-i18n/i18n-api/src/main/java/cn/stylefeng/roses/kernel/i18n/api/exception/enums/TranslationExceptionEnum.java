package cn.stylefeng.roses.kernel.i18n.api.exception.enums;

import cn.stylefeng.roses.kernel.i18n.api.constants.TranslationConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 多语言翻译的异常枚举
 *
 * @author fengshuonan
 * @date 2021/1/24 16:40
 */
@Getter
public enum TranslationExceptionEnum implements AbstractExceptionEnum {

    /**
     * 多语言xx
     */
    JWT_PARSE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TranslationConstants.I18N_EXCEPTION_STEP_CODE + "01", "jwt解析错误！jwt为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    TranslationExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
