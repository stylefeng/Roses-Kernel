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
package cn.stylefeng.roses.kernel.db.api.factory;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.rule.enums.DbTypeEnum;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * druid连接池创建工厂
 *
 * @author fengshuonan
 * @date 2020/10/16 15:59
 */
@Slf4j
public class DruidDatasourceFactory {

    /**
     * 创建druid连接池
     *
     * @author fengshuonan
     * @date 2020/10/16 16:00
     */
    public static DruidDataSource createDruidDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = new DruidDataSource();

        // 数据库连接相关设置
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());

        // 驱动
        if (StrUtil.isNotBlank(druidProperties.getDriverClassName())) {
            dataSource.setDriverClassName(druidProperties.getDriverClassName());
        }

        // 定义初始连接数
        dataSource.setInitialSize(druidProperties.getInitialSize());

        // 定义最大连接数
        dataSource.setMaxActive(druidProperties.getMaxActive());

        // 最小空闲
        dataSource.setMinIdle(druidProperties.getMinIdle());

        // 最长等待时间
        dataSource.setMaxWait(druidProperties.getMaxWait());

        // 是否缓存preparedStatement
        dataSource.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());

        // PSCache数量
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());

        // 检测连接是否有效的sql
        if (StrUtil.isNotEmpty(druidProperties.getUrl())) {
            dataSource.setValidationQuery(getValidateQueryByUrl(druidProperties.getUrl()));
        }

        // 检测连接是否有效的超时时间
        dataSource.setValidationQueryTimeout(druidProperties.getValidationQueryTimeout());

        // 连接获取时候的检测
        dataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
        dataSource.setTestOnReturn(druidProperties.getTestOnReturn());
        dataSource.setTestWhileIdle(druidProperties.getTestWhileIdle());

        // 连接池中的minIdle数量以内的连接
        dataSource.setKeepAlive(druidProperties.getKeepAlive());

        // 检测的间隔时间
        dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());

        // 保持空闲的连接多久以后会被清除
        dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());

        try {
            dataSource.setFilters(druidProperties.getFilters());
        } catch (SQLException e) {
            log.error("数据库连接池初始化异常：{}", e.getMessage());
        }

        return dataSource;
    }

    /**
     * 根据数据库url获取validate query
     *
     * @param url 数据库配置的url
     * @author fengshuonan
     * @date 2020/10/16 16:12
     */
    private static String getValidateQueryByUrl(String url) {
        for (DbTypeEnum value : DbTypeEnum.values()) {
            if (url.contains(value.getUrlWords())) {
                return value.getValidateQuery();
            }
        }

        return DbTypeEnum.MYSQL.getValidateQuery();
    }

}
