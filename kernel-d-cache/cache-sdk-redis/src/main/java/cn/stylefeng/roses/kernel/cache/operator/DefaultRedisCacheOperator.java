package cn.stylefeng.roses.kernel.cache.operator;

import cn.stylefeng.roses.kernel.cache.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认redis缓存的实现，value存放Object类型
 *
 * @author fengshuonan
 * @date 2021/2/24 22:16
 */
public class DefaultRedisCacheOperator extends AbstractRedisCacheOperator<Object> {

    public DefaultRedisCacheOperator(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_OBJECT_CACHE_PREFIX;
    }

}
