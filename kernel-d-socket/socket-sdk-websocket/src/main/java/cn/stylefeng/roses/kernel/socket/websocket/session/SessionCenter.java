package cn.stylefeng.roses.kernel.socket.websocket.session;

import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 会话中心
 * <p>
 * 维护所有的会话
 *
 * @author majianguo
 * @date 2021/6/1 下午1:43
 */
public class SessionCenter {

    /**
     * 所有会话维护
     */
    private static ConcurrentMap<String, SocketSession<GettySocketOperator>> socketSessionMap = new ConcurrentHashMap<>();

    /**
     * 获取维护的所有会话
     *
     * @return {@link ConcurrentMap< String, SocketSession<GettySocketOperator>>}
     * @author majianguo
     * @date 2021/6/1 下午2:13
     **/
    public static ConcurrentMap<String, SocketSession<GettySocketOperator>> getSocketSessionMap() {
        return socketSessionMap;
    }

    /**
     * 根据用户ID获取会话详情
     *
     * @param userId 用户ID
     * @return {@link SocketSession <GettySocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static SocketSession<GettySocketOperator> getSessionByUserId(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 设置会话
     *
     * @param socketSession 会话详情
     * @author majianguo
     * @date 2021/6/1 下午1:49
     **/
    public static void addSocketSession(SocketSession<GettySocketOperator> socketSession) {
        socketSessionMap.put(socketSession.getUserId(), socketSession);
    }

    /**
     * 连接关闭
     *
     * @param userId 用户ID
     * @author majianguo
     * @date 2021/6/1 下午3:25
     **/
    public static void closed(String userId) {
        socketSessionMap.remove(userId);
    }
}
