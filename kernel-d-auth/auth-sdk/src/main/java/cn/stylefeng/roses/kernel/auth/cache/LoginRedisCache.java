package cn.stylefeng.roses.kernel.auth.cache;

import cn.stylefeng.roses.kernel.auth.api.constants.LoginCacheConstants;
import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用户帐号冻结的缓存
 *
 * @author xixiaowei
 * @date 2022/1/23 23:34
 */
public class LoginRedisCache extends AbstractRedisCacheOperator<String> {

    public LoginRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LoginCacheConstants.LOGIN_CACHE_PREFIX;
    }
}
