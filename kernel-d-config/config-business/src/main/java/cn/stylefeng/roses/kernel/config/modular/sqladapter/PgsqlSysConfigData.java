package cn.stylefeng.roses.kernel.config.modular.sqladapter;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.stylefeng.roses.kernel.config.api.SysConfigDataApi;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Pgsql数据库的系统配置表获取
 *
 * @author AndrewFeng
 * @date 2021/5/14 21:18
 */
@Slf4j
public class PgsqlSysConfigData implements SysConfigDataApi {

    @Override
    public List<Entity> getConfigs(Connection conn) throws SQLException {
        return SqlExecutor.query(conn, getConfigListSql(), new EntityListHandler(), StatusEnum.ENABLE.getCode(), YesOrNotEnum.N.getCode());
    }

    @Override
    public String getConfigListSql() {
        return "select config_code, config_value from sys_config where status_flag = ? and del_flag = ?";
    }

}
