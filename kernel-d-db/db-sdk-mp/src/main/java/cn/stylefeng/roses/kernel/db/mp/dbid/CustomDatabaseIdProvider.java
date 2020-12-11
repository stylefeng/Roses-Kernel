package cn.stylefeng.roses.kernel.db.mp.dbid;

import cn.stylefeng.roses.kernel.db.api.enums.DbTypeEnum;
import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据库id选择器，用在一个mapper.xml中包含多种数据库的sql时候
 * <p>
 * 提供给mybatis能识别不同数据库的标识
 *
 * @author fengshuonan
 * @date 2020/10/16 17:02
 */
public class CustomDatabaseIdProvider implements DatabaseIdProvider {

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {

        String url = dataSource.getConnection().getMetaData().getURL();

        if (url.contains(DbTypeEnum.ORACLE.getCode())) {
            return DbTypeEnum.ORACLE.getCode();
        }
        if (url.contains(DbTypeEnum.MS_SQL.getCode())) {
            return DbTypeEnum.MS_SQL.getCode();
        }
        if (url.contains(DbTypeEnum.PG_SQL.getCode())) {
            return DbTypeEnum.PG_SQL.getCode();
        }

        return DbTypeEnum.MYSQL.getCode();
    }

}
