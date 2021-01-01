package cn.stylefeng.roses.kernel.message.db.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统消息
 *
 * @author liuhanqing
 * @date 2020/12/31 20:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_message")
public class SysMessage extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "message_id", type = IdType.ASSIGN_ID)
    private Long messageId;

    /**
     * 接收用户id
     */
    @TableId(value = "receive_user_id")
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    @TableId(value = "send_user_id")
    private Long sendUserId;

    /**
     * 消息标题
     */
    @TableField(value = "message_title")
    private String messageTitle;

    /**
     * 消息的内容
     */
    @TableField(value = "message_content")
    private String messageContent;

    /**
     * 消息类型
     */
    @TableField(value = "message_type")
    private String messageType;

    /**
     * 消息发送时间
     */
    @TableField(value = "message_send_time")
    private Date messageSendTime;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    private Long businessId;

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 阅读状态：0-未读，1-已读
     */
    @TableField(value = "read_flag")
    private Integer readFlag;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

}