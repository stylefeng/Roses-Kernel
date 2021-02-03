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