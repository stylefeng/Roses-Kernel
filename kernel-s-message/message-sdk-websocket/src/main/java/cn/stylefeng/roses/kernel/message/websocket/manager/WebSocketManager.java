/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.message.websocket.manager;

import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket客户端连接管理
 *
 * @author liuhanqing
 * @date 2021/1/24 22:08
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
     * @param userId  用户id
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
    public static void sendMessage(Long userId, String message) {
        List<Session> sessionList = getSessionByUserId(userId);
        // 增加判断不为空
        if (!CollectionUtils.isEmpty(sessionList)) {
            for (Session userSession : sessionList) {
                userSession.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @author liuhanqing
     * @date 2021/1/24 22:11
     */
    public static void sendMessageToAll(String message) {
        for (Long userId : WebSocketManager.getUserList()) {
            sendMessage(userId, message);
        }
    }

}
