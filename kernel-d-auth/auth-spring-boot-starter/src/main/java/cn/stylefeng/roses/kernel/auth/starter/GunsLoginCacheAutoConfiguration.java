package cn.stylefeng.roses.kernel.auth.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.auth.api.constants.LoginCacheConstants;
import cn.stylefeng.roses.kernel.auth.cache.LoginMemoryCache;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录缓存的自动配置
 *
 * @author xixiaowei
 * @date 2022/1/22 17:40
 */
@Configuration
public class GunsLoginCacheAutoConfiguration {

    /**
     * 登录帐号冻结的缓存
     *
     * @author xixiaowei
     * @date 2022/1/22 17:45
     */
    @Bean
    @ConditionalOnMissingBean(name = "loginCacheOperatorApi")
    public CacheOperatorApi<String> loginCacheOperatorApi() {
        TimedCache<String, String> loginTimeCache = CacheUtil.newTimedCache(LoginCacheConstants.LOGIN_CACHE_TIMEOUT_SECONDS * 1000);
        return new LoginMemoryCache(loginTimeCache);
    }
}
