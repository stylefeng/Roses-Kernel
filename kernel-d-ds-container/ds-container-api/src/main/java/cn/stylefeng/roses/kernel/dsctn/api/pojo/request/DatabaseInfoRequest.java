package cn.stylefeng.roses.kernel.dsctn.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据库信息表
 *
 * @author fengshuonan
 * @date 2020/11/1 21:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseInfoRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "dbId不能为空", groups = {edit.class, delete.class, detail.class})
    private Long dbId;

    /**
     * 数据库名称（英文名称）
     */
    @NotBlank(message = "数据库名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "数据库名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_database_info",
            columnName = "db_name",
            idFieldName = "db_id",
            excludeLogicDeleteItems = true)
    private String dbName;

    /**
     * jdbc的驱动类型
     */
    @NotBlank(message = "jdbc的驱动类型为空", groups = {add.class, edit.class})
    private String jdbcDriver;

    /**
     * jdbc的url
     */
    @NotBlank(message = "jdbc的url", groups = {add.class, edit.class})
    private String jdbcUrl;

    /**
     * 数据库连接的账号
     */
    @NotBlank(message = "数据库连接的账号", groups = {add.class, edit.class})
    private String username;

    /**
     * 数据库连接密码
     */
    @NotBlank(message = "数据库连接密码", groups = {add.class, edit.class})
    private String password;

    /**
     * 备注，摘要
     */
    private String remarks;

}
