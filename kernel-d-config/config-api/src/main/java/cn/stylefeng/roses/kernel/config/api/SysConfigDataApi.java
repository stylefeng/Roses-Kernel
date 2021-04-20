package cn.stylefeng.roses.kernel.config.api;

import cn.hutool.db.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 系统配置元数据获取的api
 *
 * @author fengshuonan
 * @date 2021/3/27 21:15
 */
public interface SysConfigDataApi {

    /**
     * 获取系统配置表中的所有数据
     *
     * @param conn 原始数据库连接
     * @return 所有记录的list
     * @author fengshuonan
     * @date 2021/3/27 21:15
     */
    List<Entity> getConfigs(Connection conn) throws SQLException;

    /**
     * 获取所有配置list的sql
     *
     * @author fengshuonan
     * @date 2021/3/27 21:19
     */
    String getConfigListSql();

}
