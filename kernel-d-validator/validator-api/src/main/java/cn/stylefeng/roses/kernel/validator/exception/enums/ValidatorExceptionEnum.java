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
     * Parameter传参，请求参数缺失异常
     */
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "01", "Parameter传参，请求参数缺失异常，参数名：{}，类型为：{}"),

    /**
     * 请求数据经过httpMessageConverter出错
     */
    HTTP_MESSAGE_CONVERTER_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "02", "请求Json数据格式错误或Json字段格式转化问题"),

    /**
     * 不受支持的媒体类型
     */
    HTTP_MEDIA_TYPE_NOT_SUPPORT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "03", "请求的http media type不合法"),

    /**
     * 不受支持的http请求方法
     */
    HTTP_METHOD_NOT_SUPPORT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "04", "当前接口不支持{}方式请求"),

    /**
     * 404找不到资源
     */
    NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "05", "404：找不到请求的资源"),

    /**
     * 参数校验失败
     * <p>
     * 拦截@Valid和@Validated校验失败返回的错误提示
     */
    VALIDATED_RESULT_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "06", "参数校验失败，请检查参数的传值是否正确，具体信息：{}"),

    /**
     * 数据库字段值唯一性校验出错，参数不完整
     */
    TABLE_UNIQUE_VALIDATE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "07", "数据库字段值唯一性校验出错，具体信息：{}"),

    /**
     * 验证码为空
     */
    CAPTCHA_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "08", "验证码参数不能为空"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "09", "验证码错误");

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
