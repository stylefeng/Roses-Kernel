package cn.stylefeng.roses.kernel.wrapper.starter;

import cn.stylefeng.roses.kernel.wrapper.WrapperAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wrapper的自动配置
 *
 * @author fengshuonan
 * @date 2021/1/19 22:42
 */
@Configuration
public class GunsWrapperAutoConfiguration {

    /**
     * Wrapper的自动配置
     *
     * @author fengshuonan
     * @date 2021/1/19 22:42
     */
    @Bean
    @ConditionalOnMissingBean(WrapperAop.class)
    public WrapperAop wrapperAop() {
        return new WrapperAop();
    }

}
