package cn.stylefeng.roses.kernel.message.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.message.api.MessageApi;

/**
 * 消息操作api的获取
 *
 * @author liuhanqing
 * @date 2021/1/1 21:13
 */
public class MessageContext {

    /**
     * 获取消息操作api
     *
     * @author liuhanqing
     * @date 2021/1/1 21:13
     */
    public static MessageApi me() {
        return SpringUtil.getBean(MessageApi.class);
    }

}
