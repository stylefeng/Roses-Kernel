package cn.stylefeng.roses.kernel.message.api;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.enums.MessageReadFlagEnum;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageResponse;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageSendParam;
import cn.stylefeng.roses.kernel.message.api.pojo.MessageParam;


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
     * @param messageSendParam 系统消息参数
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    void sendMessage(MessageSendParam messageSendParam);

    /**
     * 更新阅读状态
     *
     * @param messageParam 系统消息参数
     * @author liuhanqing
     * @date 2021/1/2 22:15
     */
    void updateReadFlag(MessageParam messageParam);

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
     * @param messageParam 查看参数
     * @return 系统消息
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    MessageResponse messageDetail(MessageParam messageParam);

    /**
     * 查询系统消息
     *
     * @param messageParam 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    PageResult<MessageResponse> queryMessagePage(MessageParam messageParam);

    /**
     * 查询系统消息
     *
     * @param messageParam 查询参数
     * @return 系统消息列表
     * @author liuhanqing
     * @date 2021/1/2 21:21
     */
    List<MessageResponse> queryMessageList(MessageParam messageParam);

}