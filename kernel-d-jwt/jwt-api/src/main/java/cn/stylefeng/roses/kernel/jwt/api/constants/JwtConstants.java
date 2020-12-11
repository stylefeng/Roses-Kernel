package cn.stylefeng.roses.kernel.jwt.api.constants;

/**
 * jwt模块常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:05
 */
public interface JwtConstants {

    /**
     * jwt模块的名称
     */
    String JWT_MODULE_NAME = "kernel-d-jwt";

    /**
     * 异常枚举的步进值
     */
    String JWT_EXCEPTION_STEP_CODE = "06";

    /**
     * jwt默认失效时间 1天
     */
    Long DEFAULT_JWT_TIMEOUT_SECONDS = 3600L * 24;

}
