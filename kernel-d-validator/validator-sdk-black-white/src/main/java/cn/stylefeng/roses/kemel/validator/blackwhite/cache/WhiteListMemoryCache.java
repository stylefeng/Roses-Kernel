package cn.stylefeng.roses.kemel.validator.blackwhite.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.AbstractMemoryCacheOperator;

import static cn.stylefeng.roses.kernel.validator.api.constants.ValidatorConstants.WHITE_LIST_CACHE_KEY_PREFIX;

/**
 * 白名单的缓存
 *
 * @author fengshuonan
 * @date 2020/11/15 15:26
 */
public class WhiteListMemoryCache extends AbstractMemoryCacheOperator<String> {

    public WhiteListMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return WHITE_LIST_CACHE_KEY_PREFIX;
    }

}
