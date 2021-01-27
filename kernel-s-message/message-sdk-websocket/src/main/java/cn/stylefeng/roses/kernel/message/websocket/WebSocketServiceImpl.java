package cn.stylefeng.roses.kernel.message.websocket;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.message.api.WebsocketApi;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.message.websocket.manager.WebSocketManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统消息websocket
 *
 * @author liuhanqing
 * @date 2021/1/2 22:00
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebsocketApi {


    public final static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void sendWebSocketMessage(List<Long> userIdList, MessageSendParam messageSendParam) {
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        try {
            MessageResponse sysMessage = new MessageResponse();
            BeanUtil.copyProperties(messageSendParam, sysMessage);
            sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
            sysMessage.setSendUserId(loginUser.getUserId());
            String msgInfo = MAPPER.writeValueAsString(sysMessage);

            for (Long userId : userIdList) {
                WebSocketManager.sendMessage(userId, msgInfo);
            }
        } catch (JsonProcessingException e) {
            log.error("发送websocket异常", e);
        }
    }
}
