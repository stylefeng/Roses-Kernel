package cn.stylefeng.roses.kernel.jwt.api.exception.enums;

import cn.stylefeng.roses.kernel.jwt.api.constants.JwtConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * jwt异常的状态码
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum JwtExceptionEnum implements AbstractExceptionEnum {

    /**
     * jwt解析异常
     */
    JWT_PARSE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "01", "jwt解析错误！jwt为：{}"),

    /**
     * jwt过期了
     */
    JWT_EXPIRED_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "02", "jwt过期了！jwt为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    JwtExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
