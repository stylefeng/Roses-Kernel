package cn.stylefeng.roses.kernel.socket.api.session.pojo;

import cn.stylefeng.roses.kernel.socket.api.session.SocketSessionOperatorApi;
import lombok.Data;

/**
 * Socket会话
 *
 * @author majianguo
 * @date 2021/6/1 上午11:28
 */
@Data
public class SocketSession<T extends SocketSessionOperatorApi> {

    /**
     * 会话ID，每一个新建的会话都有(目前使用通道ID)
     */
    private String sessionId;

    /**
     * 会话唯一标识
     */
    private String userId;

    /**
     * 该会话监听的消息类型
     */
    private String messageType;

    /**
     * token信息
     */
    private String token;

    /**
     * 连接时间
     */
    private Long connectionTime;

    /**
     * 最后活跃时间
     */
    private Long lastActiveTime;

    /**
     * 操作API
     */
    private T socketOperatorApi;

    /**
     * 自定义数据
     */
    private Object data;

}
