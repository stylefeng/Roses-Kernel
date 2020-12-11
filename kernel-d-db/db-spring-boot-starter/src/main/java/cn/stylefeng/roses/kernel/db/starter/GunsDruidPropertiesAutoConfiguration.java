package cn.stylefeng.roses.kernel.db.starter;

import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库连接和DAO框架的配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:24
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class GunsDruidPropertiesAutoConfiguration {

    /**
     * druid属性配置
     *
     * @author fengshuonan
     * @date 2020/11/30 22:36
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean(DruidProperties.class)
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

}
