package cn.stylefeng.roses.kernel.db.api.factory;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.enums.DbTypeEnum;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
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
        if (StrUtil.isEmpty(druidProperties.getUrl())) {
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
            log.error(">>> 数据库连接池初始化异常：{}", e.getMessage());
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
        if (url.contains(DbTypeEnum.ORACLE.getCode())) {
            return DbTypeEnum.ORACLE.getValidateQuery();
        }
        if (url.contains(DbTypeEnum.MS_SQL.getCode())) {
            return DbTypeEnum.MS_SQL.getValidateQuery();
        }
        if (url.contains(DbTypeEnum.PG_SQL.getCode())) {
            return DbTypeEnum.PG_SQL.getValidateQuery();
        }

        return DbTypeEnum.MYSQL.getValidateQuery();
    }

}
