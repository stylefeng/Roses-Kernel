package cn.stylefeng.roses.kernel.email.api.pojo.aliyun;

import lombok.Data;

/**
 * 阿里云邮件发送的配置
 *
 * @author fengshuonan
 * @date 2020/10/30 22:35
 */
@Data
public class AliyunMailSenderProperties {

    /**
     * 发送邮件的key
     */
    private String accessKeyId;

    /**
     * 发送邮件的secret
     */
    private String accessKeySecret;

    /**
     * 发信人的地址，控制台配置
     */
    private String accountName;

}
