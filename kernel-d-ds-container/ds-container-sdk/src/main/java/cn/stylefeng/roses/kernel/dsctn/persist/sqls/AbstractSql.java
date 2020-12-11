package cn.stylefeng.roses.kernel.dsctn.persist.sqls;

import cn.stylefeng.roses.kernel.db.api.enums.DbTypeEnum;

/**
 * 异构sql获取
 *
 * @author fengshuonan
 * @date 2020/10/31 23:44
 */
public abstract class AbstractSql {

    /**
     * 获取异构sql
     *
     * @param jdbcUrl 数据连接的url
     * @return 具体的sql
     * @author fengshuonan
     * @date 2020/10/31 23:44
     */
    public String getSql(String jdbcUrl) {
        if (jdbcUrl.contains(DbTypeEnum.ORACLE.getCode())) {
            return oracle();
        }
        if (jdbcUrl.contains(DbTypeEnum.MS_SQL.getCode())) {
            return sqlServer();
        }
        if (jdbcUrl.contains(DbTypeEnum.PG_SQL.getCode())) {
            return pgSql();
        }
        return mysql();
    }

    /**
     * 获取mysql的sql语句
     *
     * @return 具体的sql
     * @author fengshuonan
     * @date 2020/10/31 23:45
     */
    protected abstract String mysql();

    /**
     * 获取sqlServer的sql语句
     *
     * @return 具体的sql
     * @author fengshuonan
     * @date 2020/10/31 23:45
     */
    protected abstract String sqlServer();

    /**
     * 获取pgSql的sql语句
     *
     * @return 具体的sql
     * @author fengshuonan
     * @date 2020/10/31 23:45
     */
    protected abstract String pgSql();

    /**
     * 获取oracle的sql语句
     *
     * @return 具体的sql
     * @author fengshuonan
     * @date 2020/10/31 23:45
     */
    protected abstract String oracle();

}
