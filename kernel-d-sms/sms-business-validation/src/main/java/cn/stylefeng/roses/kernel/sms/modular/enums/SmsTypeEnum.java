package cn.stylefeng.roses.kernel.sms.modular.enums;

import lombok.Getter;

/**
 * 短信类型枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 21:30
 */
@Getter
public enum SmsTypeEnum {

    /**
     * 验证类短信
     */
    SMS(1, "验证类短信"),

    /**
     * 纯发送短信
     */
    MESSAGE(2, "纯发送短信");

    private final Integer code;

    private final String message;

    SmsTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
