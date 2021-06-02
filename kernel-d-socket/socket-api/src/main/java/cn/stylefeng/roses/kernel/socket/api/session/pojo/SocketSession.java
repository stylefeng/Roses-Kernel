package cn.stylefeng.roses.kernel.socket.api.session.pojo;

import cn.stylefeng.roses.kernel.socket.api.session.SocketSessionOperatorApi;
import lombok.Data;

import java.util.Set;

/**
 * Socket会话
 *
 * @author majianguo
 * @date 2021/6/1 上午11:28
 */
@Data
public class SocketSession<T extends SocketSessionOperatorApi> {

    /**
     * 会话唯一标识
     */
    private String sessionId;

    /**
     * 该会话所有的监听消息类型
     */
    private Set<String> messageTypes;

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
