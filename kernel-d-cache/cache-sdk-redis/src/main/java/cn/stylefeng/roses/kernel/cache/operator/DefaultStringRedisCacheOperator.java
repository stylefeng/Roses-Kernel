package cn.stylefeng.roses.kernel.cache.operator;

import cn.stylefeng.roses.kernel.cache.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认redis缓存的实现，value存放String类型
 *
 * @author fengshuonan
 * @date 2021/2/24 22:16
 */
public class DefaultStringRedisCacheOperator extends AbstractRedisCacheOperator<String> {

    public DefaultStringRedisCacheOperator(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
