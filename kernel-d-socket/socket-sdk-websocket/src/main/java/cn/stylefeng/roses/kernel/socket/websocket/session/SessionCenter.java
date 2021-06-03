package cn.stylefeng.roses.kernel.socket.websocket.session;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.socket.api.session.pojo.SocketSession;
import cn.stylefeng.roses.kernel.socket.websocket.operator.channel.GettySocketOperator;

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
     * 所有会话维护
     */
    private static ConcurrentMap<String, SocketSession<GettySocketOperator>> socketSessionMap = new ConcurrentHashMap<>();

    /**
     * 消息类型和用户ID关系维护
     */
    private static ConcurrentMap<String, Set<String>> messageTypeSessionMap = new ConcurrentHashMap<>();

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
     * 获取消息和用户ID的完整映射关系
     *
     * @return {@link ConcurrentMap< String, Set<String>>}
     * @author majianguo
     * @date 2021/6/1 下午2:14
     **/
    public static ConcurrentMap<String, Set<String>> getMessageTypeSessionMap() {
        return messageTypeSessionMap;
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

        // 维护会话
        socketSessionMap.put(socketSession.getUserId(), socketSession);

        // 维护会话所有的消息类型和会话的关系
        if (ObjectUtil.isNotEmpty(socketSession.getMessageTypes())) {
            for (String messageType : socketSession.getMessageTypes()) {
                Set<String> userIds = messageTypeSessionMap.get(messageType);
                if (ObjectUtil.isEmpty(userIds)) {
                    userIds = new HashSet<>();
                    messageTypeSessionMap.put(messageType, userIds);
                }
                userIds.add(socketSession.getUserId());
            }
        }
    }

    /**
     * 根据消息类型获取所有的会话
     *
     * @return {@link List< SocketSession<GettySocketOperator>>}
     * @author majianguo
     * @date 2021/6/1 下午2:06
     **/
    public static List<SocketSession<GettySocketOperator>> getSocketSessionByMsgType(String msgType) {
        List<SocketSession<GettySocketOperator>> res = new ArrayList<>();

        // 获取监听该消息所有的会话
        Set<String> userIds = messageTypeSessionMap.get(msgType);
        if (ObjectUtil.isNotEmpty(userIds)) {
            for (String userId : userIds) {
                SocketSession<GettySocketOperator> socketSession = socketSessionMap.get(userId);
                res.add(socketSession);
            }
        }

        return res;
    }

    /**
     * 给会话添加监听的消息类型
     *
     * @param msgType 消息类型
     * @param userId  用户ID
     * @author majianguo
     * @date 2021/6/1 下午2:11
     **/
    public static void addSocketSessionMsgType(String msgType, String userId) {
        // 维护Session信息
        SocketSession<GettySocketOperator> socketSession = socketSessionMap.get(userId);
        if (ObjectUtil.isNotEmpty(socketSession)) {
            socketSession.getMessageTypes().add(msgType);
        }
        // 维护消息列表
        Set<String> userIds = messageTypeSessionMap.get(msgType);
        if (ObjectUtil.isEmpty(userIds)) {
            userIds = new HashSet<>();
            messageTypeSessionMap.put(msgType, userIds);
        }
        userIds.add(userId);
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
        for (Map.Entry<String, Set<String>> stringListEntry : messageTypeSessionMap.entrySet()) {
            stringListEntry.getValue().removeIf(item -> item.equals(userId));
        }
    }
}
