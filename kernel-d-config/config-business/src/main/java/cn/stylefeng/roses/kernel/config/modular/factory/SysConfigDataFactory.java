package cn.stylefeng.roses.kernel.config.modular.factory;

import cn.stylefeng.roses.kernel.config.api.SysConfigDataApi;
import cn.stylefeng.roses.kernel.config.modular.data.MysqlSysConfigData;
import cn.stylefeng.roses.kernel.config.modular.data.PgsqlSysConfigData;
import cn.stylefeng.roses.kernel.rule.enums.DbTypeEnum;
import cn.stylefeng.roses.kernel.rule.util.DatabaseTypeUtil;

/**
 * SysConfigDataApi的创建工厂
 *
 * @author fengshuonan
 * @date 2021/3/27 21:27
 */
public class SysConfigDataFactory {

    /**
     * 通过jdbc url获取api
     *
     * @author fengshuonan
     * @date 2021/3/27 21:27
     */
    public static SysConfigDataApi getSysConfigDataApi(String jdbcUrl) {
        DbTypeEnum dbType = DatabaseTypeUtil.getDbType(jdbcUrl);
        if (DbTypeEnum.MYSQL.equals(dbType)) {
            return new MysqlSysConfigData();
        } else if (DbTypeEnum.PG_SQL.equals(dbType)) {
            return new PgsqlSysConfigData();
        } else if (DbTypeEnum.MS_SQL.equals(dbType)) {
            // todo
        } else if (DbTypeEnum.ORACLE.equals(dbType)) {
            // todo
        }
        return new MysqlSysConfigData();
    }

}
