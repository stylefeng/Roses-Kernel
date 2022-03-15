package cn.stylefeng.roses.kernel.auth.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.constants.LoginCacheConstants;
import cn.stylefeng.roses.kernel.auth.cache.LoginErrorCountMemoryCache;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录错误次数的缓存
 *
 * @author fengshuonan
 * @date 2022/3/15 17:26
 */
@Configuration
public class GunsLoginCacheAutoConfiguration {

    /**
     * 登录错误次数的缓存
     *
     * @author fengshuonan
     * @date 2022/3/15 17:25
     */
    @Bean
    @ConditionalOnMissingBean(name = "loginErrorCountCacheApi")
    public CacheOperatorApi<Integer> loginErrorCountCacheApi() {
        TimedCache<String, Integer> loginTimeCache = CacheUtil.newTimedCache(LoginCacheConstants.LOGIN_CACHE_TIMEOUT_SECONDS * 1000);
        return new LoginErrorCountMemoryCache(loginTimeCache);
    }
}
