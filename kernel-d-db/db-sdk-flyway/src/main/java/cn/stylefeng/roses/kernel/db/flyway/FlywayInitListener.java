/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.db.flyway;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.exception.DaoException;
import cn.stylefeng.roses.kernel.db.api.exception.enums.FlywayExceptionEnum;
import cn.stylefeng.roses.kernel.rule.listener.ContextInitializedListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 初始化flyway配置
 * <p>
 * 当spring装配好配置后开始初始化
 *
 * @author liuhanqing
 * @date 2021/1/17 21:14
 */
@Slf4j
public class FlywayInitListener extends ContextInitializedListener implements Ordered {

    private static final String FLYWAY_LOCATIONS = "classpath:db/migration/mysql";

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {

        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 判断是否开启 sharding jdbc
        Boolean enableSharding = environment.getProperty("spring.shardingsphere.enabled", Boolean.class);

        // 如果开启了sharding jdbc，则读取 sharding jdbc 主库配置
        if (ObjectUtil.isNotNull(enableSharding) && enableSharding) {
            driverClassName = environment.getProperty("spring.shardingsphere.datasource.master.driver-class-name");
            dataSourceUrl = environment.getProperty("spring.shardingsphere.datasource.master.url");
            dataSourceUsername = environment.getProperty("spring.shardingsphere.datasource.master.username");
            dataSourcePassword = environment.getProperty("spring.shardingsphere.datasource.master.password");
        }

        // flyway的配置
        String enabledStr = environment.getProperty("spring.flyway.enabled");
        String locations = environment.getProperty("spring.flyway.locations");
        String baselineOnMigrateStr = environment.getProperty("spring.flyway.baseline-on-migrate");
        String outOfOrderStr = environment.getProperty("spring.flyway.out-of-order");
        String placeholder = environment.getProperty("spring.flyway.placeholder-replacement");

        // 是否开启flyway，默认false.
        boolean enabled = false;
        if (StrUtil.isNotBlank(enabledStr)) {
            enabled = Boolean.parseBoolean(enabledStr);
        }

        // 如果未开启flyway 直接return
        if (!enabled) {
            return;
        }

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword, driverClassName)) {
            throw new DaoException(FlywayExceptionEnum.DB_CONFIG_ERROR);
        }

        // 当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移，默认false.
        boolean baselineOnMigrate = false;
        if (StrUtil.isNotBlank(baselineOnMigrateStr)) {
            baselineOnMigrate = Boolean.parseBoolean(baselineOnMigrateStr);
        }

        // 如果未设置flyway路径，则设置为默认flyway路径
        if (StrUtil.isBlank(locations)) {
            locations = FLYWAY_LOCATIONS;
        }

        // 是否允许无序的迁移 开发环境最好开启, 生产环境关闭
        boolean outOfOrder = false;
        if (StrUtil.isNotBlank(outOfOrderStr)) {
            outOfOrder = Boolean.parseBoolean(outOfOrderStr);
        }

        // 是否开启占位符
        boolean enablePlaceholder = true;
        if (StrUtil.isNotBlank(placeholder)) {
            enablePlaceholder = Boolean.parseBoolean(placeholder);
        }

        DriverManagerDataSource dmDataSource = null;
        try {
            assert dataSourceUrl != null;
            // 手动创建数据源
            dmDataSource = new DriverManagerDataSource();
            dmDataSource.setDriverClassName(driverClassName);
            dmDataSource.setUrl(dataSourceUrl);
            dmDataSource.setUsername(dataSourceUsername);
            dmDataSource.setPassword(dataSourcePassword);

            // flyway配置
            Flyway flyway = Flyway.configure()
                    .dataSource(dmDataSource)

                    // 迁移脚本的位置
                    .locations(locations)

                    // 当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移
                    .baselineOnMigrate(baselineOnMigrate)

                    // 是否允许无序的迁移 开发环境最好开启 , 生产环境关闭
                    .outOfOrder(outOfOrder)

                    // 是否开启占位符
                    .placeholderReplacement(enablePlaceholder)

                    .load();

            // 执行迁移
            flyway.migrate();

        } catch (Exception e) {
            log.error("flyway初始化失败", e);
            throw new DaoException(FlywayExceptionEnum.FLYWAY_MIGRATE_ERROR, e.getMessage());
        }

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
