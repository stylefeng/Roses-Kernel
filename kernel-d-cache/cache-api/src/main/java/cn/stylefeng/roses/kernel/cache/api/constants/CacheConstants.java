package cn.stylefeng.roses.kernel.cache.api.constants;

/**
 * 缓存模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/22 16:55
 */
public interface CacheConstants {

    /**
     * 缓存模块的名称
     */
    String CACHE_MODULE_NAME = "kernel-d-cache";

    /**
     * 缓存模块的异常步进值
     */
    String CACHE_EXCEPTION_STEP_CODE = "07";

    /**
     * 缓存的分割符号
     */
    String CACHE_DELIMITER = ":";

    /**
     * 给hutool缓存用的无限过期时间
     */
    Long NONE_EXPIRED_TIME = 1000L * 3600 * 24 * 999;

}
