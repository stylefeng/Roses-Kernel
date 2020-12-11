package cn.stylefeng.roses.kernel.validator.exception.enums;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants;
import lombok.Getter;

/**
 * 参数校验错误
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum ValidatorExceptionEnum implements AbstractExceptionEnum {

    /**
     * 参数错误
     */
    PARAM_VALIDATE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "01", "参数校验失败，请检查参数的传值是否正确，具体信息：{}"),

    /**
     * 中断执行
     */
    INTERRUPT_EXECUTION(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "02", "满足自定义策略要求,程序已中断执行!"),

    /**
     * 数据库字段值唯一性校验出错，参数不完整
     */
    TABLE_UNIQUE_VALIDATE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "03", "数据库字段值唯一性校验出错，参数不完整，字段存在空值：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ValidatorExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
