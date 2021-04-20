/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.db.init.util;

import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql操作工具
 *
 * @author fengshuonan
 * @date 2019/12/29 16:37
 */
@Slf4j
public class SqlExe {
    /**
     * 获取一条数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static Map<String, Object> selectOne(DataSource dataSource, String sql, Object... params) {

        RsHandler<Map<String, Object>> rsHandler = SqlUtil::resultSet2Map;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.query(conn, sql, rsHandler, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return new HashMap<>();
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 获取一条数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static Map<String, Object> selectOne(String sql, Object... params) {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        return selectOne(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static List<Map<String, Object>> selectList(DataSource dataSource, String sql, Object... params) {

        RsHandler<List<Map<String, Object>>> rsHandler = SqlUtil::resultSet2ListMap;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.query(conn, sql, rsHandler, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return new ArrayList<>();
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 查询多条记录
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static List<Map<String, Object>> selectList(String sql, Object... params) {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        return selectList(dataSource, sql, params);
    }

    /**
     * 更新数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int update(DataSource dataSource, String sql, Object... params) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.execute(conn, sql, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return 0;
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 更新数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int update(String sql, Object... params) {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        return update(dataSource, sql, params);
    }

    /**
     * 新增数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int insert(DataSource dataSource, String sql, Object... params) {
        return update(dataSource, sql, params);
    }

    /**
     * 新增数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int insert(String sql, Object... params) {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        return insert(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int delete(DataSource dataSource, String sql, Object... params) {
        return update(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     * @author fengshuonan
     * @date 2019/12/29 16:37
     */
    public static int delete(String sql, Object... params) {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        return delete(dataSource, sql, params);
    }

}
