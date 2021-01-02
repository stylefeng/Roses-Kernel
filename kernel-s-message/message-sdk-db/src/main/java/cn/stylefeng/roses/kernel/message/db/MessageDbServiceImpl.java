package cn.stylefeng.roses.kernel.message.db;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.constants.MessageConstants;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统消息，数据库实现
 *
 * @author liuhanqing
 * @date 2021/1/2 22:00
 */
@Slf4j
@Service
public class MessageDbServiceImpl implements MessageApi {

    @Override
    public void sendMessage(MessageSendParam messageSendParam) {
        String receiveUserIds = messageSendParam.getReceiveUserIds();
        // 发送所有人判断
        if (MessageConstants.RECEIVE_ALL_USER_FLAG.equals(receiveUserIds)) {

        }

    }

    @Override
    public void updateReadFlag(MessageParam messageParam) {

    }

    @Override
    public void deleteByMessageId(Long messageId) {

    }

    @Override
    public void batchDeleteByMessageId(String messageIds) {

    }

    @Override
    public MessageResponse messageDetail(MessageParam messageParam) {
        return null;
    }

    @Override
    public PageResult<MessageResponse> queryMessagePage(MessageParam messageParam) {
        return null;
    }

    @Override
    public List<MessageResponse> queryMessageList(MessageParam messageParam) {
        return null;
    }
}
