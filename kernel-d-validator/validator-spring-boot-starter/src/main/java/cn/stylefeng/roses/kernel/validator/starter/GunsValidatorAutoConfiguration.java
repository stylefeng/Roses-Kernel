package cn.stylefeng.roses.kernel.validator.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Guns的校验器的自动配置
 *
 * @author fengshuonan
 * @date 2021/3/18 16:03
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class GunsValidatorAutoConfiguration {

    /**
     * 自定义的spring参数校验器，重写主要为了保存一些在自定义validator中读不到的属性
     *
     * @author fengshuonan
     * @date 2020/8/12 20:18
     */
    @Bean
    public GunsValidator gunsValidator() {
        return new GunsValidator();
    }

}
