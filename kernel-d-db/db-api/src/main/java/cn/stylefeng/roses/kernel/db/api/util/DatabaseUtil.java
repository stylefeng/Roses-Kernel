package cn.stylefeng.roses.kernel.db.api.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.exception.DaoException;
import cn.stylefeng.roses.kernel.db.api.exception.enums.DatabaseExceptionEnum;
import cn.stylefeng.roses.kernel.db.api.pojo.db.TableFieldInfo;
import cn.stylefeng.roses.kernel.db.api.pojo.db.TableInfo;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.db.api.sqladapter.database.CreateDatabaseSql;
import cn.stylefeng.roses.kernel.db.api.sqladapter.database.GetDatabasesSql;
import cn.stylefeng.roses.kernel.db.api.sqladapter.table.TableFieldListSql;
import cn.stylefeng.roses.kernel.db.api.sqladapter.table.TableListSql;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具类，可用来获取一些元数据
 *
 * @author fengshuonan
 * @date 2021/5/19 10:35
 */
@Slf4j
public class DatabaseUtil {

    /**
     * 获取数据库中的所有数据库列表
     *
     * @author fengshuonan
     * @date 2021/5/26 20:42
     */
    public static List<String> getDatabases(DruidProperties druidProperties) {
        Connection conn = null;
        List<String> databasesList = new ArrayList<>();
        try {
            Class.forName(druidProperties.getDriverClassName());
            conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement(new GetDatabasesSql().getSql(druidProperties.getUrl()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String database = resultSet.getString("database");
                if (StrUtil.isNotBlank(database)) {
                    if (StrUtil.startWith(database, RuleConstants.TENANT_DB_PREFIX)) {
                        database = database.replaceAll(RuleConstants.TENANT_DB_PREFIX, "");
                        databasesList.add(database);
                    }
                }
            }
            return databasesList;
        } catch (Exception e) {
            log.error("查询所有库错误！", e);
            throw new DaoException(DatabaseExceptionEnum.DATABASE_LIST_ERROR, e.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 查询某个数据库连接的所有表
     *
     * @author fengshuonan
     * @date 2021/5/19 10:35
     */
    public static List<TableInfo> selectTables(DruidProperties druidProperties) {
        List<TableInfo> tables = new ArrayList<>();
        Connection conn = null;
        try {
            Class.forName(druidProperties.getDriverClassName());
            conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            // 获取数据库名称
            String dbName = getDbName(druidProperties);

            // 构造查询语句
            PreparedStatement preparedStatement = conn.prepareStatement(new TableListSql().getSql(druidProperties.getUrl()));

            // 拼接设置数据库名称
            if (!druidProperties.getUrl().contains("sqlserver") && !druidProperties.getUrl().contains("postgresql")) {
                preparedStatement.setString(1, dbName);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TableInfo tableInfo = new TableInfo();
                String tableName = resultSet.getString("tableName");
                String tableComment = resultSet.getString("tableComment");
                tableInfo.setTableName(tableName);
                tableInfo.setTableComment(tableComment);
                tables.add(tableInfo);
            }
            return tables;
        } catch (Exception ex) {
            log.error("查询所有表错误！", ex);
            throw new DaoException(DatabaseExceptionEnum.TABLE_LIST_ERROR, ex.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 查询某个表的所有字段
     *
     * @author fengshuonan
     * @date 2021/5/19 11:01
     */
    public static List<TableFieldInfo> getTableFields(DruidProperties druidProperties, String tableName) {
        ArrayList<TableFieldInfo> fieldList = new ArrayList<>();
        Connection conn = null;
        try {
            Class.forName(druidProperties.getDriverClassName());
            conn = DriverManager.getConnection(
                    druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            PreparedStatement preparedStatement = conn.prepareStatement(new TableFieldListSql().getSql(druidProperties.getUrl()));

            if (druidProperties.getUrl().contains("oracle")) {
                preparedStatement.setString(1, tableName);
            } else if (druidProperties.getUrl().contains("postgresql")) {
                preparedStatement.setString(1, tableName);
            } else if (druidProperties.getUrl().contains("sqlserver")) {
                preparedStatement.setString(1, tableName);
            } else {
                String dbName = getDbName(druidProperties);
                preparedStatement.setString(1, tableName);
                preparedStatement.setString(2, dbName);
            }

            //执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TableFieldInfo tableFieldInfo = new TableFieldInfo();
                String columnName = resultSet.getString("columnName");
                String columnComment = resultSet.getString("columnComment");
                tableFieldInfo.setColumnName(columnName);
                tableFieldInfo.setColumnComment(columnComment);
                tableFieldInfo.setCamelFieldName(StrUtil.toCamelCase(columnName));
                fieldList.add(tableFieldInfo);
            }
            return fieldList;
        } catch (Exception ex) {
            log.error("查询表的所有字段错误！", ex);
            throw new DaoException(DatabaseExceptionEnum.FIELD_GET_ERROR, ex.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 创建数据库
     *
     * @author fengshuonan
     * @date 2021/5/19 10:39
     */
    public static void createDatabase(DruidProperties druidProperties, String databaseName) {
        Connection conn = null;
        try {
            Class.forName(druidProperties.getDriverClassName());
            conn = DriverManager.getConnection(druidProperties.getUrl(), druidProperties.getUsername(), druidProperties.getPassword());

            //创建sql
            String sql = new CreateDatabaseSql().getSql(druidProperties.getUrl());
            sql = sql.replaceAll("\\?", databaseName);

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            int i = preparedStatement.executeUpdate();
            log.info("创建数据库！数量：" + i);

        } catch (Exception ex) {
            log.error("执行sql出现问题！", ex);
            throw new DaoException(DatabaseExceptionEnum.CREATE_DATABASE_ERROR, ex.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 获取数据库名称
     * <p>
     * oracle的数据库会直接返回username作为数据库名称，这里需要注意一下，如果用户名不是数据库名则返回不准确
     *
     * @author fengshuonan
     * @date 2021/5/19 10:39
     */
    private static String getDbName(DruidProperties druidProperties) {

        if (druidProperties.getUrl().contains("oracle")) {
            // 如果是oracle，直接返回username
            return druidProperties.getUsername();
        } else if (druidProperties.getUrl().contains("postgresql")) {
            // postgresql，直接返回最后一个/后边的字符
            int first = druidProperties.getUrl().lastIndexOf("/") + 1;
            return druidProperties.getUrl().substring(first);
        } else if (druidProperties.getUrl().contains("sqlserver")) {
            // sqlserver，直接返回最后一个=后边的字符
            int first = druidProperties.getUrl().lastIndexOf("=") + 1;
            return druidProperties.getUrl().substring(first);
        } else {
            // mysql，返回/和?之间的字符
            String jdbcUrl = druidProperties.getUrl();
            int first = jdbcUrl.lastIndexOf("/") + 1;
            int last = jdbcUrl.indexOf("?");
            return jdbcUrl.substring(first, last);
        }
    }

}
