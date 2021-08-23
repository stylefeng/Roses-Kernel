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
package cn.stylefeng.roses.kernel.message.db.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messageId;

    /**
     * 接收用户id
     */
    @TableField(value = "receive_user_id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    @TableField(value = "send_user_id")
    @JSONField(serializeUsing = ToStringSerializer.class)
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
     * 消息优先级
     */
    @TableField(value = "priority_level")
    private String priorityLevel;

    /**
     * 消息发送时间
     */
    @TableField(value = "message_send_time")
    private Date messageSendTime;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    @JSONField(serializeUsing = ToStringSerializer.class)
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

    /**
     * 业务类型值
     */
    @TableField(exist = false)
    private String businessTypeValue;

}
