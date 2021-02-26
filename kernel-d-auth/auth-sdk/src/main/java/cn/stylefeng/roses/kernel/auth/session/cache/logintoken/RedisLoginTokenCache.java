package cn.stylefeng.roses.kernel.auth.session.cache.logintoken;

import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_USERID_PREFIX;


/**
 * 基于redis的token的缓存
 *
 * @author fengshuonan
 * @date 2020/12/24 19:16
 */
public class RedisLoginTokenCache extends AbstractRedisCacheOperator<Set<String>> {

    public RedisLoginTokenCache(RedisTemplate<String, Set<String>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_USERID_PREFIX;
    }

}
