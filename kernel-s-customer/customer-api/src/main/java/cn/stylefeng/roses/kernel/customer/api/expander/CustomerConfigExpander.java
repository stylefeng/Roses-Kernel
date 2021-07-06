package cn.stylefeng.roses.kernel.customer.api.expander;


import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;

/**
 * C端用户的配置
 *
 * @author fengshuonan
 * @date 2021/6/7 15:38
 */
public class CustomerConfigExpander {

    /**
     * 获取注册邮件的标题
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static String getRegMailTitle() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_REG_EMAIL_TITLE", String.class, "Guns官方论坛-激活");
    }

    /**
     * 获取注册邮件的内容模板
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static String getRegMailContent() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_REG_EMAIL_CONTENT", String.class, "感谢您注册Guns官方论坛，请点击此激活链接激活您的账户：<a href=\"http://localhost:8080/customer/active?verifyCode={}\">http://localhost:8080/customer/active?verifyCode={} </a>");
    }

    /**
     * 获取重置密码的邮件标题
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static String getResetPwdMailTitle() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_RESET_PWD_EMAIL_TITLE", String.class, "Guns官网验证");
    }

    /**
     * 获取重置密码的邮件内容
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static String getResetPwdMailContent() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_RESET_PWD_EMAIL_CONTENT", String.class, "您的验证码是【{}】，此验证码用于修改登录密码，请不要泄露给他人，如果不是您本人操作，请忽略此邮件。");
    }

    /**
     * 存放用户头像的bucket的名称
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static String getCustomerBucket() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_FILE_BUCKET", String.class, "customer-bucket");
    }

    /**
     * 存放用户头像的bucket的名称的过期时间
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static Long getCustomerBucketExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_FILE_BUCKET_EXPIRED_SECONDS", Long.class, 600L);
    }

    /**
     * 获取用户缓存的过期时间
     *
     * @author fengshuonan
     * @date 2021/6/7 15:42
     */
    public static Long getCustomerCacheExpiredSeconds() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_CACHE_EXPIRED_SECONDS", Long.class, 3600L);
    }

    /**
     * 是否开启旧版密码校验
     *
     * @author fengshuonan
     * @date 2021/7/6 22:00
     */
    public static Boolean getOldPasswordValidate() {
        return ConfigContext.me().getSysConfigValueWithDefault("CUSTOMER_OPEN_OLD_PASSWORD_VALIDATE", Boolean.class, Boolean.FALSE);
    }

}
