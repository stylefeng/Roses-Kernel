package cn.stylefeng.roses.kernel.cache.starter.redis;

import cn.stylefeng.roses.kernel.cache.serializer.FastJson2JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 基于redis缓存的默认配置，默认提供两个RedisTemplate工具类，其他的各个模块自行配置
 *
 * @author fengshuonan
 * @date 2021/1/31 20:33
 */
@Configuration
public class GunsRedisCacheAutoConfiguration {

    /**
     * Redis的value序列化器
     *
     * @author fengshuonan
     * @date 2021/1/31 20:44
     */
    @Bean
    public RedisSerializer<?> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    /**
     * value是object类型的redis操作类
     *
     * @author fengshuonan
     * @date 2021/1/31 20:45
     */
    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * value是string类型的redis操作类
     *
     * @author fengshuonan
     * @date 2021/1/31 20:45
     */
    @Bean
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
