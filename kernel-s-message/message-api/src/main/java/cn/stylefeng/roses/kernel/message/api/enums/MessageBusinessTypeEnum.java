package cn.stylefeng.roses.kernel.message.api.enums;

import lombok.Getter;

/**
 * 消息阅读状态
 *
 * @author liuhanqing
 * @date 2021/1/4 22:26
 */
@Getter
public enum MessageBusinessTypeEnum {

    /**
     * 已读
     */
    SYS_NOTICE("sys_notice", "系统通知");

    private String code;

    private String name;

    MessageBusinessTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
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
