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

}
