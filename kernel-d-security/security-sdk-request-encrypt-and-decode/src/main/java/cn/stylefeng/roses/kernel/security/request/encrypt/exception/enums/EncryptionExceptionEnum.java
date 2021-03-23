package cn.stylefeng.roses.kernel.security.request.encrypt.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.security.request.encrypt.constants.EncryptionConstants;
import lombok.Getter;

/**
 * 请求解密，响应加密 异常枚举
 *
 * @author luojie
 * @date 2021/3/23 12:54
 */
@Getter
public enum EncryptionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 请求的json解析异常
     */
    REQUEST_JSON_PARSE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + EncryptionConstants.ENCRYPTION_EXCEPTION_STEP_CODE + "01", "请求的json解析异常"),

    /**
     * 请求的json格式错误，未包含加密的data字段数据以及加密的key字段
     */
    REQUEST_JSON_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + EncryptionConstants.ENCRYPTION_EXCEPTION_STEP_CODE + "02", "请求的json格式错误，未包含加密的data字段数据以及加密的key字段"),

    /**
     * 解密失败
     */
    RSA_DECRYPT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + EncryptionConstants.ENCRYPTION_EXCEPTION_STEP_CODE + "03", "解密失败"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    EncryptionExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
