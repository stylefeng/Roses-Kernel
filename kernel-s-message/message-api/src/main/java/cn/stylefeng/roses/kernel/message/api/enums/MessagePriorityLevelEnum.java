package cn.stylefeng.roses.kernel.message.api.enums;

import lombok.Getter;

/**
 * 消息优先级
 *
 * @author liuhanqing
 * @date 2021/1/8 13:26
 */
@Getter
public enum MessagePriorityLevelEnum {

    /**
     * 高
     */
    HIGH("high", "高"),

    /**
     * 中
     */
    MIDDLE("middle", "中"),

    /**
     * 低
     */
    LOW("low", "低");

    private final String code;

    private final String name;

    MessagePriorityLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        if (code == null) {
            return null;
        }
        for (MessagePriorityLevelEnum flagEnum : MessagePriorityLevelEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum.name;
            }
        }
        return null;
    }

}
