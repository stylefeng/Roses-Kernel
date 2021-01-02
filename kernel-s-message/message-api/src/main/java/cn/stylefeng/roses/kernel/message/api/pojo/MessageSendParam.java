package cn.stylefeng.roses.kernel.message.api.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 发送系统消息的参数
 *
 * @author liuhanqing
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageSendParam extends BaseRequest {

    /**
     * 接收用户id字符串，多个以,分割
     */
    @NotBlank(message = "接收用户ID字符串不能为空", groups = {add.class, edit.class})
    private String receiveUserIds;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空", groups = {add.class, edit.class})
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
     * 业务id
     */
    @NotBlank(message = "业务id不能为空", groups = {add.class, edit.class})
    private Long businessId;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空", groups = {add.class, edit.class})
    private String businessType;

}
