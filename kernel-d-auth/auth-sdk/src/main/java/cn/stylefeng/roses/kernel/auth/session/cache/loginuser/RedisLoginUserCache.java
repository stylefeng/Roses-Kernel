package cn.stylefeng.roses.kernel.auth.session.cache.loginuser;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;


/**
 * 基于redis的登录用户缓存
 *
 * @author fengshuonan
 * @date 2020/12/24 19:16
 */
public class RedisLoginUserCache extends AbstractRedisCacheOperator<LoginUser> {

    public RedisLoginUserCache(RedisTemplate<String, LoginUser> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_TOKEN_PREFIX;
    }

}
