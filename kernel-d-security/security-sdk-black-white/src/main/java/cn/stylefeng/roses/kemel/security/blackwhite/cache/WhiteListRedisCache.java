package cn.stylefeng.roses.kemel.security.blackwhite.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;
import cn.stylefeng.roses.kernel.security.api.constants.CounterConstants;


/**
 * 白名单的缓存
 *
 * @author fengshuonan
 * @date 2020/11/15 15:26
 */
public class WhiteListRedisCache extends AbstractMemoryCacheOperator<Long> {

    public WhiteListRedisCache(TimedCache<String, Long> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.WHITE_LIST_CACHE_KEY_PREFIX;
    }

}
