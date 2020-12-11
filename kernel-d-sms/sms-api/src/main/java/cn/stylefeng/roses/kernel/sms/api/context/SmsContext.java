package cn.stylefeng.roses.kernel.sms.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.sms.api.SmsSenderApi;

/**
 * 短信发送类快速获取
 *
 * @author fengshuonan
 * @date 2020/10/26 16:53
 */
public class SmsContext {

    /**
     * 获取短信发送接口
     *
     * @author fengshuonan
     * @date 2020/10/26 16:54
     */
    public static SmsSenderApi me() {
        return SpringUtil.getBean(SmsSenderApi.class);
    }

}
