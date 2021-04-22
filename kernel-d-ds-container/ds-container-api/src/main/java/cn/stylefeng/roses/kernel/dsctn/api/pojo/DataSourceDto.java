package cn.stylefeng.roses.kernel.dsctn.api.pojo;

import lombok.Data;

/**
 * 数据库连接信息传输DTO
 *
 * @author fengshuonan
 * @date 2021/4/22 14:20
 */
@Data
public class DataSourceDto {

    /**
     * jdbc的驱动类型
     */
    private String jdbcDriver;

    /**
     * jdbc的url
     */
    private String jdbcUrl;

    /**
     * 数据库连接的账号
     */
    private String username;

    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 数据库schemaName，注意，每种数据库的schema意义不同
     */
    private String schemaName;

}
