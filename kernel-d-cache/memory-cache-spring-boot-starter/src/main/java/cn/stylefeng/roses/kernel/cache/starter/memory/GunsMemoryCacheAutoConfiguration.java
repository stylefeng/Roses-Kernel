package cn.stylefeng.roses.kernel.cache.starter.memory;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
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
     * 创建默认的value是string类型的缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public TimedCache<String, String> stringTimedCache() {
        return CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
    }

    /**
     * 创建默认的value是object类型的缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public TimedCache<String, Object> objectTimedCache() {
        return CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
    }

}
