package cn.stylefeng.roses.kernel.system.modular.user.cache;

import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用户的缓存
 *
 * @author fengshuonan
 * @date 2021/2/28 10:23
 */
public class SysUserRedisCache extends AbstractRedisCacheOperator<SysUserDTO> {

    public SysUserRedisCache(RedisTemplate<String, SysUserDTO> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return SystemConstants.USER_CACHE_PREFIX;
    }

}
