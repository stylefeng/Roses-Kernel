package cn.stylefeng.roses.kernel.config.modular.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.stylefeng.roses.kernel.config.ConfigContainer;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;
import cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum.APP_DB_CONFIG_ERROR;

/**
 * 初始化系统配置表
 * <p>
 * 当spring装配好配置后，就去数据库读constants
 * <p>
 *
 * @author stylefeng
 * @date 2020/6/6 23:39
 */
@Slf4j
public class ConfigInitListener implements ApplicationListener<ApplicationContextInitializedEvent>, Ordered {

    private static final String CONFIG_LIST_SQL = "select config_code, config_value from sys_config where status_flag = ? and del_flag = ?";

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE-100;
    }

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {

        // 如果是配置中心的上下文略过，spring cloud环境environment会读取不到
        ConfigurableApplicationContext applicationContext = applicationContextInitializedEvent.getApplicationContext();
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        // 初始化Config Api
        ConfigContext.setConfigApi(new ConfigContainer());

        ConfigurableEnvironment environment = applicationContextInitializedEvent.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            throw new ConfigException(APP_DB_CONFIG_ERROR);
        }

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            assert dataSourceUrl != null;
            conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);

            // 获取sys_config表的数据
            List<Entity> entityList = SqlExecutor.query(conn, CONFIG_LIST_SQL, new EntityListHandler(), StatusEnum.ENABLE.getCode(), YesOrNotEnum.N.getCode());

            // 将查询到的参数配置添加到缓存
            if (ObjectUtil.isNotEmpty(entityList)) {
                entityList.forEach(sysConfig -> ConfigContext.me().putConfig(sysConfig.getStr("config_code"), sysConfig.getStr("config_value")));
            }
        } catch (ClassNotFoundException e) {
            log.error(">>> 初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver类", e);
            throw new ConfigException(ConfigExceptionEnum.CLASS_NOT_FOUND_ERROR);
        } catch (SQLException sqlException) {
            log.error(">>> 初始化系统配置表失败，执行查询语句失败", sqlException);
            throw new ConfigException(ConfigExceptionEnum.CONFIG_SQL_EXE_ERROR);
        } finally {
            DbUtil.close(conn);
        }

    }
}
