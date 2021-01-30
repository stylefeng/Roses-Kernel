package cn.stylefeng.roses.kernel.groovy.starter;

import cn.stylefeng.roses.kernel.groovy.GroovyOperator;
import cn.stylefeng.roses.kernel.groovy.api.GroovyApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Groovy的自动配置
 *
 * @author fengshuonan
 * @date 2021/1/29 11:23
 */
@Configuration
public class GunsGroovyAutoConfiguration {

    /**
     * Groovy的操作类
     *
     * @author fengshuonan
     * @date 2021/1/29 11:23
     */
    @Bean
    @ConditionalOnMissingBean(GroovyApi.class)
    public GroovyApi fileOperatorApi() {
        return new GroovyOperator();
    }

}
