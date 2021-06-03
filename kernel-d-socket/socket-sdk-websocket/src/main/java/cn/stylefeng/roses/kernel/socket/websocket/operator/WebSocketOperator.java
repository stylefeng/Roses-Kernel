package cn.stylefeng.roses.kernel.socket.websocket.operator;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.enums.ServerMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.socket.api.exception.enums.SocketExceptionEnum;
import cn.stylefeng.roses.kernel.socket.websocket.message.WebSocketMessagePOJO;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import cn.stylefeng.roses.kernel.socket.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;
import com.alibaba.fastjson.JSON;
import com.gettyio.expansion.handler.codec.websocket.frame.TextWebSocketFrame;

import java.util.List;

/**
 * WebSocket操作实现类
 * <p>
 * 如果是Spring boot项目，通过注入SocketOperatorApi接口操作socket，需将本来交给Spring管理
 *
 * @author majianguo
 * @date 2021/6/2 上午10:41
 */
public class WebSocketOperator implements SocketOperatorApi {

    @Override
    public void sendMsgOfUserSession(ServerMessageTypeEnum msgType, String userId, Object msg) {
        // 根据用户ID获取会话
        SocketSession<GettySocketOperator> socketSession = SessionCenter.getSessionByUserId(userId);
        if (ObjectUtil.isEmpty(socketSession)) {
            throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
        }

        // 判断用户是否监听
        if (socketSession.getMessageTypes().contains(msgType.getCode())) {
            WebSocketMessagePOJO webSocketMessagePOJO = new WebSocketMessagePOJO();
            webSocketMessagePOJO.setData(msg);
            webSocketMessagePOJO.setType(msgType.getCode());
            // 发送内容
            socketSession.getSocketOperatorApi().writeAndFlush(webSocketMessagePOJO);
        }
    }

    @Override
    public void sendMsgOfAllUserSession(ServerMessageTypeEnum msgType, Object msg) {
        // 获取监听该消息类型的所有会话
        List<SocketSession<GettySocketOperator>> socketSessionList = SessionCenter.getSocketSessionByMsgType(msgType.getCode());

        if (ObjectUtil.isNotEmpty(socketSessionList)) {
            // 给所有会话发送消息
            for (SocketSession<GettySocketOperator> socketSession : socketSessionList) {
                WebSocketMessagePOJO webSocketMessagePOJO = new WebSocketMessagePOJO();
                webSocketMessagePOJO.setData(msg);
                webSocketMessagePOJO.setType(msgType.getCode());
                // 发送内容
                socketSession.getSocketOperatorApi().writeAndFlush(webSocketMessagePOJO);
            }
        }
    }

    @Override
    public void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface) {
        SocketMessageCenter.setMessageListener(msgType, callbackInterface);
    }
}
