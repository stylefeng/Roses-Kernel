package cn.stylefeng.roses.kernel.config.modular.factory;

import cn.stylefeng.roses.kernel.config.api.SysConfigDataApi;
import cn.stylefeng.roses.kernel.config.modular.sqladapter.MssqlSysConfigData;
import cn.stylefeng.roses.kernel.config.modular.sqladapter.MysqlSysConfigData;
import cn.stylefeng.roses.kernel.config.modular.sqladapter.OracleSysConfigData;
import cn.stylefeng.roses.kernel.config.modular.sqladapter.PgsqlSysConfigData;
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
            return new MssqlSysConfigData();
        } else if (DbTypeEnum.ORACLE.equals(dbType)) {
            return new OracleSysConfigData();
        } else if (DbTypeEnum.DM.equals(dbType)) {
            return new OracleSysConfigData();
        }
        return new MysqlSysConfigData();
    }

}
