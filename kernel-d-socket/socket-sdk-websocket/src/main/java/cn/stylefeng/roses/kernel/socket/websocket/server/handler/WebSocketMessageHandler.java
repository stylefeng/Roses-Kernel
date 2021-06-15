package cn.stylefeng.roses.kernel.socket.websocket.server.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.socket.api.enums.ClientMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.enums.ServerMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.pojo.WebSocketMessageDTO;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import com.alibaba.fastjson.JSON;
import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;
import com.gettyio.expansion.handler.codec.websocket.frame.TextWebSocketFrame;
import com.gettyio.expansion.handler.codec.websocket.frame.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 消息监听处理器
 *
 * @author majianguo
 * @date 2021/6/1 下午2:35
 */
@Slf4j
public class WebSocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    public void channelAdded(SocketChannel aioChannel) {
        log.info(aioChannel.getChannelId() + " connection successful.");
    }

    @Override
    public void channelClosed(SocketChannel aioChannel) {
        log.info(aioChannel.getChannelId() + " disconnected");
        SessionCenter.closed(aioChannel.getChannelId());
    }

    @Override
    public void channelRead0(SocketChannel socketChannel, WebSocketFrame webSocketFrame) {

        if (webSocketFrame instanceof TextWebSocketFrame) {
            String data = new String(webSocketFrame.getPayloadData(), StandardCharsets.UTF_8);

            // 转换为Java对象
            WebSocketMessageDTO WebSocketMessageDTO = JSON.toJavaObject(JSON.parseObject(data), WebSocketMessageDTO.class);

            // 心跳包
            if (ClientMessageTypeEnum.USER_HEART.getCode().equals(WebSocketMessageDTO.getClientMsgType())) {
                // 更新会话最后活跃时间
                SocketSession<GettySocketOperator> session = SessionCenter.getSessionBySessionId(socketChannel.getChannelId());
                if (ObjectUtil.isNotEmpty(session)) {
                    session.setLastActiveTime(System.currentTimeMillis());
                }
            }

            // 用户ID为空不处理直接跳过
            if (ObjectUtil.isEmpty(WebSocketMessageDTO.getFormUserId())) {
                return;
            }

            // 维护通道是否已初始化
            SocketSession<GettySocketOperator> socketSession = SessionCenter.getSessionBySessionId(socketChannel.getChannelId());
            if (ObjectUtil.isEmpty(socketSession) && ClientMessageTypeEnum.USER_CONNECTION_AUTHENTICATION.getCode().equals(WebSocketMessageDTO.getClientMsgType())) {
                // 操作api包装
                GettySocketOperator gettySocketOperator = new GettySocketOperator(socketChannel);

                // 回复消息
                WebSocketMessageDTO replyMsg = new WebSocketMessageDTO();
                replyMsg.setServerMsgType(ServerMessageTypeEnum.SYS_REPLY_MSG_TYPE.getCode());
                replyMsg.setToUserId(WebSocketMessageDTO.getFormUserId());

                try {
                    // 校验token是否合法
                    JwtContext.me().validateTokenWithException(WebSocketMessageDTO.getData().toString());

                    // 设置回复内容
                    replyMsg.setData(socketChannel.getChannelId());

                    // 创建会话对象
                    socketSession = new SocketSession<>();
                    socketSession.setSessionId(socketChannel.getChannelId());
                    socketSession.setUserId(WebSocketMessageDTO.getFormUserId());
                    socketSession.setSocketOperatorApi(gettySocketOperator);
                    socketSession.setConnectionTime(System.currentTimeMillis());

                    // 维护会话
                    SessionCenter.addSocketSession(socketSession);
                } finally {
                    // 回复消息
                    gettySocketOperator.writeAndFlush(replyMsg);
                }
                return;
            }

            // 会话建立成功执行业务逻辑
            if (ObjectUtil.isNotEmpty(socketSession)) {

                // 更新最后会话时间
                socketSession.setLastActiveTime(System.currentTimeMillis());

                // 找到该消息的处理器
                SocketMsgCallbackInterface socketMsgCallbackInterface = SocketMessageCenter.getSocketMsgCallbackInterface(WebSocketMessageDTO.getClientMsgType());
                if (ObjectUtil.isNotEmpty(socketMsgCallbackInterface)) {
                    // 触发回调
                    socketMsgCallbackInterface.callback(WebSocketMessageDTO.getClientMsgType(), WebSocketMessageDTO, socketSession);
                } else {
                    socketChannel.writeAndFlush(new TextWebSocketFrame("{\"serverMsgType\":\"404\"}"));
                }
            }
        }
    }
}
