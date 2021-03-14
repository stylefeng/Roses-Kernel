package cn.stylefeng.roses.kemel.security.count.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;
import cn.stylefeng.roses.kernel.security.api.constants.CounterConstants;


/**
 * 计数用的缓存
 *
 * @author fengshuonan
 * @date 2020/11/15 15:26
 */
public class DefaultCountValidateCache extends AbstractMemoryCacheOperator<Long> {

    public DefaultCountValidateCache(TimedCache<String, Long> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX;
    }

}
