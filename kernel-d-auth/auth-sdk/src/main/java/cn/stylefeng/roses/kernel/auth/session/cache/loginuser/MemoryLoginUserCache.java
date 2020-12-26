package cn.stylefeng.roses.kernel.auth.session.cache.loginuser;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.cache.AbstractMemoryCacheOperator;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.LOGGED_TOKEN_PREFIX;

/**
 * 基于内存的登录用户缓存
 *
 * @author fengshuonan
 * @date 2020/12/24 19:16
 */
public class MemoryLoginUserCache extends AbstractMemoryCacheOperator<LoginUser> {

    public MemoryLoginUserCache(TimedCache<String, LoginUser> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGGED_TOKEN_PREFIX;
    }

}
