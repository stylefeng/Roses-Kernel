package cn.stylefeng.roses.kernel.rule.exception.enums.http;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.BUSINESS_ERROR_TYPE_CODE;
import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.RULE_EXCEPTION_STEP_CODE;

/**
 * servlet相关业务异常
 *
 * @author fengshuonan
 * @date 2020/10/15 17:39
 */
@Getter
public enum ServletExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取不到http context异常
     */
    HTTP_CONTEXT_ERROR(BUSINESS_ERROR_TYPE_CODE + RULE_EXCEPTION_STEP_CODE + "01", "获取不到http context，请确认当前请求是http请求");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ServletExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
