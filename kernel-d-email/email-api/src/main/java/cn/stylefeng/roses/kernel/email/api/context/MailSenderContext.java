package cn.stylefeng.roses.kernel.email.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.email.api.MailSenderApi;

/**
 * 邮件发送的api上下文
 *
 * @author fengshuonan
 * @date 2020/10/26 10:16
 */
public class MailSenderContext {

    /**
     * 获取邮件发送的接口
     *
     * @author fengshuonan
     * @date 2020/10/26 10:16
     */
    public static MailSenderApi me() {
        return SpringUtil.getBean(MailSenderApi.class);
    }

}
