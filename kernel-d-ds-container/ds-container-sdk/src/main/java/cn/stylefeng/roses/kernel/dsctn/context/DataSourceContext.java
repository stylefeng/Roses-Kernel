package cn.stylefeng.roses.kernel.dsctn.context;


import cn.stylefeng.roses.kernel.dsctn.persist.DataBaseInfoPersistence;
import com.alibaba.druid.pool.DruidDataSource;
import cn.stylefeng.roses.kernel.db.api.factory.DruidFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants.MASTER_DATASOURCE_NAME;


/**
 * 存放数据源容和数据源配置的容器
 *
 * @author fengshuonan
 * @date 2020/10/31 23:40
 */
public class DataSourceContext {

    /**
     * 数据源容器
     * <p>
     * key是数据源名称
     */
    private static final Map<String, DataSource> DATA_SOURCES = new ConcurrentHashMap<>();

    /**
     * 数据源的配置容器
     * <p>
     * key是数据源名称
     */
    private static Map<String, DruidProperties> DATA_SOURCES_CONF = new ConcurrentHashMap<>();

    /**
     * 初始化所有dataSource
     *
     * @author fengshuonan
     * @date 2020/10/31 23:40
     */
    public static void initDataSource(DruidProperties masterDataSourceProperties, DataSource dataSourcePrimary) {

        // 清空数据库中的主数据源信息
        new DataBaseInfoPersistence(masterDataSourceProperties).deleteMasterDatabaseInfo();

        // 初始化主数据源信息
        new DataBaseInfoPersistence(masterDataSourceProperties).createMasterDatabaseInfo();

        // 从数据库中获取所有的数据源信息
        DataBaseInfoPersistence dataBaseInfoDao = new DataBaseInfoPersistence(masterDataSourceProperties);
        Map<String, DruidProperties> allDataBaseInfo = dataBaseInfoDao.getAllDataBaseInfo();

        // 赋给全局变量
        DATA_SOURCES_CONF = allDataBaseInfo;

        // 根据数据源信息初始化所有的DataSource
        for (Map.Entry<String, DruidProperties> entry : allDataBaseInfo.entrySet()) {

            String dbName = entry.getKey();
            DruidProperties druidProperties = entry.getValue();

            // 如果是主数据源，不用初始化第二遍，如果是其他数据源就通过property初始化
            if (dbName.equalsIgnoreCase(MASTER_DATASOURCE_NAME)) {
                DATA_SOURCES_CONF.put(dbName, druidProperties);
                DATA_SOURCES.put(dbName, dataSourcePrimary);
            } else {
                DataSource dataSource = createDataSource(dbName, druidProperties);
                DATA_SOURCES.put(dbName, dataSource);
            }
        }
    }

    /**
     * 新增数据源到容器中
     *
     * @author fengshuonan
     * @date 2020/10/31 23:40
     */
    public static void addDataSource(String dbName, DataSource dataSource, DruidProperties druidProperties) {
        DATA_SOURCES.put(dbName, dataSource);
        DATA_SOURCES_CONF.put(dbName, druidProperties);
    }

    /**
     * 获取所有数据源
     *
     * @author fengshuonan
     * @date 2020/10/31 23:42
     */
    public static Map<String, DataSource> getDataSources() {
        return DATA_SOURCES;
    }

    /**
     * 获取数据源的配置
     *
     * @author fengshuonan
     * @date 2020/10/31 23:42
     */
    public static Map<String, DruidProperties> getDataSourcesConfs() {
        return DATA_SOURCES_CONF;
    }

    /**
     * 删除容器中的数据源
     *
     * @author fengshuonan
     * @date 2020/10/31 23:44
     */
    public static void removeDataSource(String dataSourceName) {

        // 先关闭掉数据源
        DataSource dataSource = DATA_SOURCES.get(dataSourceName);
        if (dataSource instanceof DruidDataSource) {
            ((DruidDataSource) dataSource).close();
        }

        // 从容器中删除数据源
        DATA_SOURCES.remove(dataSourceName);

        // 多数据源配置中也删除
        DATA_SOURCES_CONF.remove(dataSourceName);
    }

    /**
     * 创建数据源，但是不添加到容器中
     *
     * @author fengshuonan
     * @date 2020/10/31 23:43
     */
    private static DataSource createDataSource(String dataSourceName, DruidProperties druidProperties) {

        //添加到全局配置里
        DATA_SOURCES_CONF.put(dataSourceName, druidProperties);

        return DruidFactory.createDruidDataSource(druidProperties);
    }

}
