package cn.stylefeng.roses.kernel.socket.websocket.server.bind;

import com.gettyio.core.channel.SocketChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 通道和用户的绑定中心
 *
 * @author majianguo
 * @date 2021/6/1 下午3:09
 */
public class ChannelIdAndUserBindCenter {

    /**
     * 通道和用户绑定关系映射
     * <p>
     * key是channelId通道id，value是userId
     */
    private static final ConcurrentMap<String, String> channelIdAndUserBind = new ConcurrentHashMap<>();

    /**
     * 等待绑定的通道
     */
    private static final List<SocketChannel> waitingBindList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 获取通道ID
     *
     * @param channelId 通道ID
     * @return {@link java.lang.String}
     * @author majianguo
     * @date 2021/6/1 下午3:33
     **/
    public static String getUserId(String channelId) {
        return channelIdAndUserBind.get(channelId);
    }

    /**
     * 添加一个未绑定的通道
     *
     * @param socketChannel 通道对象
     * @author majianguo
     * @date 2021/6/1 下午3:17
     **/
    public static void addSocketChannel(SocketChannel socketChannel) {
        waitingBindList.add(socketChannel);
    }

    /**
     * 绑定关系
     *
     * @param channelId 通道ID
     * @param userId    用户ID
     * @return {@link boolean}
     * @author majianguo
     * @date 2021/6/1 下午3:21
     **/
    public static boolean bind(String channelId, String userId) {
        Iterator<SocketChannel> iterator = waitingBindList.iterator();
        while (iterator.hasNext()) {
            SocketChannel item = iterator.next();
            if (item.getChannelId().equals(channelId)) {
                channelIdAndUserBind.put(channelId, userId);
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * 用户是否已绑定通道
     *
     * @param userId 用户ID
     * @return {@link boolean}
     * @author majianguo
     * @date 2021/6/1 下午3:29
     **/
    public static boolean isBind(String userId) {
        return channelIdAndUserBind.containsValue(userId);
    }

    /**
     * 关闭通道
     *
     * @param channelId 通道ID
     * @author majianguo
     * @date 2021/6/1 下午3:31
     **/
    public static void closed(String channelId) {
        waitingBindList.removeIf(item -> item.getChannelId().equals(channelId));
        channelIdAndUserBind.remove(channelId);
    }

}
