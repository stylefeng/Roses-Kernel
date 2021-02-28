package cn.stylefeng.roses.kernel.system.modular.user.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.memory.AbstractMemoryCacheOperator;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;

/**
 * 用户的缓存
 *
 * @author fengshuonan
 * @date 2021/2/28 10:23
 */
public class SysUserMemoryCache extends AbstractMemoryCacheOperator<SysUserDTO> {

    public SysUserMemoryCache(TimedCache<String, SysUserDTO> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return SystemConstants.USER_CACHE_PREFIX;
    }

}
