package cn.stylefeng.roses.kernel.message.websocket.manager;

import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuhq
 */
public class WebSocketManager {
    private static final ConcurrentHashMap<Long, List<Session>> userIdSessionMap = new ConcurrentHashMap<>();

    /**
     * 添加用户ID相关的Session
     *
     * @param userId  用户id
     * @param session 用户websocketSession
     * @author liuhanqing
     * @date 2021/1/24 22:08
     */
    public static void add(Long userId, Session session) {
        userIdSessionMap.computeIfAbsent(userId, v -> new ArrayList<>()).add(session);
    }

    /**
     * 根据用户ID获取Session
     *
     * @param userId 用户id
     * @return List<Session>  用户websocketSession集合
     * @author liuhanqing
     * @date 2021/1/24 22:10
     */
    public static List<Session> getSessionByUserId(Long userId) {
        return userIdSessionMap.get(userId);
    }

    /**
     * 移除失效的Session
     *
     * @param userId   用户id
     * @param session 用户websocketSession
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static void removeSession(Long userId, Session session) {
        if (session == null) {
            return;
        }
        List<Session> webSessoin = userIdSessionMap.get(userId);
        if (webSessoin == null || CollectionUtils.isEmpty(webSessoin)) {
            return;
        }
        webSessoin.remove(session);
    }

    /**
     * 获取链接用户集合
     *
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static Set<Long> getUserList() {
        return userIdSessionMap.keySet();
    }

    /**
     * 发送消息
     *
     * @param userId  用户id
     * @param message 消息
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static void sendMessage(Long userId, String message){
        for(Session userSession: getSessionByUserId(userId)){
            userSession.getAsyncRemote().sendText(message);
        }
    }
    /**
     * 发送消息
     *
     * @param message 消息
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static void sendMessageToAll(String message){
        for (Long userId : WebSocketManager.getUserList()) {
            sendMessage(userId, message);
        }
    }
}
