package cn.stylefeng.roses.kernel.sms.modular.enums;

import lombok.Getter;

/**
 * 短信发送业务枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 21:29
 */
@Getter
public enum SmsSendSourceEnum {

    /**
     * APP
     */
    APP(1),

    /**
     * PC
     */
    PC(2),

    /**
     * OTHER
     */
    OTHER(3);

    private final Integer code;

    SmsSendSourceEnum(Integer code) {
        this.code = code;
    }
}
