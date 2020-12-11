package cn.stylefeng.roses.kernel.dsctn.starter;

import cn.stylefeng.roses.kernel.dsctn.DynamicDataSource;
import cn.stylefeng.roses.kernel.dsctn.aop.MultiSourceExchangeAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库连接和DAO框架的配置
 * <p>
 * 如果开启此连接池，注意排除 GunsDataSourceAutoConfiguration
 *
 * @author fengshuonan
 * @date 2020/11/30 22:24
 */
@Configuration
public class GunsDataSourceContainerAutoConfiguration {

    /**
     * 多数据源连接池，如果开启此连接池，注意排除 GunsDataSourceAutoConfiguration
     *
     * @author fengshuonan
     * @date 2020/11/30 22:49
     */
    @Bean
    public DynamicDataSource dataSource() {
        return new DynamicDataSource();
    }

    /**
     * 数据源切换的AOP
     *
     * @author fengshuonan
     * @date 2020/11/30 22:49
     */
    @Bean
    public MultiSourceExchangeAop multiSourceExchangeAop() {
        return new MultiSourceExchangeAop();
    }

}
