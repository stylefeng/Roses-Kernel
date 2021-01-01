package cn.stylefeng.roses.kernel.message.api.pojo.manage;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统消息的查询参数
 *
 * @author liuhanqing
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageParam extends BaseRequest {

    /**
     * 消息id
     */
    @NotNull(message = "messageId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long messageId;

    /**
     * 接收用户id
     */
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    private Long sendUserId;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息的内容
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息发送时间
     */
    private Date messageSendTime;

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 阅读状态：0-未读，1-已读
     */
    private Integer readFlag;

}
