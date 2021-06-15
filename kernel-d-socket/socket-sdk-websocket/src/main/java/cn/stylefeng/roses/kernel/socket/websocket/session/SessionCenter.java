package cn.stylefeng.roses.kernel.socket.websocket.session;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;

import java.util.ArrayList;
import java.util.List;
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
    private static ConcurrentMap<String, List<SocketSession<GettySocketOperator>>> socketSessionMap = new ConcurrentHashMap<>();

    /**
     * 获取维护的所有会话
     *
     * @return {@link ConcurrentMap< String, SocketSession<GettySocketOperator>>}
     * @author majianguo
     * @date 2021/6/1 下午2:13
     **/
    public static ConcurrentMap<String, List<SocketSession<GettySocketOperator>>> getSocketSessionMap() {
        return socketSessionMap;
    }

    /**
     * 根据用户ID获取会话信息列表
     *
     * @param userId 用户ID
     * @return {@link SocketSession <GettySocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<GettySocketOperator>> getSessionByUserId(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据用户ID和消息类型获取会话信息列表
     *
     * @param userId  用户ID
     * @return {@link SocketSession <GettySocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<GettySocketOperator>> getSessionByUserIdAndMsgType(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据会话ID获取会话信息
     *
     * @param sessionId 会话ID
     * @return {@link SocketSession <GettySocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static SocketSession<GettySocketOperator> getSessionBySessionId(String sessionId) {
        for (List<SocketSession<GettySocketOperator>> values : socketSessionMap.values()) {
            for (SocketSession<GettySocketOperator> session : values) {
                if (sessionId.equals(session.getSessionId())) {
                    return session;
                }
            }
        }
        return null;
    }

    /**
     * 设置会话
     *
     * @param socketSession 会话详情
     * @author majianguo
     * @date 2021/6/1 下午1:49
     **/
    public static void addSocketSession(SocketSession<GettySocketOperator> socketSession) {
        List<SocketSession<GettySocketOperator>> socketSessions = socketSessionMap.get(socketSession.getUserId());
        if (ObjectUtil.isEmpty(socketSessions)) {
            socketSessions = new ArrayList<>();
            socketSessionMap.put(socketSession.getUserId(), socketSessions);
        }
        socketSessions.add(socketSession);
    }

    /**
     * 连接关闭
     *
     * @param sessionId 会话ID
     * @author majianguo
     * @date 2021/6/1 下午3:25
     **/
    public static void closed(String sessionId) {
        for (List<SocketSession<GettySocketOperator>> values : socketSessionMap.values()) {
            values.removeIf(item -> item.getSessionId().equals(sessionId));
        }
    }
}
