package cn.stylefeng.roses.kernel.dsctn.api.constants;

/**
 * 数据源容器的常量
 *
 * @author fengshuonan
 * @date 2020/10/31 21:58
 */
public interface DatasourceContainerConstants {

    /**
     * db模块的名称
     */
    String DS_CTN_MODULE_NAME = "kernel-d-ds-container";

    /**
     * 异常枚举的步进值
     */
    String DS_CTN_EXCEPTION_STEP_CODE = "16";

    /**
     * 主数据源名称
     */
    String MASTER_DATASOURCE_NAME = "master";

    /**
     * 多数据源切换的aop的顺序
     */
    int MULTI_DATA_SOURCE_EXCHANGE_AOP = 1;

    /**
     * 租户数据源标识前缀
     */
    String TENANT_DB_PREFIX = "sys_tenant_db_";

}
