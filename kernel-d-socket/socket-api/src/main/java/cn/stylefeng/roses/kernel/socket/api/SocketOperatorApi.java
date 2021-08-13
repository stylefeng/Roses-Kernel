package cn.stylefeng.roses.kernel.socket.api;

import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;

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
     * @param msgType   消息类型可参考{@link cn.stylefeng.roses.kernel.socket.api.enums}枚举类
     * @param sessionId 会话ID
     * @param msg       消息体
     * @author majianguo
     * @date 2021/6/11 下午2:19
     **/
    void sendMsgOfUserSessionBySessionId(String msgType, String sessionId, Object msg) throws SocketException;

    /**
     * 发送消息到指定用户的所有会话
     * <p>
     * 如果用户同一个消息类型建立了多个会话，则统一全部发送
     *
     * @param msgType 消息类型可参考{@link cn.stylefeng.roses.kernel.socket.api.enums}枚举类
     * @param userId  用户ID
     * @param msg     消息体
     * @author majianguo
     * @date 2021/6/2 上午9:35
     **/
    void sendMsgOfUserSession(String msgType, String userId, Object msg) throws SocketException;

    /**
     * 发送消息到所有会话
     *
     * @param msgType 消息类型可参考{@link cn.stylefeng.roses.kernel.socket.api.enums}枚举类
     * @param msg     消息体
     * @author majianguo
     * @date 2021/6/2 上午9:35
     **/
    void sendMsgOfAllUserSession(String msgType, Object msg);

    /**
     * 根据会话id关闭会话
     *
     * @param socketId 会话id
     * @author majianguo
     * @date 2021/8/13 16:00
     **/
    void closeSocketBySocketId(String socketId);

    /**
     * 监听指定类型消息
     * <p>
     * 1.该方法每调用一次即注册一个监听,同一个消息类型多次调用只有最后一次生效
     *
     * @param msgType           消息类型可参考{@link cn.stylefeng.roses.kernel.socket.api.enums}枚举类
     * @param callbackInterface 消息监听器
     * @author majianguo
     * @date 2021/6/2 上午9:54
     **/
    void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface);
}
