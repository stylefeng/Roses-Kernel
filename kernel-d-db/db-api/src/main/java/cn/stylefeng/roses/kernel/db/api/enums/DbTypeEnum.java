package cn.stylefeng.roses.kernel.db.api.enums;

import lombok.Getter;

/**
 * 不同数据库类型的枚举
 * <p>
 * 用于标识mapping.xml中不同数据库的标识
 *
 * @author fengshuonan
 * @date 2020/6/20 21:08
 */
@Getter
public enum DbTypeEnum {

    /**
     * mysql
     */
    MYSQL("mysql", "select 1"),

    /**
     * pgsql
     */
    PG_SQL("postgresql", "select version()"),

    /**
     * oracle
     */
    ORACLE("oracle", "select 1 from dual"),

    /**
     * mssql
     */
    MS_SQL("sqlserver", "select 1");

    /**
     * 数据库编码，也是datasource url上会有的关键字
     */
    private final String code;

    /**
     * 检测数据库是否运行的sql语句
     */
    private final String validateQuery;

    DbTypeEnum(String code, String validateQuery) {
        this.code = code;
        this.validateQuery = validateQuery;
    }

}
