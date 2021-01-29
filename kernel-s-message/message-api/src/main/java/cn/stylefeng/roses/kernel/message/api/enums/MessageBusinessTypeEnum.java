package cn.stylefeng.roses.kernel.message.api.enums;

import lombok.Getter;

/**
 * 消息业务类型枚举
 *
 * @author liuhanqing
 * @date 2021/1/4 22:26
 */
@Getter
public enum MessageBusinessTypeEnum {

    /**
     * 已读
     */
    SYS_NOTICE("sys_notice", "通知", "/sysNotice/detail");

    private final String code;

    private final String name;

    private final String url;


    MessageBusinessTypeEnum(String code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public static MessageBusinessTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (MessageBusinessTypeEnum flagEnum : MessageBusinessTypeEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum;
            }
        }
        return null;
    }

    public static String getName(String code) {
        if (code == null) {
            return null;
        }
        for (MessageBusinessTypeEnum flagEnum : MessageBusinessTypeEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum.name;
            }
        }
        return null;
    }

}
