package cn.stylefeng.roses.kernel.socket.api.enums;

import lombok.Getter;

/**
 * 服务端消息类型枚举
 * <p>
 * 说明：该枚举适用于服务器推送给客户端消息时使用
 *
 * @author majianguo
 * @date 2021/6/3 上午9:14
 */
@Getter
public enum ServerMessageTypeEnum {

    /**
     * 系统通知消息类型
     */
    SYS_NOTICE_MSG_TYPE("100001", "系统通知消息类型"),

    /**
     * 连接消息回复
     */
    SYS_REPLY_MSG_TYPE("100002", "连接消息回复");

    private final String code;

    private final String name;

    ServerMessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
