package cn.stylefeng.roses.kemel.blackwhite.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.AbstractMemoryCacheOperator;

import static cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants.BLACK_LIST_CACHE_KEY_PREFIX;

/**
 * 黑名单用户的缓存
 *
 * @author fengshuonan
 * @date 2020/11/20 15:50
 */
public class BlackListMemoryCache extends AbstractMemoryCacheOperator<String> {

    public BlackListMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return BLACK_LIST_CACHE_KEY_PREFIX;
    }

}
