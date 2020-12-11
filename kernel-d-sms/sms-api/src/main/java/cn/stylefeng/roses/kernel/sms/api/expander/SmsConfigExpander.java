package cn.stylefeng.roses.kernel.sms.api.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;

/**
 * 短信相关的配置拓展
 *
 * @author fengshuonan
 * @date 2020/10/26 22:09
 */
public class SmsConfigExpander {

    /**
     * 获取短信验证码失效时间（单位：秒）
     * <p>
     * 默认300秒
     *
     * @author fengshuonan
     * @date 2020/10/26 22:09
     */
    public static Integer getSmsValidateExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SMS_VALIDATE_EXPIRED_SECONDS", Integer.class, 300);
    }

    /**
     * 阿里云短信的accessKeyId
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeyId() {
        return ConfigContext.me().getConfigValue("SYS_ALIYUN_SMS_ACCESS_KEY_ID", String.class);
    }

    /**
     * 阿里云短信的accessKeySecret
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsAccessKeySecret() {
        return ConfigContext.me().getConfigValue("SYS_ALIYUN_SMS_ACCESS_KEY_SECRET", String.class);
    }

    /**
     * 阿里云短信的签名
     *
     * @author fengshuonan
     * @date 2020/12/1 21:20
     */
    public static String getAliyunSmsSignName() {
        return ConfigContext.me().getConfigValue("SYS_ALIYUN_SMS_SIGN_NAME", String.class);
    }

}
