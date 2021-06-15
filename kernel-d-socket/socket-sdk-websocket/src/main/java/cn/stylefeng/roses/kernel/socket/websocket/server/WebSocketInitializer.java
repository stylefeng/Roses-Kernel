package cn.stylefeng.roses.kernel.socket.websocket.server;

import cn.stylefeng.roses.kernel.socket.websocket.server.handler.WebSocketMessageHandler;
import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.pipeline.ChannelInitializer;
import com.gettyio.core.pipeline.DefaultChannelPipeline;
import com.gettyio.expansion.handler.codec.websocket.WebSocketDecoder;
import com.gettyio.expansion.handler.codec.websocket.WebSocketEncoder;

/**
 * WebSocket通道责任链对象
 *
 * @author majianguo
 * @date 2021/6/1 下午2:36
 */
public class WebSocketInitializer extends ChannelInitializer {

    @Override
    public void initChannel(SocketChannel channel) {
        // 获取责任链对象
        DefaultChannelPipeline pipeline = channel.getDefaultChannelPipeline();

        // 先把ws的编解码器添加到责任链前面。注意，只有先通过ws的编解码器，才能解析ws的消息帧，
        // 后续的解码器才能继续解析期望得到的结果
        pipeline.addLast(new WebSocketEncoder());
        pipeline.addLast(new WebSocketDecoder());

        // 添加自定义的消息处理器
        pipeline.addLast(new WebSocketMessageHandler());
    }
}
