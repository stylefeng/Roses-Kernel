package cn.stylefeng.roses.kernel.socket.business.websocket.operator;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.socket.api.exception.enums.SocketExceptionEnum;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.business.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.business.websocket.pojo.WebSocketMessageDTO;
import cn.stylefeng.roses.kernel.socket.business.websocket.session.SessionCenter;
import cn.stylefeng.roses.kernel.socket.business.websocket.operator.channel.GunsSocketOperator;

import java.util.Collection;
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
    public void sendMsgOfUserSessionBySessionId(String msgType, String sessionId, Object msg) throws SocketException {
        SocketSession<GunsSocketOperator> session = SessionCenter.getSessionBySessionId(sessionId);
        if (ObjectUtil.isEmpty(session)) {
            throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
        }
        WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
        webSocketMessageDTO.setData(msg);
        webSocketMessageDTO.setServerMsgType(msgType);
        session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
    }

    @Override
    public void sendMsgOfUserSession(String msgType, String userId, Object msg) throws SocketException {
        // 根据用户ID获取会话
        List<SocketSession<GunsSocketOperator>> socketSessionList = SessionCenter.getSessionByUserIdAndMsgType(userId);
        if (ObjectUtil.isEmpty(socketSessionList)) {
            throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
        }
        WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
        webSocketMessageDTO.setData(msg);
        webSocketMessageDTO.setServerMsgType(msgType);
        for (SocketSession<GunsSocketOperator> session : socketSessionList) {
            // 发送内容
            session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
        }
    }

    @Override
    public void sendMsgOfAllUserSession(String msgType, Object msg) {
        Collection<List<SocketSession<GunsSocketOperator>>> values = SessionCenter.getSocketSessionMap().values();
        WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
        webSocketMessageDTO.setData(msg);
        webSocketMessageDTO.setServerMsgType(msgType);
        for (List<SocketSession<GunsSocketOperator>> sessions : values) {
            for (SocketSession<GunsSocketOperator> session : sessions) {
                // 找到该类型的通道
                if (session.getMessageType().equals(msgType)) {
                    session.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
                }
            }
        }
    }

    @Override
    public void closeSocketBySocketId(String socketId) {
         SessionCenter.closed(socketId);
    }

    @Override
    public void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface) {
        SocketMessageCenter.setMessageListener(msgType, callbackInterface);
    }
}
