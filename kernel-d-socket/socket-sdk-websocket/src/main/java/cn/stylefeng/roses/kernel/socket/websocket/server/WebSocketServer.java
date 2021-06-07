package cn.stylefeng.roses.kernel.socket.websocket.server;

import com.gettyio.core.channel.config.ServerConfig;
import com.gettyio.core.channel.starter.AioServerStarter;

import java.net.StandardSocketOptions;

/**
 * WebSocket服务端
 *
 * @author majianguo
 * @date 2021/6/1 下午2:40
 */
public class WebSocketServer {

    /**
     * 无参数启动(开发测试使用)
     *
     * @author majianguo
     * @date 2021/6/2 上午11:10
     **/
    public static void start() {
        // 初始化配置对象
        ServerConfig aioServerConfig = new ServerConfig();

        // 设置host,不设置默认localhost
        aioServerConfig.setHost("0.0.0.0");

        // 设置端口号
        aioServerConfig.setPort(11130);

        // 设置服务器端内存池最大可分配空间大小，默认256mb，内存池空间可以根据吞吐量设置。
        // 尽量可以设置大一点，因为这不会真正的占用系统内存，只有真正使用时才会分配
        aioServerConfig.setServerChunkSize(512 * 1024 * 1024);

        // 设置数据输出器队列大小，一般不用设置这个参数，默认是10*1024*1024
        aioServerConfig.setBufferWriterQueueSize(10 * 1024 * 1024);

        // 设置读取缓存块大小，一般不用设置这个参数，默认128字节
        aioServerConfig.setReadBufferSize(2048);

        // 设置内存池等待分配内存的最大阻塞时间，默认是1秒
        aioServerConfig.setChunkPoolBlockTime(2000);

        // 设置SocketOptions
        aioServerConfig.setOption(StandardSocketOptions.SO_RCVBUF, 8192);

        // 启动
        run(aioServerConfig);
    }

    /**
     * 启动Socket服务
     *
     * @param aioServerConfig 服务器配置
     * @author majianguo
     * @date 2021/6/1 下午2:40
     **/
    public static void run(ServerConfig aioServerConfig) {
        final AioServerStarter starter = new AioServerStarter(aioServerConfig);
        starter.channelInitializer(new WebSocketInitializer());
        try {
            // 启动服务
            starter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
