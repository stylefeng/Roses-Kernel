package cn.stylefeng.roses.kernel.db.api.constants;

/**
 * 数据库模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:05
 */
public interface DbConstants {

    /**
     * db模块的名称
     */
    String DB_MODULE_NAME = "kernel-d-db";

    /**
     * 异常枚举的步进值
     */
    String DB_EXCEPTION_STEP_CODE = "02";

    /**
     * druid默认servlet的映射url
     */
    String DEFAULT_DRUID_URL_MAPPINGS = "/druid/*";

    /**
     * druid控制台账号
     */
    String DEFAULT_DRUID_ADMIN_ACCOUNT = "admin";

    /**
     * druid控制台的监控数据默认不能清空
     */
    String DEFAULT_DRUID_ADMIN_RESET_ENABLE = "false";

    /**
     * druid web url统计的拦截范围
     */
    String DRUID_WEB_STAT_FILTER_URL_PATTERN = "/*";

    /**
     * druid web url统计的排除拦截表达式
     */
    String DRUID_WEB_STAT_FILTER_EXCLUSIONS = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

    /**
     * druid web url统计的session统计开关
     */
    String DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE = "false";

    /**
     * druid web url统计的session名称
     */
    String DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME = "";

    /**
     * druid web url统计的session最大监控数
     */
    String DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT = "1000";

    /**
     * druid web url统计的cookie名称
     */
    String DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME = "";

    /**
     * druid web url统计的 是否开启监控单个url调用的sql列表
     */
    String DRUID_WEB_STAT_FILTER_PROFILE_ENABLE = "true";

}
