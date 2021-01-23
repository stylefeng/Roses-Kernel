package cn.stylefeng.roses.kernel.dsctn.persist.sqls;

import lombok.Getter;

/**
 * 数据源列表sql
 *
 * @author fengshuonan
 * @date 2019-07-16-13:06
 */
@Getter
public class DatabaseListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select db_name,jdbc_driver,jdbc_url,username,password from sys_database_info";
    }

    @Override
    protected String sqlServer() {
        return "select db_name,jdbc_driver,jdbc_url,user_name,password from sys_database_info";
    }

    @Override
    protected String pgSql() {
        return "select db_name,jdbc_driver,jdbc_url,user_name,password from sys_database_info";
    }

    @Override
    protected String oracle() {
        return "select db_name,jdbc_driver,jdbc_url,user_name,password from sys_database_info";
    }
}
