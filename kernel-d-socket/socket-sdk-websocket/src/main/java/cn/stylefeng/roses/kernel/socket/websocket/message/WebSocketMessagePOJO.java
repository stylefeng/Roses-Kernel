package cn.stylefeng.roses.kernel.socket.websocket.message;

import lombok.Data;

/**
 * WebSocket交互通用对象
 *
 * @author majianguo
 * @date 2021/6/1 下午2:56
 */
@Data
public class WebSocketMessagePOJO {

    /**
     * 消息类型
     */
    private String type;

    /**
     * 目标Id
     */
    private String toId;

    /**
     * 发送者ID
     */
    private String formId;

    /**
     * 数据
     */
    private String data;
}
