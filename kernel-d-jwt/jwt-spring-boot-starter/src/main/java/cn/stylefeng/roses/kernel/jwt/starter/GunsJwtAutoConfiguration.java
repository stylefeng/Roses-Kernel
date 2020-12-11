package cn.stylefeng.roses.kernel.jwt.starter;

import cn.stylefeng.roses.kernel.jwt.JwtTokenOperator;
import cn.stylefeng.roses.kernel.jwt.api.JwtApi;
import cn.stylefeng.roses.kernel.jwt.api.expander.JwtConfigExpander;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jwt的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 14:34
 */
@Configuration
public class GunsJwtAutoConfiguration {

    /**
     * jwt操作工具类的配置
     *
     * @author fengshuonan
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi() {

        JwtConfig jwtConfig = new JwtConfig();

        // 从系统配置表中读取配置
        jwtConfig.setJwtSecret(JwtConfigExpander.getJwtSecret());
        jwtConfig.setExpiredSeconds(JwtConfigExpander.getJwtTimeoutSeconds());

        return new JwtTokenOperator(jwtConfig);
    }

}
