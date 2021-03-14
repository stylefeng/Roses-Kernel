package cn.stylefeng.roses.kernel.security.api.constants;

/**
 * 黑白名单和计数器的常量
 *
 * @author fengshuonan
 * @date 2021/3/14 17:29
 */
public interface CounterConstants {

    /**
     * 计数校验用的 缓存前缀标识
     */
    String COUNT_VALIDATE_CACHE_KEY_PREFIX = "COUNT_VALIDATE";

    /**
     * 黑名单 缓存前缀标识
     */
    String BLACK_LIST_CACHE_KEY_PREFIX = "BLACK_LIST";

    /**
     * 白名单 缓存前缀标识
     */
    String WHITE_LIST_CACHE_KEY_PREFIX = "WHITE_LIST";

    /**
     * 计数校验用的 计数当时的秒数
     */
    String RECORD_TIME_SECONDS = "RECORD_TIME_SECONDS";

    /**
     * 计数校验用的 时间窗内的次数
     */
    String COUNT_NUMBER = "COUNT_NUMBER";

}
