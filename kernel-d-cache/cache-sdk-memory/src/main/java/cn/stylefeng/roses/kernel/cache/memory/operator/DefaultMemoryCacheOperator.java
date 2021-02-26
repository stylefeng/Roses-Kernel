package cn.stylefeng.roses.kernel.cache.memory.operator;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;

/**
 * 默认内存缓存的实现，value存放Object类型
 *
 * @author fengshuonan
 * @date 2021/2/24 22:16
 */
public class DefaultMemoryCacheOperator extends AbstractMemoryCacheOperator<Object> {

    public DefaultMemoryCacheOperator(TimedCache<String, Object> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_OBJECT_CACHE_PREFIX;
    }

}
