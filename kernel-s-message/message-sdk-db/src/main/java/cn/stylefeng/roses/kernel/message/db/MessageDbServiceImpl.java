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
package cn.stylefeng.roses.kernel.message.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.constants.MessageConstants;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.exception.MessageException;
import cn.stylefeng.roses.kernel.message.api.exception.enums.MessageExceptionEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageRequest;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageSendRequest;
import cn.stylefeng.roses.kernel.message.api.pojo.response.MessageResponse;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import cn.stylefeng.roses.kernel.message.db.service.SysMessageService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.socket.api.SocketOperatorApi;
import cn.stylefeng.roses.kernel.socket.api.enums.ServerMessageTypeEnum;
import cn.stylefeng.roses.kernel.socket.api.exception.SocketException;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
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
    private SocketOperatorApi socketOperatorApi;

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SysMessageService sysMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(MessageSendRequest messageSendRequest) {

        String receiveUserIds = messageSendRequest.getReceiveUserIds();
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

        // 无人可发，不发送
        if (userIds == null || userIds.isEmpty()) {
            throw new MessageException(MessageExceptionEnum.ERROR_RECEIVE_USER_IDS);
        }

        Set<Long> userIdSet = new HashSet<>(userIds);
        for (Long userId : userIdSet) {
            // 判断用户是否存在
            if (userServiceApi.userExist(userId)) {
                SysMessage sysMessage = new SysMessage();
                BeanUtil.copyProperties(messageSendRequest, sysMessage);
                // 初始化默认值
                sysMessage.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
                sysMessage.setSendUserId(loginUser.getUserId());
                sysMessage.setReceiveUserId(userId);
                sendMsgList.add(sysMessage);
            }
        }
        sysMessageService.saveBatch(sendMsgList);

        // 给用户发送通知
        for (SysMessage item : sendMsgList) {
            try {
                socketOperatorApi.sendMsgOfUserSession(ServerMessageTypeEnum.SYS_NOTICE_MSG_TYPE.getCode(), item.getReceiveUserId().toString(), item);
            } catch (SocketException socketException) {
                // 该用户不在线

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReadFlag(MessageRequest messageRequest) {
        Long messageId = messageRequest.getMessageId();
        SysMessage sysMessage = sysMessageService.getById(messageId);
        Optional.ofNullable(sysMessage).ifPresent(msg -> {
            msg.setReadFlag(messageRequest.getReadFlag());
            sysMessageService.updateById(msg);
        });

    }

    @Override
    public void allMessageReadFlag() {
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Long userId = loginUser.getUserId();
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMessage::getReadFlag, MessageReadFlagEnum.READ.getCode()).eq(SysMessage::getReadFlag, MessageReadFlagEnum.UNREAD.getCode()).eq(SysMessage::getReceiveUserId, userId).set(SysMessage::getDelFlag, YesOrNotEnum.N.getCode());
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
        updateWrapper.eq(SysMessage::getMessageId, messageId).set(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());
        sysMessageService.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByMessageIds(String messageIds) {
        LambdaUpdateWrapper<SysMessage> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.inSql(SysMessage::getMessageId, messageIds).set(SysMessage::getDelFlag, YesOrNotEnum.Y.getCode());
        sysMessageService.update(updateWrapper);
    }

    @Override
    public MessageResponse messageDetail(MessageRequest messageRequest) {
        SysMessage sysMessage = sysMessageService.getById(messageRequest.getMessageId());
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
    public PageResult<MessageResponse> queryPage(MessageRequest messageRequest) {
        PageResult<SysMessage> pageResult = sysMessageService.findPage(messageRequest);
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
    public List<MessageResponse> queryList(MessageRequest messageRequest) {
        List<SysMessage> messageList = sysMessageService.findList(messageRequest);
        return messageList.stream().map(msg -> {
            MessageResponse response = new MessageResponse();
            BeanUtil.copyProperties(msg, response);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResult<MessageResponse> queryPageCurrentUser(MessageRequest messageRequest) {
        if (ObjectUtil.isEmpty(messageRequest)) {
            messageRequest = new MessageRequest();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageRequest.setReceiveUserId(loginUser.getUserId());
        return this.queryPage(messageRequest);
    }

    @Override
    public List<MessageResponse> queryListCurrentUser(MessageRequest messageRequest) {
        if (ObjectUtil.isEmpty(messageRequest)) {
            messageRequest = new MessageRequest();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageRequest.setReceiveUserId(loginUser.getUserId());
        return this.queryList(messageRequest);
    }

    @Override
    public Integer queryCount(MessageRequest messageRequest) {
        return sysMessageService.findCount(messageRequest);
    }

    @Override
    public Integer queryCountCurrentUser(MessageRequest messageRequest) {
        if (ObjectUtil.isEmpty(messageRequest)) {
            messageRequest = new MessageRequest();
        }
        // 获取当前登录人
        LoginUser loginUser = LoginContext.me().getLoginUser();
        messageRequest.setReceiveUserId(loginUser.getUserId());
        return this.queryCount(messageRequest);
    }

}
