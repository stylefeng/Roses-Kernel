package cn.stylefeng.roses.kernel.system.modular.home.cache;

import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.system.api.constants.StatisticsCacheConstants;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * 接口统计Redis缓存
 *
 * @author xixiaowei
 * @date 2022/2/9 16:38
 */
public class InterfaceStatisticsRedisCache extends AbstractRedisCacheOperator<Map<Long, Integer>> {

    public InterfaceStatisticsRedisCache(RedisTemplate<String, Map<Long, Integer>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return StatisticsCacheConstants.INTERFACE_STATISTICS_PREFIX;
    }
}
