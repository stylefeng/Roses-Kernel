package cn.stylefeng.roses.kernel.security.api.constants;

/**
 * 安全模块常量
 *
 * @author fengshuonan
 * @date 2021/2/19 8:45
 */
public interface SecurityConstants {

    /**
     * 安全模块的名称
     */
    String SECURITY_MODULE_NAME = "kernel-d-security";

    /**
     * 异常枚举的步进值
     */
    String SECURITY_EXCEPTION_STEP_CODE = "28";

    /**
     * XSS默认拦截范围
     */
    String DEFAULT_XSS_PATTERN = "/*";

}
