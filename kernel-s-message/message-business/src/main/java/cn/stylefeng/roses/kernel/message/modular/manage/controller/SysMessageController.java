package cn.stylefeng.roses.kernel.message.modular.manage.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.message.api.MessageApi;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MessageApi messageApi;

    /**
     * 发送系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "发送系统消息", path = "/sysMessage/sendMessage")
    public ResponseData sendMessage(@RequestBody @Validated(MessageSendParam.add.class) MessageSendParam messageSendParam) {
        messageApi.sendMessage(messageSendParam);
        return new SuccessResponseData();
    }

    /**
     * 批量更新系统消息状态
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "批量更新系统消息状态", path = "/sysMessage/batchUpdateReadFlag")
    public ResponseData batchUpdateReadFlag(@RequestBody @Validated(MessageParam.updateReadFlag.class) MessageParam messageParam) {
        List<Long> messageIdList = messageParam.getMessageIdList();
        messageApi.batchReadFlagByMessageIds(StrUtil.join(",", messageIdList), MessageReadFlagEnum.READ);
        return new SuccessResponseData();
    }

    /**
     * 系统消息全部修改已读
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息全部修改已读", path = "/sysMessage/allMessageReadFlag")
    public ResponseData allMessageReadFlag() {
        messageApi.allMessageReadFlag();
        return new SuccessResponseData();
    }

    /**
     * 删除系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @PostResource(name = "删除系统消息", path = "/sysMessage/delete")
    public ResponseData delete(@RequestBody @Validated(MessageParam.delete.class) MessageParam messageParam) {
        messageApi.deleteByMessageId(messageParam.getMessageId());
        return new SuccessResponseData();
    }


    /**
     * 查看系统消息
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "查看系统消息", path = "/sysMessage/detail")
    public ResponseData detail(@Validated(MessageParam.detail.class) MessageParam messageParam) {
        return new SuccessResponseData(messageApi.messageDetail(messageParam));
    }


    /**
     * 分页查询系统消息列表
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "分页查询系统消息列表", path = "/sysMessage/page")
    public ResponseData page(MessageParam messageParam) {
        return new SuccessResponseData(messageApi.queryPageCurrentUser(messageParam));
    }

    /**
     * 系统消息列表
     *
     * @author liuhanqing
     * @date 2021/1/8 13:50
     */
    @GetResource(name = "系统消息列表", path = "/sysMessage/list")
    public ResponseData list(MessageParam messageParam) {
        return new SuccessResponseData(messageApi.queryListCurrentUser(messageParam));
    }


    /**
     * 系统消息未读数量
     *
     * @author liuhanqing
     * @date 2021/1/11 19:50
     */
    @GetResource(name = "系统消息列表", path = "/sysMessage/unReadCount")
    public ResponseData msgUnRead(MessageParam messageParam) {
        messageParam.setReadFlag(MessageReadFlagEnum.UNREAD.getCode());
        Integer messageCount = messageApi.queryCountCurrentUser(messageParam);
        Map<String, Object> msgMap = new HashMap<>(1);
        msgMap.put("msgUnReadCount", messageCount);
        return new SuccessResponseData(messageApi.queryListCurrentUser(messageParam));
    }

}
