package cn.stylefeng.roses.kernel.dsctn.modular.factory;

import cn.stylefeng.roses.kernel.db.api.pojo.druid.DruidProperties;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;

/**
 * Druid数据源创建
 *
 * @author fengshuonan
 * @date 2020/11/1 21:44
 */
public class DruidFactory {

    /**
     * 创建druid配置
     *
     * @author fengshuonan
     * @date 2019-06-15 20:05
     */
    public static DruidProperties createDruidProperties(DatabaseInfo databaseInfo) {
        DruidProperties druidProperties = new DruidProperties();
        druidProperties.setDriverClassName(databaseInfo.getJdbcDriver());
        druidProperties.setUsername(databaseInfo.getUserName());
        druidProperties.setPassword(databaseInfo.getPassword());
        druidProperties.setUrl(databaseInfo.getJdbcUrl());
        return druidProperties;
    }

}
