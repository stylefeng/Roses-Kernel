/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.cache.redis.starter;

import cn.stylefeng.roses.kernel.cache.redis.operator.DefaultRedisCacheOperator;
import cn.stylefeng.roses.kernel.cache.redis.operator.DefaultStringRedisCacheOperator;
import cn.stylefeng.roses.kernel.cache.redis.serializer.FastJson2JsonRedisSerializer;
import cn.stylefeng.roses.kernel.cache.redis.util.CreateRedisTemplateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

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
        return CreateRedisTemplateUtil.createObject(redisConnectionFactory);
    }

    /**
     * value是string类型的redis操作类
     *
     * @author fengshuonan
     * @date 2021/1/31 20:45
     */
    @Bean
    public RedisTemplate<String, String> gunsStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return CreateRedisTemplateUtil.createString(redisConnectionFactory);

    }

    /**
     * 创建默认的value是string类型的redis缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public DefaultStringRedisCacheOperator defaultStringRedisCacheOperator(RedisTemplate<String, String> stringRedisTemplate) {
        return new DefaultStringRedisCacheOperator(stringRedisTemplate);
    }

    /**
     * 创建默认的value是object类型的redis缓存
     *
     * @author fengshuonan
     * @date 2021/1/31 20:39
     */
    @Bean
    public DefaultRedisCacheOperator defaultRedisCacheOperator(RedisTemplate<String, Object> objectRedisTemplate) {
        return new DefaultRedisCacheOperator(objectRedisTemplate);
    }

}
