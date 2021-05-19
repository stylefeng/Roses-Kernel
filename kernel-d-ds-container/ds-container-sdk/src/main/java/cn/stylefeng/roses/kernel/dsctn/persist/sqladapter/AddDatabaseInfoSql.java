package cn.stylefeng.roses.kernel.dsctn.persist.sqladapter;

import cn.stylefeng.roses.kernel.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 添加数据源的sql
 *
 * @author fengshuonan
 * @date 2019-07-16-13:06
 */
@Getter
public class AddDatabaseInfoSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "INSERT INTO `sys_database_info`(`db_id`, `db_name`, `jdbc_driver`, `jdbc_url`, `username`, `password`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String sqlServer() {
        return "INSERT INTO [sys_database_info] ([db_id], [db_name], [jdbc_driver], [jdbc_url], [username], [password], [remarks], [create_time]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String pgSql() {
        return "INSERT INTO sys_database_info(db_id, db_name, jdbc_driver, jdbc_url, username, password,  remarks, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))";
    }

    @Override
    protected String oracle() {
        return "INSERT INTO sys_database_info(db_id, db_name, jdbc_driver, jdbc_url, username, password,  remarks, create_time)  VALUES (?, ?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
    }
}
