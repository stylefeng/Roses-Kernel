package cn.stylefeng.roses.kernel.socket.business.websocket.session;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.business.websocket.operator.channel.GunsSocketOperator;

import java.util.*;
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
     * 所有用户会话维护
     */
    private static ConcurrentMap<String, List<SocketSession<GunsSocketOperator>>> socketSessionMap = new ConcurrentHashMap<>();

    /**
     * 获取维护的所有会话
     *
     * @return {@link ConcurrentMap< String, SocketSession<GunsSocketOperator>>}
     * @author majianguo
     * @date 2021/6/1 下午2:13
     **/
    public static ConcurrentMap<String, List<SocketSession<GunsSocketOperator>>> getSocketSessionMap() {
        return socketSessionMap;
    }

    /**
     * 根据用户ID获取会话信息列表
     *
     * @param userId 用户ID
     * @return {@link SocketSession <GunsSocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<GunsSocketOperator>> getSessionByUserId(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据用户ID和消息类型获取会话信息列表
     *
     * @param userId 用户ID
     * @return {@link SocketSession <GunsSocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static List<SocketSession<GunsSocketOperator>> getSessionByUserIdAndMsgType(String userId) {
        return socketSessionMap.get(userId);
    }

    /**
     * 根据会话ID获取会话信息
     *
     * @param sessionId 会话ID
     * @return {@link SocketSession <GunsSocketOperator>}
     * @author majianguo
     * @date 2021/6/1 下午1:48
     **/
    public static SocketSession<GunsSocketOperator> getSessionBySessionId(String sessionId) {
        Set<Map.Entry<String, List<SocketSession<GunsSocketOperator>>>> entrySet = socketSessionMap.entrySet();
        for (Map.Entry<String, List<SocketSession<GunsSocketOperator>>> stringListEntry : entrySet) {
            List<SocketSession<GunsSocketOperator>> stringListEntryValue = stringListEntry.getValue();
            for (SocketSession<GunsSocketOperator> socketSession : stringListEntryValue) {
                if (sessionId.equals(socketSession.getSessionId())) {
                    return socketSession;
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
    public static void addSocketSession(SocketSession<GunsSocketOperator> socketSession) {
        List<SocketSession<GunsSocketOperator>> socketSessions = socketSessionMap.get(socketSession.getUserId());
        if (ObjectUtil.isEmpty(socketSessions)) {
            socketSessions = Collections.synchronizedList(new ArrayList<>());
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
        // 删除维护关系
        SocketSession<GunsSocketOperator> operatorSocketSession = getSessionBySessionId(sessionId);
        if (ObjectUtil.isNotEmpty(operatorSocketSession)) {
            operatorSocketSession.getSocketOperatorApi().close();
        }
    }

    /**
     * 删除维护关系
     *
     * @param sessionId 会话ID
     * @return {@link cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession<cn.stylefeng.roses.kernel.socket.business.websocket.operator.channel.GunsSocketOperator>}
     * @author majianguo
     * @date 2021/8/30 9:20
     **/
    public static SocketSession<GunsSocketOperator> deleteById(String sessionId) {
        // 获取所有人员的连接会话信息
        Set<Map.Entry<String, List<SocketSession<GunsSocketOperator>>>> entrySet = socketSessionMap.entrySet();
        Iterator<Map.Entry<String, List<SocketSession<GunsSocketOperator>>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<SocketSession<GunsSocketOperator>>> next = iterator.next();
            List<SocketSession<GunsSocketOperator>> value = next.getValue();
            if (ObjectUtil.isNotEmpty(value)) {

                // 找出该人员的指定会话信息
                Iterator<SocketSession<GunsSocketOperator>> socketSessionIterator = value.iterator();
                while (socketSessionIterator.hasNext()) {
                    SocketSession<GunsSocketOperator> operatorSocketSession = socketSessionIterator.next();
                    if (operatorSocketSession.getSessionId().equals(sessionId)) {
                        // 在列表中删除
                        socketSessionIterator.remove();
                        return operatorSocketSession;
                    }
                }
            }
        }

        return null;
    }
}
