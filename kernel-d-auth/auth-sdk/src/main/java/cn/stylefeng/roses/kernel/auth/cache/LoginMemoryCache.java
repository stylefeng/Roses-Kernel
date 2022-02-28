package cn.stylefeng.roses.kernel.auth.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.constants.LoginCacheConstants;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;

/**
 * 用户帐号冻结的缓存
 *
 * @author xixiaowei
 * @date 2022/1/22 17:33
 */
public class LoginMemoryCache extends AbstractMemoryCacheOperator<String> {

    public LoginMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LoginCacheConstants.LOGIN_CACHE_PREFIX;
    }
}
