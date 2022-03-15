package cn.stylefeng.roses.kernel.auth.cache;

import cn.stylefeng.roses.kernel.auth.api.constants.LoginCacheConstants;
import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 记录用户登录失败次数的缓存
 * <p>
 * key是用户账号，value是登录失败错误次数
 *
 * @author fengshuonan
 * @date 2022/3/15 17:06
 */
public class LoginErrorCountRedisCache extends AbstractRedisCacheOperator<Integer> {

    public LoginErrorCountRedisCache(RedisTemplate<String, Integer> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LoginCacheConstants.LOGIN_ERROR_CACHE_PREFIX;
    }
}
