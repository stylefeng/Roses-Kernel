package cn.stylefeng.roses.kernel.security.api;

import cn.stylefeng.roses.kernel.security.api.exception.CountValidateException;

/**
 * 计数and校验API
 * <p>
 * 检验一个key（用户或某个IP等）在一个时间段内的操作次数是否超标
 *
 * @author fengshuonan
 * @date 2020/11/14 17:18
 */
public interface CountValidatorApi {

    /**
     * 记录key的操作次数加1，并校验次数是否在当前时间窗内超标
     *
     * @param key                一个操作的唯一标识，例如API_COUNT_192.168.1.15
     * @param timeWindowSeconds  一个时间窗的范围，时间窗单位是秒
     * @param timeWindowMaxCount 一个时间窗内操作的次数限制
     * @throws CountValidateException 如果校验失败，会抛出本异常
     * @author fengshuonan
     * @date 2020/11/14 17:27
     */
    void countAndValidate(String key, Long timeWindowSeconds, Long timeWindowMaxCount) throws CountValidateException;

}
