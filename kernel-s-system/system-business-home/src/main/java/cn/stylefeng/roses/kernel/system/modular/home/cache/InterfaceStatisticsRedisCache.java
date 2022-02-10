package cn.stylefeng.roses.kernel.system.modular.home.cache;

import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.system.api.constants.InterfaceStatisticsCacheConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 接口统计缓存
 *
 * @author xixiaowei
 * @date 2022/2/9 16:38
 */
public class InterfaceStatisticsRedisCache extends AbstractRedisCacheOperator<String> {

    public InterfaceStatisticsRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return InterfaceStatisticsCacheConstants.INTERFACE_STATISTICS_PREFIX;
    }
}
