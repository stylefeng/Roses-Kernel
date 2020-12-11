package cn.stylefeng.roses.kemel.blackwhite.cache;

import cn.stylefeng.roses.kernel.cache.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import static cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants.BLACK_LIST_CACHE_KEY_PREFIX;

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
        return BLACK_LIST_CACHE_KEY_PREFIX;
    }

}
