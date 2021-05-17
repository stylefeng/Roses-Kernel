package cn.stylefeng.roses.kernel.cache.redis.util;

import cn.stylefeng.roses.kernel.cache.redis.serializer.FastJson2JsonRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate创建工具类
 *
 * @author fengshuonan
 * @date 2021/5/17 16:50
 */
public class CreateRedisTemplateUtil {

    /**
     * 穿件序列化器
     *
     * @author fengshuonan
     * @date 2021/5/17 16:48
     */
    public static RedisSerializer<?> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    /**
     * 创建value是object类型的redis操作类
     *
     * @author fengshuonan
     * @date 2021/5/17 16:49
     */
    public static <T> RedisTemplate<String, T> createObject(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建value是string类型的redis操作类
     *
     * @author fengshuonan
     * @date 2021/5/17 16:49
     */
    public static RedisTemplate<String, String> createString(RedisConnectionFactory redisConnectionFactory) {
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
