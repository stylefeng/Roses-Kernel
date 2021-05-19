package cn.stylefeng.roses.kernel.db.api.pojo.db;

import lombok.Data;

/**
 * 数据库信息的封装
 *
 * @author fengshuonan
 * @date 2021/5/19 10:37
 */
@Data
public class DatabaseInfoDTO {

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

}
