package cn.stylefeng.roses.kernel.sms.starter;

import cn.stylefeng.roses.kernel.sms.aliyun.AliyunSmsSender;
import cn.stylefeng.roses.kernel.sms.aliyun.msign.impl.MapBasedMultiSignManager;
import cn.stylefeng.roses.kernel.sms.api.SmsSenderApi;
import cn.stylefeng.roses.kernel.sms.api.expander.SmsConfigExpander;
import cn.stylefeng.roses.kernel.sms.api.pojo.AliyunSmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信的自动配置类
 *
 * @author fengshuonan
 * @date 2020/12/1 21:18
 */
@Configuration
public class GunsSmsAutoConfiguration {

    /**
     * 短信发送器的配置
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(SmsSenderApi.class)
    public SmsSenderApi smsSenderApi() {

        AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();

        // 配置默认从系统配置表读取
        aliyunSmsProperties.setAccessKeyId(SmsConfigExpander.getAliyunSmsAccessKeyId());
        aliyunSmsProperties.setAccessKeySecret(SmsConfigExpander.getAliyunSmsAccessKeySecret());
        aliyunSmsProperties.setSignName(SmsConfigExpander.getAliyunSmsSignName());

        return new AliyunSmsSender(new MapBasedMultiSignManager(), aliyunSmsProperties);
    }

}
