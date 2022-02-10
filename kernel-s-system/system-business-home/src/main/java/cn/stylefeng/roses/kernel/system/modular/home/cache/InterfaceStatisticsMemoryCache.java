package cn.stylefeng.roses.kernel.system.modular.home.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;
import cn.stylefeng.roses.kernel.system.api.constants.InterfaceStatisticsCacheConstants;

/**
 * 接口统计缓存
 *
 * @author xixiaowei
 * @date 2022/2/9 16:36
 */
public class InterfaceStatisticsMemoryCache extends AbstractMemoryCacheOperator<String> {

    public InterfaceStatisticsMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return InterfaceStatisticsCacheConstants.INTERFACE_STATISTICS_PREFIX;
    }
}
