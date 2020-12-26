package cn.stylefeng.roses.kernel.db.starter;

import cn.stylefeng.roses.kernel.db.init.listener.InitTableListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库初始话监听器自动配置
 *
 * @author fengshuonan
 * @date 2020/12/26 22:47
 */
@Configuration
public class GunsDbInitListenerAutoConfiguration {

    @Bean
    public InitTableListener initTableListener() {
        return new InitTableListener();
    }

}
