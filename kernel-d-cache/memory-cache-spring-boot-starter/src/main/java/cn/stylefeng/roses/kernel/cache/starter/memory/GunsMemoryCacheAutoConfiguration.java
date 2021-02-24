package cn.stylefeng.roses.kernel.cache.starter.memory;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import cn.stylefeng.roses.kernel.cache.operator.DefaultMemoryCacheOperator;
import cn.stylefeng.roses.kernel.cache.operator.DefaultStringMemoryCacheOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于内存缓存的默认配置
 *
 * @author fengshuonan
 * @date 2021/1/31 20:32
 */
@Configuration
public class GunsMemoryCacheAutoConfiguration {

    /**
     * 创建默认的value是string类型的内存缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public DefaultStringMemoryCacheOperator defaultStringMemoryCacheOperator() {
        TimedCache<String, String> stringTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultStringMemoryCacheOperator(stringTimedCache);
    }

    /**
     * 创建默认的value是object类型的内存缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public DefaultMemoryCacheOperator defaultMemoryCacheOperator() {
        TimedCache<String, Object> objectTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultMemoryCacheOperator(objectTimedCache);
    }

}
