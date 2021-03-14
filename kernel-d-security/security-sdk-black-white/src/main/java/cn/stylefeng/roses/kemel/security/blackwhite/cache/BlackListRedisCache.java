package cn.stylefeng.roses.kemel.security.blackwhite.cache;

import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.security.api.constants.CounterConstants;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 黑名单用户的缓存
 *
 * @author fengshuonan
 * @date 2020/11/20 15:50
 */
public class BlackListRedisCache extends AbstractRedisCacheOperator<String> {

    public BlackListRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CounterConstants.BLACK_LIST_CACHE_KEY_PREFIX;
    }

}
