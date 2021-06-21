package cn.stylefeng.roses.kernel.socket.business.websocket.pojo;

import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import lombok.Data;

/**
 * WebSocket交互通用对象
 * <p>
 * 特殊说明一下serverMsgType和clientMsgType的区别
 * 1.serverMsgType字段是服务端发送给客户端的字段
 * 例如：服务端发送一个系统消息(type:100001),客户端接收到该消息以后判断需不需要处理，不需要处理跳过即可
 * <p>
 * 2.clientMsgType字段是客户端发送给服务器的字段
 * 例如：客户端发送给服务器一个心跳消息(type:299999)，服务端如果需要处理该消息就注册一个该消息的监听器，
 * 那么收到消息服务端会把消息推送给对应的监听器，接口见{@link SocketOperatorApi#msgTypeCallback}
 *
 * @author majianguo
 * @date 2021/6/1 下午2:56
 */
@Data
public class WebSocketMessageDTO {

    /**
     * 服务端发送的消息类型(客户端如果需要监听该消息类型，注册对应的消息处理器即可)
     */
    private String serverMsgType;

    /**
     * 客户端发送的消息类型(服务端需要处理的消息类型)
     */
    private String clientMsgType;

    /**
     * 目标Id
     */
    private String toUserId;

    /**
     * 发送者ID
     */
    private String formUserId;

    /**
     * 具体发送的数据
     */
    private Object data;

}
