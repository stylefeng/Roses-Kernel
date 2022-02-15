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
package cn.stylefeng.roses.kernel.message.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 系统消息的查询参数
 *
 * @author liuhanqing
 * @date 2021/1/1 20:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequest extends BaseRequest {

    /**
     * 消息id
     */
    @NotNull(message = "messageId不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("消息id")
    private Long messageId;

    /**
     * 接收用户id
     */
    @ChineseDescription("接收用户id")
    private Long receiveUserId;

    /**
     * 发送用户id
     */
    @ChineseDescription("发送用户id")
    private Long sendUserId;

    /**
     * 消息标题
     */
    @ChineseDescription("消息标题")
    private String messageTitle;

    /**
     * 消息的内容
     */
    @ChineseDescription("消息内容")
    private String messageContent;

    /**
     * 消息类型
     */
    @ChineseDescription("消息类型")
    private String messageType;

    /**
     * 消息优先级
     */
    @ChineseDescription("消息优先级")
    private String priorityLevel;

    /**
     * 消息发送时间
     */
    @ChineseDescription("消息发送时间")
    private Date messageSendTime;

    /**
     * 业务id
     */
    @ChineseDescription("业务id")
    private Long businessId;

    /**
     * 业务类型
     */
    @ChineseDescription("业务类型")
    private String businessType;

    /**
     * 阅读状态：0-未读，1-已读
     */
    @NotNull(message = "阅读状态不能为空", groups = {updateStatus.class})
    @ChineseDescription("阅读状态")
    private Integer readFlag;

    /**
     * 消息id集合
     */
    @NotEmpty(message = "消息id集合不能为空", groups = {updateReadFlag.class})
    @ChineseDescription("消息id集合")
    private List<Long> messageIdList;


    /**
     * 参数校验分组：修改阅读状态
     */
    public @interface updateReadFlag {
    }

}
