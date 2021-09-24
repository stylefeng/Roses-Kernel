package cn.stylefeng.roses.kernel.validator.api.validators.unique.constants;

/**
 * 租户数据常量，如果租户业务变了这里也需要修改
 *
 * @author fengshuonan
 * @date 2021/9/24 17:20
 */
public interface TenantConstants {

    /**
     * 租户库的前缀
     */
    String TENANT_DB_PREFIX = "sys_tenant_db_";

    /**
     * master数据源名称
     */
    String MASTER_DATASOURCE_NAME = "master";

}
