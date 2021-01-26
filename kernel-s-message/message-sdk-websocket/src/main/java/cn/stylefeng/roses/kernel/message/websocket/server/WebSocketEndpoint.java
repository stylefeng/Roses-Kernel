package cn.stylefeng.roses.kernel.message.websocket.server;

import cn.stylefeng.roses.kernel.message.websocket.manager.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket服务端
 *
 * @author liuhanqing
 * @date 2021/1/24 22:26
 */
@Slf4j
@Component
@ServerEndpoint("/message/websocket/{userId}")
public class WebSocketEndpoint {


    /**
     * 连接建立成功后调用
     *
     * @param userId  用户id
     * @param session 用户websocketSession
     * @author liuhanqing
     * @date 2021/1/24 22:27
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") Long userId, Session session) {
        // 添加到链接管理
        WebSocketManager.add(userId, session);
        // 返回消息
//        session.getAsyncRemote().sendText("WebSocket连接成功");
    }

    /**
     * 连接关闭时调用
     *
     * @author liuhanqing
     * @date 2021/1/24 22:29
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") Long userId, Session session) {
        // 从map中删除
        WebSocketManager.removeSession(userId, session);
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message 客户端发送过来的消息
     * @param session 用户websocketSession
     * @author liuhanqing
     * @date 2021/1/24 22:29
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时回调
     *
     * @param session 用户信息
     * @param error   错误
     * @author liuhanqing
     * @date 2021/1/24 22:29
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误");
        error.printStackTrace();
    }

}