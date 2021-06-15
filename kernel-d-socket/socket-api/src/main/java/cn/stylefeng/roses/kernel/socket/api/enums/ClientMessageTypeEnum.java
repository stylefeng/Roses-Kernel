package cn.stylefeng.roses.kernel.socket.api.enums;

import lombok.Getter;

/**
 * 客户端消息类型枚举
 * <p>
 * 说明：该枚举适用于服务器接收到客户端发来的消息，判断消息类型时使用
 *
 * @author majianguo
 * @date 2021/6/3 上午9:14
 */
@Getter
public enum ClientMessageTypeEnum {

    /**
     * 用户连接鉴权
     */
    USER_CONNECTION_AUTHENTICATION("200000", "用户连接鉴权"),

    /**
     * 用户心跳消息类型
     */
    USER_HEART("299999", "用户心跳消息类型");

    private final String code;

    private final String name;

    ClientMessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
