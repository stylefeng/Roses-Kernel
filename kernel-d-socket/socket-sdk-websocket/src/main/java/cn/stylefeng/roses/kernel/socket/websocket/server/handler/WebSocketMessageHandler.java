package cn.stylefeng.roses.kernel.socket.websocket.server.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.enums.ClientMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.message.SocketMsgCallbackInterface;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.message.SocketMessageCenter;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import cn.stylefeng.roses.kernel.socket.websocket.pojo.WebSocketMessageDTO;
import cn.stylefeng.roses.kernel.socket.websocket.server.bind.ChannelIdAndUserBindCenter;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;
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
        ChannelIdAndUserBindCenter.addSocketChannel(aioChannel);
    }

    @Override
    public void channelClosed(SocketChannel aioChannel) {
        log.info(aioChannel.getChannelId() + " disconnected");
        // 获取用户ID
        String userId = ChannelIdAndUserBindCenter.getUserId(aioChannel.getChannelId());
        if (ObjectUtil.isNotEmpty(userId)) {
            // 根据用户ID关闭会话
            SessionCenter.closed(userId);
        }
        ChannelIdAndUserBindCenter.closed(aioChannel.getChannelId());
    }

    @Override
    public void channelRead0(SocketChannel socketChannel, WebSocketFrame webSocketFrame) {

        if (webSocketFrame instanceof TextWebSocketFrame) {
            String data = new String(webSocketFrame.getPayloadData(), StandardCharsets.UTF_8);

            // 转换为Java对象
            WebSocketMessageDTO webSocketMessageDTO = JSON.toJavaObject(JSON.parseObject(data), WebSocketMessageDTO.class);

            // 心跳包
            if (ClientMessageTypeEnum.USER_HEART.getCode().equals(webSocketMessageDTO.getClientMsgType())) {
                // 更新用户最后活跃时间
                String userId = ChannelIdAndUserBindCenter.getUserId(socketChannel.getChannelId());
                if (ObjectUtil.isNotEmpty(userId)) {
                    SocketSession<GettySocketOperator> session = SessionCenter.getSessionByUserId(userId);
                    session.setLastActiveTime(System.currentTimeMillis());
                }
            }

            // 用户ID为空不处理直接跳过
            if (ObjectUtil.isEmpty(webSocketMessageDTO.getFormUserId())) {
                return;
            }

            // 维护通道和用户ID的绑定关系
            if (!ChannelIdAndUserBindCenter.isBind(webSocketMessageDTO.getFormUserId())) {
                ChannelIdAndUserBindCenter.bind(socketChannel.getChannelId(), webSocketMessageDTO.getFormUserId());

                // 创建api的会话对象
                SocketSession<GettySocketOperator> socketSession = new SocketSession<>();
                socketSession.setUserId(webSocketMessageDTO.getFormUserId());
                socketSession.setSocketOperatorApi(new GettySocketOperator(socketChannel));
                socketSession.setConnectionTime(System.currentTimeMillis());

                // 维护会话
                SessionCenter.addSocketSession(socketSession);
            }

            // 更新最后会话时间
            SocketSession<GettySocketOperator> userSession = SessionCenter.getSessionByUserId(webSocketMessageDTO.getFormUserId());
            userSession.setLastActiveTime(System.currentTimeMillis());

            // 找到该消息的处理器
            SocketMsgCallbackInterface socketMsgCallbackInterface = SocketMessageCenter.getSocketMsgCallbackInterface(webSocketMessageDTO.getClientMsgType());
            if (ObjectUtil.isNotEmpty(socketMsgCallbackInterface)) {
                // 获取会话
                SocketSession<GettySocketOperator> session = SessionCenter.getSessionByUserId(webSocketMessageDTO.getFormUserId());

                // 触发回调
                socketMsgCallbackInterface.callback(webSocketMessageDTO.getClientMsgType(), webSocketMessageDTO, session);
            } else {
                socketChannel.writeAndFlush(new TextWebSocketFrame("{\"code\":\"404\"}"));
            }
        }
    }

}
