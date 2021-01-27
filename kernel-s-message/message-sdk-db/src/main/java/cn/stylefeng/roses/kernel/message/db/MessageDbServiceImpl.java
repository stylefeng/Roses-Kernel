package cn.stylefeng.roses.kernel.message.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.WebsocketApi;
import cn.stylefeng.roses.kernel.message.api.constants.MessageConstants;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.exception.MessageException;
import cn.stylefeng.roses.kernel.message.api.exception.enums.MessageExceptionEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import cn.stylefeng.roses.kernel.message.db.service.SysMessageService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    private WebsocketApi websocketApi;

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SysMessageService sysMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(MessageSendParam messageSendParam) {
        String receiveUserIds = messageSendParam.getReceiveUserIds();
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<SysMessage> sendMsgList = new ArrayList<>();
        List<Long> userIds;
        // 发送所有人判断
        if (MessageConstants.RECEIVE_ALL_USER_FLAG.equals(receiveUserIds)) {
            // 查询所有用户
            userIds = userServiceApi.queryAllUserIdList(new SysUserRequest());
        } else {
            String[] userIdArr = receiveUserIds.split(",");
            userIds = Convert.toList(Long.class, userIdArr);
        }
        if (userIds == null || userIds.isEmpty()) {
            throw new MessageException(MessageExceptionEnum.ERROR_RECEIVE_USER_IDS);
        }

        Set<Long> userIdSet = new HashSet<>(userIds);
        SysMessage sysMessage = new SysMessage();
        BeanUtil.copyProperties(messageSendParam, sysMessage);
        // 初始化默认值
        sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
        sysMessage.setSendUserId(loginUser.getUserId());
        userIdSet.forEach(userId -> {
            // 判断用户是否存在
            if (userServiceApi.userExist(userId)) {
                sysMessage.setReceiveUserId(userId);
                sendMsgList.add(sysMessage);
            }
        });

        websocketApi.sendWebSocketMessage(ListUtil.toList(userIdSet), messageSendParam);
        sysMessageService.saveBatch(sendMsgList);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReadFlag(MessageParam messageParam) {
        Long messageId = messageParam.getMessageId();
        SysMessage sysMessage = sysMessageService.getById(messageId);
        Optional.ofNullable(sysMessage).ifPresent(msg -> {
            msg.setReadFlag(messageParam.getReadFlag());
            sysMessageService.updateById(msg);
        });

    }

    @Override
    public void allMessageReadFlag() {
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long userId = loginUser.getUserId();
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMessage::getReadFlag, MessageReadFlagEnum.READ.getCode())
                .eq(SysMessage::getReadFlag, MessageReadFlagEnum.UNREAD.getCode())
                .eq(SysMessage::getReceiveUserId, userId)
                .set(SysMessage::getDelFlag, YesOrNotEnum.N.getCode());
        sysMessageService.update(updateWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchReadFlagByMessageIds(String messageIds, MessageReadFlagEnum flagEnum) {
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.inSql(SysMessage::getMessageId, messageIds).set(SysMessage::getReadFlag, flagEnum.getCode());
        sysMessageService.update(updateWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMessageId(Long messageId) {
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        // 修改为逻辑删除
        updateWrapper.eq(SysMessage::getMessageId, messageId)
                .set(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());
        sysMessageService.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByMessageIds(String messageIds) {
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.inSql(SysMessage::getMessageId, messageIds)
                .set(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());
        sysMessageService.update(updateWrapper);
    }

    @Override
    public MessageResponse messageDetail(MessageParam messageParam) {
        SysMessage sysMessage = sysMessageService.getById(messageParam.getMessageId());
        // 判断消息为未读状态更新为已读
        Optional.ofNullable(sysMessage).ifPresent(msg -> {
            if (MessageReadFlagEnum.UNREAD.getCode().equals(sysMessage.getReadFlag())) {
                msg.setReadFlag(MessageReadFlagEnum.READ.getCode());
                sysMessageService.updateById(msg);
            }
        });
        MessageResponse messageResponse = new MessageResponse();
        BeanUtil.copyProperties(sysMessage, messageResponse);
        return messageResponse;
    }

    @Override
    public PageResult<MessageResponse> queryPage(MessageParam messageParam) {
        PageResult<SysMessage> pageResult = sysMessageService.page(messageParam);
        PageResult<MessageResponse> result = new PageResult<>();
        List<SysMessage> messageList = pageResult.getRows();
        List<MessageResponse> resultList = messageList.stream().map(msg -> {
            MessageResponse response = new MessageResponse();
            BeanUtil.copyProperties(msg, response);
            return response;
        }).collect(Collectors.toList());
        BeanUtil.copyProperties(pageResult, result);
        result.setRows(resultList);
        return result;
    }

    @Override
    public List<MessageResponse> queryList(MessageParam messageParam) {
        List<SysMessage> messageList = sysMessageService.list(messageParam);
        List<MessageResponse> resultList = messageList.stream().map(msg -> {
            MessageResponse response = new MessageResponse();
            BeanUtil.copyProperties(msg, response);
            return response;
        }).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public PageResult<MessageResponse> queryPageCurrentUser(MessageParam messageParam) {
        if (ObjectUtil.isEmpty(messageParam)) {
            messageParam = new MessageParam();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageParam.setReceiveUserId(loginUser.getUserId());
        return this.queryPage(messageParam);
    }

    @Override
    public List<MessageResponse> queryListCurrentUser(MessageParam messageParam) {
        if (ObjectUtil.isEmpty(messageParam)) {
            messageParam = new MessageParam();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageParam.setReceiveUserId(loginUser.getUserId());
        return this.queryList(messageParam);
    }

    @Override
    public Integer queryCount(MessageParam messageParam) {
        return sysMessageService.count(messageParam);
    }

    @Override
    public Integer queryCountCurrentUser(MessageParam messageParam) {
        if (ObjectUtil.isEmpty(messageParam)) {
            messageParam = new MessageParam();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageParam.setReceiveUserId(loginUser.getUserId());
        return this.queryCount(messageParam);
    }

}
