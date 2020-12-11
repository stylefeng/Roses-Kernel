package cn.stylefeng.roses.kernel.validator.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kemel.blackwhite.BlackListService;
import cn.stylefeng.roses.kemel.blackwhite.WhiteListService;
import cn.stylefeng.roses.kemel.blackwhite.cache.BlackListMemoryCache;
import cn.stylefeng.roses.kemel.blackwhite.cache.WhiteListMemoryCache;
import cn.stylefeng.roses.kemel.count.DefaultCountValidator;
import cn.stylefeng.roses.kemel.count.cache.DefaultCountValidateCache;
import cn.stylefeng.roses.kernel.validator.BlackListApi;
import cn.stylefeng.roses.kernel.validator.CountValidatorApi;
import cn.stylefeng.roses.kernel.validator.WhiteListApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 校验器自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 21:44
 */
@Configuration
public class GunsValidatorAutoConfiguration {

    /**
     * 黑名单校验
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(BlackListApi.class)
    public BlackListApi blackListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000L * 3600 * 24 * 999);
        BlackListMemoryCache blackListMemoryCache = new BlackListMemoryCache(timedCache);
        return new BlackListService(blackListMemoryCache);
    }

    /**
     * 计数校验器
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(CountValidatorApi.class)
    public CountValidatorApi countValidatorApi() {
        TimedCache<String, Long> timedCache = CacheUtil.newTimedCache(1000L * 3600 * 24 * 999);
        DefaultCountValidateCache defaultCountValidateCache = new DefaultCountValidateCache(timedCache);
        return new DefaultCountValidator(defaultCountValidateCache);
    }

    /**
     * 白名单校验
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(WhiteListApi.class)
    public WhiteListApi whiteListApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000L * 3600 * 24 * 999);
        WhiteListMemoryCache whiteListMemoryCache = new WhiteListMemoryCache(timedCache);
        return new WhiteListService(whiteListMemoryCache);
    }

}
