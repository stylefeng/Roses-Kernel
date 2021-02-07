package cn.stylefeng.roses.kernel.rule.exception.enums.defaults;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.FIRST_LEVEL_WIDE_CODE;
import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.USER_OPERATION_ERROR_TYPE_CODE;

/**
 * 源于用户操作的异常枚举，比如参数错误，用户安装版本过低，用户支付超时等问题
 *
 * @author fengshuonan
 * @date 2020/10/15 17:31
 */
@Getter
public enum DefaultUserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用户端错误（一级宏观错误码）
     */
    USER_OPERATION_ERROR(USER_OPERATION_ERROR_TYPE_CODE + FIRST_LEVEL_WIDE_CODE, "执行失败，请检查操作是否正常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultUserExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
