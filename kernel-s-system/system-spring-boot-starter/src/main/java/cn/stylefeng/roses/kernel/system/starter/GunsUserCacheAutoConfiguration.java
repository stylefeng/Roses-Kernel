package cn.stylefeng.roses.kernel.system.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.modular.user.cache.SysUserMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户缓存的自动配置
 *
 * @author fengshuonan
 * @date 2021/2/28 10:29
 */
@Configuration
public class GunsUserCacheAutoConfiguration {

    /**
     * 用户的缓存
     *
     * @author fengshuonan
     * @date 2021/2/28 10:30
     */
    @Bean
    @ConditionalOnMissingBean(name = "sysUserCacheOperatorApi")
    public CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi() {
        TimedCache<String, SysUserDTO> sysUserTimedCache = CacheUtil.newTimedCache(SystemConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new SysUserMemoryCache(sysUserTimedCache);
    }

}
