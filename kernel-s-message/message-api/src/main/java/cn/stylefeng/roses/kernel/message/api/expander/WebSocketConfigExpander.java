package cn.stylefeng.roses.kernel.message.api.expander;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;

/**
 * websocket相关配置快速获取
 *
 * @author liuhanqing
 * @date 2021/1/25 20:05
 */
public class WebSocketConfigExpander {

    /**
     * 获取websocket的ws-url
     *
     * @author liuhanqing
     * @date 2021/1/25 20:34
     */
    public static String getWebSocketWsUrl() {
        String webSocketWsUr = ConfigContext.me().getConfigValueNullable("WEB_SOCKET_WS_URL", String.class);

        if (webSocketWsUr == null) {
            // 没配置就查询配置文件
            String propertiesUrl = SpringUtil.getProperty("web-socket.ws-url");
            if(StrUtil.isNotEmpty(propertiesUrl)){
                return propertiesUrl;
            }
            // 没配置就返回一个空串
            return StrUtil.EMPTY;
        }
        return webSocketWsUr;

    }

}
