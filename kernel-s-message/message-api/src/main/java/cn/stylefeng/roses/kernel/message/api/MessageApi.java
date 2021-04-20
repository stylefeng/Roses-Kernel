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
package cn.stylefeng.roses.kernel.message.api;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.response.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageSendRequest;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageRequest;


import java.util.List;

/**
 * 系统消息相关接口
 * <p>
 * 接口可以有多种实现，目前只实现数据库存储方式
 *
 * @author liuhanqing
 * @date 2021/1/2 21:21
 */
public interface MessageApi {

    /**
     * 发送系统消息
     *
     * @param messageSendRequest 系统消息参数
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    void sendMessage(MessageSendRequest messageSendRequest);

    /**
     * 更新阅读状态
     *
     * @param messageRequest 系统消息参数
     * @author liuhanqing
     * @date 2021/1/2 22:15
     */
    void updateReadFlag(MessageRequest messageRequest);

    /**
     * 全部更新阅读状态
     *
     * @author liuhanqing
     * @date 2021/1/2 22:15
     */
    void allMessageReadFlag();

    /**
     * 批量更新阅读状态
     *
     * @param messageIds 消息id字符串，多个用逗号分隔
     * @author liuhanqing
     * @date 2021/1/4 21:21
     */
    void batchReadFlagByMessageIds(String messageIds, MessageReadFlagEnum flagEnum);
    /**
     * 删除系统消息
     *
     * @param messageId 消息id
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    void deleteByMessageId(Long messageId);

    /**
     * 批量删除系统消息
     *
     * @param messageIds 消息id字符串，多个用逗号分隔
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    void batchDeleteByMessageIds(String messageIds);

    /**
     * 查看系统消息
     *
     * @param messageRequest 查看参数
     * @return 系统消息
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    MessageResponse messageDetail(MessageRequest messageRequest);

    /**
     * 查询分页系统消息
     *
     * @param messageRequest 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    PageResult<MessageResponse> queryPage(MessageRequest messageRequest);

    /**
     * 查询系统消息
     *
     * @param messageRequest 查询参数
     * @return 系统消息列表
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    List<MessageResponse> queryList(MessageRequest messageRequest);

    /**
     * 查询分页系统消息 当前登录用户
     *
     * @param messageRequest 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    PageResult<MessageResponse> queryPageCurrentUser(MessageRequest messageRequest);

    /**
     * 查询系统消息 当前登录用户
     *
     * @param messageRequest 查询参数
     * @return 系统消息列表
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    List<MessageResponse> queryListCurrentUser(MessageRequest messageRequest);

    /**
     * 查询系统消息数量
     *
     * @param messageRequest 查询参数
     * @return 系统消息数量
     * @author liuhanqing
     * @date 2021/1/11 21:21
     */
    Integer queryCount(MessageRequest messageRequest);

    /**
     * 查询系统消息数量，当前登录用户
     *
     * @param messageRequest 查询参数
     * @return 系统消息数量
     * @author liuhanqing
     * @date 2021/1/11 21:21
     */
    Integer queryCountCurrentUser(MessageRequest messageRequest);


}
