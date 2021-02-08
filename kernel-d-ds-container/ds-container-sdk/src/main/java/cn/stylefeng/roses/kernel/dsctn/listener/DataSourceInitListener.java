package cn.stylefeng.roses.kernel.dsctn.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.DruidDatasourceFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.dsctn.api.exception.DatasourceContainerException;
import cn.stylefeng.roses.kernel.dsctn.context.DataSourceContext;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import static cn.stylefeng.roses.kernel.dsctn.api.exception.enums.DatasourceContainerExceptionEnum.DB_CONNECTION_INFO_EMPTY_ERROR;
import static cn.stylefeng.roses.kernel.dsctn.api.exception.enums.DatasourceContainerExceptionEnum.INIT_DATASOURCE_CONTAINER_ERROR;


/**
 * 多数据源的初始化，加入到数据源Context中的过程
 *
 * @author fengshuonan
 * @date 2020/11/1 0:02
 */
@Slf4j
public class DataSourceInitListener implements ApplicationListener<ApplicationContextInitializedEvent>, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {

        // 如果是配置中心的上下文略过，spring cloud环境environment会读取不到
        ConfigurableApplicationContext applicationContext = applicationContextInitializedEvent.getApplicationContext();
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        ConfigurableEnvironment environment = applicationContextInitializedEvent.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            String userTip = StrUtil.format(DB_CONNECTION_INFO_EMPTY_ERROR.getUserTip(), dataSourceUrl, dataSourceUsername);
            throw new DatasourceContainerException(DB_CONNECTION_INFO_EMPTY_ERROR, userTip);
        }

        // 创建主数据源的properties
        DruidProperties druidProperties = new DruidProperties();
        druidProperties.setDriverClassName(dataSourceDriver);
        druidProperties.setUrl(dataSourceUrl);
        druidProperties.setUsername(dataSourceUsername);
        druidProperties.setPassword(dataSourcePassword);

        // 创建主数据源
        DruidDataSource druidDataSource = DruidDatasourceFactory.createDruidDataSource(druidProperties);

        // 初始化数据源容器
        try {
            DataSourceContext.initDataSource(druidProperties, druidDataSource);
        } catch (Exception exception) {
            log.error("初始化数据源容器错误!", exception);
            String userTip = StrUtil.format(INIT_DATASOURCE_CONTAINER_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(INIT_DATASOURCE_CONTAINER_ERROR, userTip);
        }

    }
}
