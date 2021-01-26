package cn.stylefeng.roses.kernel.message.api;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;

import java.util.List;

/**
 * 系统消息websocket相关接口
 *
 * @author liuhanqing
 * @date 2021/1/26 18:14
 */
public interface WebsocketApi {

    /**
     * 发送websocket系统消息
     *
     * @param userIdList userId 集合
     * @param messageSendParam 系统消息参数
     * @author liuhanqing
     * @date 2021/1/26 18:17
     */
    void sendWebSocketMessage(List<Long> userIdList, MessageSendParam messageSendParam);

}