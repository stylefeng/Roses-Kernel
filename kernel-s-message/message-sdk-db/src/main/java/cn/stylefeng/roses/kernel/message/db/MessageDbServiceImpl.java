package cn.stylefeng.roses.kernel.message.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.constants.MessageConstants;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.exception.enums.MessageExceptionEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import cn.stylefeng.roses.kernel.message.db.service.SysMessageService;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统消息，数据库实现
 *
 * @author liuhanqing
 * @date 2021/1/2 22:00
 */
@Slf4j
@Service
public class MessageDbServiceImpl implements MessageApi {


    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SysMessageService sysMessageService;

    @Override
    public void sendMessage(MessageSendParam messageSendParam) {
        String receiveUserIds = messageSendParam.getReceiveUserIds();
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<SysMessage> sendMsgList = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        // 发送所有人判断
        if (MessageConstants.RECEIVE_ALL_USER_FLAG.equals(receiveUserIds)) {
            // 查询所有用户
            userIds = userServiceApi.queryAllUserIdList(new SysUserRequest());

        } else {
            String[] userIdArr = receiveUserIds.split(",");
            userIds = Convert.toList(Long.class, userIdArr);
        }
        if (userIds == null || userIds.isEmpty()) {
            throw new SystemModularException(MessageExceptionEnum.ERROR_RECEIVE_USER_IDS, receiveUserIds);
        }

        Set<Long> userIdSet = new HashSet<>(userIds);
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(messageSendParam, sysMessage);
        // 初始化默认值
        sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
        sysMessage.setSendUserId(loginUser.getUserId());
        sysMessage.setMessageSendTime(new Date());
        userIdSet.forEach(userId -> {
            // 判断用户是否存在
            if(userServiceApi.userExist(userId)){
                sysMessage.setReceiveUserId(userId);
                sendMsgList.add(sysMessage);
            }
        });
        sysMessageService.saveBatch(sendMsgList);

    }

    @Override
    public void updateReadFlag(MessageParam messageParam) {

    }

    @Override
    public void batchReadFlagByMessageIds(String messageIds, MessageReadFlagEnum flagEnum) {

    }

    @Override
    public void deleteByMessageId(Long messageId) {

    }

    @Override
    public void batchDeleteByMessageIds(String messageIds) {

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
