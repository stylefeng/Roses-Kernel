package cn.stylefeng.roses.kernel.socket.websocket.operator.channel;

import com.gettyio.core.channel.SocketChannel;
import com.gettyio.expansion.handler.codec.websocket.frame.TextWebSocketFrame;

/**
 * Socket操作类实现
 * <p>
 * 这里使用的是Getty,所以对Getty的SocketChannel对象做简单封装
 *
 * @author majianguo
 * @date 2021/6/1 下午3:41
 */
public class GettySocketOperator implements GettyChannelExpandInterFace {

    /**
     * 实际操作的通道
     */
    private SocketChannel socketChannel;

    public GettySocketOperator(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void writeAndFlush(Object obj) {
        if (obj instanceof String) {
            // 处理WebSocket的数据
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(obj.toString());
            socketChannel.writeAndFlush(textWebSocketFrame);
            return;
        }
        socketChannel.writeAndFlush(obj);
    }

    @Override
    public void writeToChannel(Object obj) {
        if (obj instanceof String) {
            // 处理WebSocket的数据
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(obj.toString());
            socketChannel.writeToChannel(textWebSocketFrame);
            return;
        }
        socketChannel.writeToChannel(obj);
    }

    @Override
    public void close() {
        socketChannel.close();
    }

    @Override
    public boolean isInvalid() {
        return socketChannel.isInvalid();
    }
}
