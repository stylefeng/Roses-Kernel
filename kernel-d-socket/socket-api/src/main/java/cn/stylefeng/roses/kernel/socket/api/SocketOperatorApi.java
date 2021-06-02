package cn.stylefeng.roses.kernel.socket.api;

/**
 * Socket通用操作类
 * <p>
 * 可通过该类直接发送消息，每一个Socket实现的子模块必须实现该接口，以提供统一的操作API
 *
 * @author majianguo
 * @date 2021/6/2 上午9:25
 */
public interface SocketOperatorApi {

    /**
     * 发送消息到指定会话
     *
     * @param sessionId 会话ID(会话ID具体看业务的实现，WebSocket中使用的是用户传入的formId)
     * @param msg       消息体
     * @author majianguo
     * @date 2021/6/2 上午9:35
     **/
    void sendMsgOfSession(String sessionId, Object msg);

    /**
     * 发送消息到所有会话
     *
     * @param msg 消息体
     * @author majianguo
     * @date 2021/6/2 上午9:35
     **/
    void sendMsgOfAllSession(Object msg);

    /**
     * 监听指定类型消息
     * <p>
     * 1.该方法每调用一次即注册一个监听,同一个消息类型多次调用只有最后一次生效
     *
     * @param msgType           消息类型
     * @param callbackInterface 消息监听器
     * @author majianguo
     * @date 2021/6/2 上午9:54
     **/
    void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface);
}
