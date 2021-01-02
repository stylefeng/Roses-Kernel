package cn.stylefeng.roses.kernel.message.api.exception;

import cn.stylefeng.roses.kernel.message.api.constants.MessageConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 消息异常枚举
 *
 * @author liuhanqing
 * @date 2021/1/1 20:55
 */
public class MessageException extends ServiceException {

    public MessageException(AbstractExceptionEnum exception) {
        super(MessageConstants.MESSAGE_MODULE_NAME, exception);
    }

}
