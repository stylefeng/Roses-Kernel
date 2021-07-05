package cn.stylefeng.roses.kernel.socket.api.enums;

import lombok.Getter;

/**
 * 服务端监听器枚举
 * <p>
 * 说明：该枚举适用于服务端监听首次连接和断开连接
 *
 * @author majianguo
 * @date 2021/6/3 上午9:14
 */
@Getter
public enum SystemMessageTypeEnum {

    /**
     * 监听首次连接
     */
    SYS_LISTENER_ONOPEN("S00001", "监听首次连接"),

    /**
     * 监听断开连接
     */
    SYS_LISTENER_ONCLOSE("S00002", "监听断开连接"),

    /**
     * 监听异常信息
     */
    SYS_LISTENER_ONERROR("S00003", "监听异常信息");

    private final String code;

    private final String name;

    SystemMessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
