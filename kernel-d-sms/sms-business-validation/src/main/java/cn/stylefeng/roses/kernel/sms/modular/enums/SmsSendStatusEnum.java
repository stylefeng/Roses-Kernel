package cn.stylefeng.roses.kernel.sms.modular.enums;

import lombok.Getter;

/**
 * 短信发送状态枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 21:29
 */
@Getter
public enum SmsSendStatusEnum {

    /**
     * 未发送
     */
    WAITING(0, "未发送"),

    /**
     * 发送成功
     */
    SUCCESS(1, "发送成功"),

    /**
     * 发送失败
     */
    FAILED(2, "发送失败"),

    /**
     * 失效
     */
    INVALID(3, "失效");

    private final Integer code;

    private final String message;

    SmsSendStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
