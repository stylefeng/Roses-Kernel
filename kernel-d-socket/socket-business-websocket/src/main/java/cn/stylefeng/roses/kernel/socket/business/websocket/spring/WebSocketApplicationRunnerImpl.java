package cn.stylefeng.roses.kernel.socket.business.websocket.spring;

import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.socket.websocket.server.WebSocketServer;
import com.gettyio.core.channel.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.StandardSocketOptions;

import static cn.stylefeng.roses.kernel.socket.api.constants.SocketConstants.*;

/**
 * Spring Boot启动完成拉起WebSocket
 *
 * @author majianguo
 * @date 2021/6/2 上午11:06
 */
@Component
@Slf4j
public class WebSocketApplicationRunnerImpl implements ApplicationRunner {

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
    }
}
