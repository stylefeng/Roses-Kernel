package cn.stylefeng.roses.kernel.message.starter;

import cn.stylefeng.roses.kernel.message.api.pojo.MessageWebSocketProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 系统消息的自动配置
 *
 * @author liuhanqing
 * @date 2020/12/31 18:50
 */
@Configuration
public class GunsMessageAutoConfiguration {

    public static final String WEB_SOCKET_PREFIX = "web-socket";


    /**
     * websocket的配置
     *
     * @author liuhanqing
     * @date 2021/1/25 9:29
     */
    @Bean
    @ConfigurationProperties(prefix = WEB_SOCKET_PREFIX)
    public MessageWebSocketProperties sysLogProperties() {
        return new MessageWebSocketProperties();
    }


    /**
     * 开启WebSocket功能
     *
     * @return serverEndpointExporter
     * @author liuhanqing
     * @date 2021/01/24 22:09
     */
    @Bean
    @ConditionalOnMissingBean(ServerEndpointExporter.class)
    @ConditionalOnProperty(prefix = WEB_SOCKET_PREFIX, name = "open", havingValue = "true")
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}