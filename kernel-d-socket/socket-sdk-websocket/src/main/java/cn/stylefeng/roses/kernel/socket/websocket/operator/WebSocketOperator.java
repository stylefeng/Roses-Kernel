package cn.stylefeng.roses.kernel.socket.websocket.operator;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.socket.api.exception.enums.SocketExceptionEnum;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import cn.stylefeng.roses.kernel.socket.websocket.pojo.WebSocketMessageDTO;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;

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
    public void sendMsgOfUserSession(String msgType, String userId, Object msg) {
        // 根据用户ID获取会话
        SocketSession<GettySocketOperator> socketSession = SessionCenter.getSessionByUserId(userId);
        if (ObjectUtil.isEmpty(socketSession)) {
            throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
        }
        WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
        webSocketMessageDTO.setData(msg);
        webSocketMessageDTO.setServerMsgType(msgType);
        // 发送内容
        socketSession.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
    }

    @Override
    public void sendMsgOfAllUserSession(String msgType, Object msg) {
        for (SocketSession<GettySocketOperator> socketSession : SessionCenter.getSocketSessionMap().values()) {
            WebSocketMessageDTO webSocketMessageDTO = new WebSocketMessageDTO();
            webSocketMessageDTO.setData(msg);
            webSocketMessageDTO.setServerMsgType(msgType);
            // 发送内容
            socketSession.getSocketOperatorApi().writeAndFlush(webSocketMessageDTO);
        }
    }

    @Override
    public void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface) {
        SocketMessageCenter.setMessageListener(msgType, callbackInterface);
    }
}
