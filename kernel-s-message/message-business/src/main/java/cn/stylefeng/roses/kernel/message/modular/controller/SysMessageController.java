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
package cn.stylefeng.roses.kernel.message.modular.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageRequest;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageSendRequest;
import cn.stylefeng.roses.kernel.message.api.pojo.response.MessageResponse;
import cn.stylefeng.roses.kernel.message.modular.wrapper.MessageWrapper;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.wrapper.api.annotation.Wrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统消息控制器
 *
 * @author liuhanqing
 * @date 2021/1/1 22:30
 */
@RestController
@ApiResource(name = "系统消息控制器")
public class SysMessageController {

    /**
     * 系统消息api
     */
    @Resource
    private MessageApi messageApi;

    /**
     * 发送系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "发送系统消息", path = "/sysMessage/sendMessage")
    @BusinessLog
    public ResponseData<?> sendMessage(@RequestBody @Validated(MessageSendRequest.add.class) MessageSendRequest messageSendRequest) {
        messageSendRequest.setMessageSendTime(new Date());
        messageApi.sendMessage(messageSendRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量更新系统消息状态
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "批量更新系统消息状态", path = "/sysMessage/batchUpdateReadFlag")
    @BusinessLog
    public ResponseData<?> batchUpdateReadFlag(@RequestBody @Validated(MessageRequest.updateReadFlag.class) MessageRequest messageRequest) {
        List<Long> messageIdList = messageRequest.getMessageIdList();
        messageApi.batchReadFlagByMessageIds(StrUtil.join(",", messageIdList), MessageReadFlagEnum.READ);
        return new SuccessResponseData<>();
    }

    /**
     * 系统消息全部修改已读
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息全部修改已读", path = "/sysMessage/allMessageReadFlag")
    public ResponseData<?> allMessageReadFlag() {
        messageApi.allMessageReadFlag();
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "删除系统消息", path = "/sysMessage/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(MessageRequest.delete.class) MessageRequest messageRequest) {
        messageApi.deleteByMessageId(messageRequest.getMessageId());
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "查看系统消息", path = "/sysMessage/detail")
    public ResponseData<MessageResponse> detail(@Validated(MessageRequest.detail.class) MessageRequest messageRequest) {
        return new SuccessResponseData<>(messageApi.messageDetail(messageRequest));
    }

    /**
     * 分页查询系统消息列表
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "分页查询系统消息列表", path = "/sysMessage/page")
    @Wrapper(MessageWrapper.class)
    public ResponseData<PageResult<MessageResponse>> page(MessageRequest messageRequest) {
        return new SuccessResponseData<>(messageApi.queryPageCurrentUser(messageRequest));
    }

    /**
     * 系统消息列表
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息列表", path = "/sysMessage/list")
    public ResponseData<List<MessageResponse>> list(MessageRequest messageRequest) {
        return new SuccessResponseData<>(messageApi.queryListCurrentUser(messageRequest));
    }

    /**
     * 查询所有未读系统消息列表
     *
     * @author fengshuonan
     * @date 2021/6/12 17:42
     */
    @GetResource(name = "查询所有未读系统消息列表", path = "/sysMessage/unReadMessageList", requiredPermission = false)
    public ResponseData<List<MessageResponse>> unReadMessageList(MessageRequest messageRequest) {
        messageRequest.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
        List<MessageResponse> messageResponses = messageApi.queryListCurrentUser(messageRequest);
        return new SuccessResponseData<>(messageResponses);
    }

}
