package cn.stylefeng.roses.kernel.message.api.enums;

import lombok.Getter;

/**
 * 消息阅读状态
 *
 * @author liuhanqing
 * @date 2021/1/4 22:26
 */
@Getter
public enum MessageReadFlagEnum {

    /**
     * 未读
     */
    UNREAD(0, "未读"),

    /**
     * 已读
     */
    READ(1, "已读");

    private final Integer code;

    private final String name;

    MessageReadFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        if (code == null) {
            return null;
        }
        for (MessageReadFlagEnum flagEnum : MessageReadFlagEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum.name;
            }
        }
        return null;
    }

}
