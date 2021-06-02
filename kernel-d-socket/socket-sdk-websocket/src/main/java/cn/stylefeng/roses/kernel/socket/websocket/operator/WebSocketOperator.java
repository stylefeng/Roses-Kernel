package cn.stylefeng.roses.kernel.socket.websocket.operator;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.socket.api.exception.enums.SocketExceptionEnum;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import cn.stylefeng.roses.kernel.socket.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;

import java.util.Collection;

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
    public void sendMsgOfSession(String sessionId, Object msg) {
        // 获取会话
        SocketSession<GettySocketOperator> socketSession = SessionCenter.getSessionById(sessionId);
        if (ObjectUtil.isEmpty(socketSession)) {
            throw new SocketException(SocketExceptionEnum.SESSION_NOT_EXIST);
        }

        // 发送内容
        socketSession.getSocketOperatorApi().writeAndFlush(msg);
    }

    @Override
    public void sendMsgOfAllSession(Object msg) {
        // 获取所有会话
        Collection<SocketSession<GettySocketOperator>> socketSessions = SessionCenter.getSocketSessionMap().values();
        if (ObjectUtil.isNotEmpty(socketSessions)) {
            // 给所有会话发送消息
            for (SocketSession<?> socketSession : socketSessions) {
                // 发送内容
                socketSession.getSocketOperatorApi().writeAndFlush(msg);
            }
        }
    }

    @Override
    public void msgTypeCallback(String msgType, SocketMsgCallbackInterface callbackInterface) {
        SocketMessageCenter.setMessageListener(msgType, callbackInterface);
    }
}
