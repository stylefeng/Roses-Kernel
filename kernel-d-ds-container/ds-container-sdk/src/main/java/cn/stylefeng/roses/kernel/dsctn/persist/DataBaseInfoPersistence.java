package cn.stylefeng.roses.kernel.dsctn.persist;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.dsctn.api.exception.DatasourceContainerException;
import cn.stylefeng.roses.kernel.dsctn.persist.sqls.AddDatabaseInfoSql;
import cn.stylefeng.roses.kernel.dsctn.persist.sqls.DatabaseListSql;
import cn.stylefeng.roses.kernel.dsctn.persist.sqls.DeleteDatabaseInfoSql;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants.MASTER_DATASOURCE_NAME;
import static cn.stylefeng.roses.kernel.dsctn.api.exception.enums.DatasourceContainerExceptionEnum.*;


/**
 * 数据源信息相关操作的dao
 *
 * @author fengshuonan
 * @date 2020/10/31 23:47
 */
@Slf4j
public class DataBaseInfoPersistence {

    private final DruidProperties druidProperties;

    public DataBaseInfoPersistence(DruidProperties druidProperties) {
        this.druidProperties = druidProperties;
    }

    /**
     * 查询所有数据源列表
     *
     * @author fengshuonan
     * @date 2020/10/31 23:55
     */
    public Map<String, DruidProperties> getAllDataBaseInfo() {
        Map<String, DruidProperties> dataSourceList = new HashMap<>(16);
        try {
            Class.forName(druidProperties.getDriverClassName());
            Connection conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            PreparedStatement preparedStatement = conn.prepareStatement(new DatabaseListSql().getSql(druidProperties.getUrl()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DruidProperties druidProperties = createDruidProperties(resultSet);
                String dbName = resultSet.getString("db_name");
                dataSourceList.put(dbName, druidProperties);
            }

            return dataSourceList;

        } catch (Exception exception) {
            log.error("查询数据源信息错误！", exception);
            String userTip = StrUtil.format(QUERY_DBS_DAO_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(QUERY_DBS_DAO_ERROR, userTip);
        }
    }

    /**
     * 初始化master的数据源，要和properties配置的数据源一致
     *
     * @author fengshuonan
     * @date 2020/10/31 23:55
     */
    public void createMasterDatabaseInfo() {
        try {
            Class.forName(druidProperties.getDriverClassName());
            Connection conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            PreparedStatement preparedStatement = conn.prepareStatement(new AddDatabaseInfoSql().getSql(druidProperties.getUrl()));

            preparedStatement.setLong(1, IdWorker.getId());

            preparedStatement.setString(2, MASTER_DATASOURCE_NAME);
            preparedStatement.setString(3, druidProperties.getDriverClassName());
            preparedStatement.setString(4, druidProperties.getUrl());
            preparedStatement.setString(5, druidProperties.getUsername());
            preparedStatement.setString(6, druidProperties.getPassword());
            preparedStatement.setString(7, "主数据源，项目启动数据源！");
            preparedStatement.setString(8, DateUtil.formatDateTime(new Date()));

            int i = preparedStatement.executeUpdate();
            log.info("初始化master的databaseInfo信息！初始化" + i + "条！");
        } catch (Exception exception) {
            log.error("初始化master的databaseInfo信息错误！", exception);
            String userTip = StrUtil.format(INSERT_DBS_DAO_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(INSERT_DBS_DAO_ERROR, userTip);
        }
    }

    /**
     * 删除master的数据源信息
     *
     * @author fengshuonan
     * @date 2020/10/31 23:55
     */
    public void deleteMasterDatabaseInfo() {
        try {
            Class.forName(druidProperties.getDriverClassName());
            Connection conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            PreparedStatement preparedStatement = conn.prepareStatement(new DeleteDatabaseInfoSql().getSql(druidProperties.getUrl()));
            preparedStatement.setString(1, MASTER_DATASOURCE_NAME);
            int i = preparedStatement.executeUpdate();
            log.info("删除master的databaseInfo信息！删除" + i + "条！");
        } catch (Exception exception) {
            log.info("删除master的databaseInfo信息失败！", exception);
            String userTip = StrUtil.format(DELETE_DBS_DAO_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(DELETE_DBS_DAO_ERROR, userTip);
        }
    }

    /**
     * 通过查询结果组装druidProperties
     *
     * @author fengshuonan
     * @date 2020/10/31 23:55
     */
    private DruidProperties createDruidProperties(ResultSet resultSet) {

        DruidProperties druidProperties = new DruidProperties();

        druidProperties.setTestOnBorrow(true);
        druidProperties.setTestOnReturn(true);

        try {
            druidProperties.setDriverClassName(resultSet.getString("jdbc_driver"));
            druidProperties.setUrl(resultSet.getString("jdbc_url"));
            druidProperties.setUsername(resultSet.getString("username"));
            druidProperties.setPassword(resultSet.getString("password"));
        } catch (SQLException exception) {
            log.info("根据数据库查询结果，创建DruidProperties失败！", exception);
            String userTip = StrUtil.format(CREATE_PROP_DAO_ERROR.getUserTip(), exception.getMessage());
            throw new DatasourceContainerException(CREATE_PROP_DAO_ERROR, userTip);
        }

        return druidProperties;
    }

}
