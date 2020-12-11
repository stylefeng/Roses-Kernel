package cn.stylefeng.roses.kernel.db.starter;

import com.alibaba.druid.pool.DruidDataSource;
import cn.stylefeng.roses.kernel.db.api.factory.DruidFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 数据库连接和DAO框架的配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:24
 */
@Configuration
@Import(GunsDruidPropertiesAutoConfiguration.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class GunsDataSourceAutoConfiguration {

    /**
     * druid数据库连接池
     *
     * @author fengshuonan
     * @date 2020/11/30 22:37
     */
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(DataSource.class)
    public DruidDataSource druidDataSource(DruidProperties druidProperties) {
        return DruidFactory.createDruidDataSource(druidProperties);
    }

}
