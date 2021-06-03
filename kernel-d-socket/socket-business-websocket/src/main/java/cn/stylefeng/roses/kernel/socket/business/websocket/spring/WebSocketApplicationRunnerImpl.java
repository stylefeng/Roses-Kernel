package cn.stylefeng.roses.kernel.socket.business.websocket.spring;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.enums.ServerMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.message.WebSocketMessagePOJO;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;
import cn.stylefeng.roses.kernel.socket.websocket.server.WebSocketServer;
import cn.stylefeng.roses.kernel.socket.websocket.session.SessionCenter;
import com.gettyio.core.channel.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.StandardSocketOptions;
import java.util.HashSet;
import java.util.List;

import static cn.stylefeng.roses.kernel.socket.api.constants.SocketConstants.*;
import static cn.stylefeng.roses.kernel.socket.api.enums.ClientMessageTypeEnum.*;

/**
 * Spring Boot启动完成拉起WebSocket
 *
 * @author majianguo
 * @date 2021/6/2 上午11:06
 */
@Component
@Slf4j
public class WebSocketApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private SocketOperatorApi socketOperatorApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化配置对象
        ServerConfig aioServerConfig = new ServerConfig();

        // 设置host,不设置默认0.0.0.0
        String socketHost = ConfigContext.me().getSysConfigValueWithDefault(SOCKET_HOST, String.class, "0.0.0.0");
        aioServerConfig.setHost(socketHost);

        // 设置端口号
        Integer socketPort = ConfigContext.me().getSysConfigValueWithDefault(SOCKET_PORT, Integer.class, 11130);
        aioServerConfig.setPort(socketPort);

        // 设置服务器端内存池最大可分配空间大小，默认512mb，内存池空间可以根据吞吐量设置。
        // 尽量可以设置大一点，因为这不会真正的占用系统内存，只有真正使用时才会分配
        Integer socketServerChunkSize = ConfigContext.me().getSysConfigValueWithDefault(SOCKET_SERVER_CHUNK_SIZE, Integer.class, 512 * 1024 * 1024);
        aioServerConfig.setServerChunkSize(socketServerChunkSize);

        // 设置SocketOptions
        aioServerConfig.setOption(StandardSocketOptions.SO_RCVBUF, 8192);

        // 启动
        WebSocketServer.run(aioServerConfig);

        log.info("WebSocket Server Start Success!");

        // 添加用户新增消息类型的回调
        socketOperatorApi.msgTypeCallback(USER_ADD_MSG_TYPE.getCode(), (msgType, msg, socketSession) -> {
            // 转换对象
            WebSocketMessagePOJO webSocketMessage = (WebSocketMessagePOJO)msg;

            // 维护会话中心的消息类型
            SessionCenter.addSocketSessionMsgType(webSocketMessage.getData().toString(), webSocketMessage.getFormUserId());
        });
    }
}
