package cn.stylefeng.roses.kernel.validator.api.constants;

/**
 * 校验器模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/31 14:24
 */
public interface ValidatorConstants {

    /**
     * 校验器模块的名称
     */
    String VALIDATOR_MODULE_NAME = "kernel-d-validator";

    /**
     * 异常枚举的步进值
     */
    String VALIDATOR_EXCEPTION_STEP_CODE = "15";

    /**
     * 默认逻辑删除字段的字段名
     */
    String DEFAULT_LOGIC_DELETE_FIELD_NAME = "del_flag";

    /**
     * 默认逻辑删除字段的值
     */
    String DEFAULT_LOGIC_DELETE_FIELD_VALUE = "Y";

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
