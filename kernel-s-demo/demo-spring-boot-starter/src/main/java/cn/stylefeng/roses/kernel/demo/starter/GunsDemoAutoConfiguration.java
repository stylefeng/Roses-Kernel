package cn.stylefeng.roses.kernel.demo.starter;

import cn.stylefeng.roses.kernel.demo.interceptor.DemoProfileSqlInterceptor;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 演示环境的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 21:51
 */
@Configuration
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class GunsDemoAutoConfiguration {

    /**
     * 演示环境的sql拦截器
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    public DemoProfileSqlInterceptor demoProfileSqlInterceptor() {
        return new DemoProfileSqlInterceptor();
    }

}
