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
package cn.stylefeng.roses.kernel.db.mp.dbid;

import cn.stylefeng.roses.kernel.rule.enums.DbTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * 数据库id选择器，用在一个mapper.xml中包含多种数据库的sql时候
 * <p>
 * 提供给mybatis能识别不同数据库的标识
 *
 * @author fengshuonan
 * @date 2020/10/16 17:02
 */
@Slf4j
public class CustomDatabaseIdProvider implements DatabaseIdProvider {

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {

        String url = "";

        // 常规获取url的方式
        try {
            url = dataSource.getConnection().getMetaData().getURL();
        } catch (Exception e) {

            // 兼容对seata数据源的判断
            try {
                Class<?> XAClass = Class.forName("io.seata.rm.datasource.xa.DataSourceProxyXA");

                // xa的dataSource
                if (XAClass.isInstance(dataSource)) {
                    Method xaMethod = XAClass.getMethod("getResourceId");
                    Object xaResult = xaMethod.invoke(dataSource);
                    url = xaResult.toString();
                }

            } catch (Exception e2) {
                log.warn("CustomDatabaseIdProvider无法判断当前数据源类型，默认选择Mysql类型");
                return DbTypeEnum.MYSQL.getXmlDatabaseId();
            }
        }

        // 达梦和oracle使用同一种
        if (url.contains(DbTypeEnum.ORACLE.getUrlWords())) {
            return DbTypeEnum.ORACLE.getXmlDatabaseId();
        }
        if (url.contains(DbTypeEnum.DM.getUrlWords())) {
            return DbTypeEnum.ORACLE.getXmlDatabaseId();
        }

        if (url.contains(DbTypeEnum.MS_SQL.getUrlWords())) {
            return DbTypeEnum.MS_SQL.getXmlDatabaseId();
        }
        if (url.contains(DbTypeEnum.PG_SQL.getUrlWords())) {
            return DbTypeEnum.PG_SQL.getXmlDatabaseId();
        }

        return DbTypeEnum.MYSQL.getXmlDatabaseId();
    }

}
