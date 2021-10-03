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
package cn.stylefeng.roses.kernel.config.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigApi;
import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;
import cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 基于Redis的系统配置初始化
 *
 * @author fengshuonan
 * @date 2021/10/3 21:18
 */
@Slf4j
public class RedisConfigContainer implements ConfigApi {

    private JedisPool pool = null;

    private final String CONFIG_PREFIX = "SYS_CONFIG_CACHE:";

    public RedisConfigContainer(String redisHost, Integer redisPort, String redisPassword, Integer dbNumber) {

        // 设置默认值
        if (StrUtil.isEmpty(redisHost)) {
            redisHost = "localhost";
        }
        if (ObjectUtil.isEmpty(redisPort)) {
            redisPort = 6379;
        }
        if (StrUtil.isEmpty(redisPassword)) {
            redisPassword = null;
        }
        if (ObjectUtil.isEmpty(dbNumber)) {
            dbNumber = 0;
        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        // jedis实例数
        jedisPoolConfig.setMaxTotal(50);

        // 最小实例
        jedisPoolConfig.setMinIdle(5);

        // 最大等待时间，单位毫秒
        jedisPoolConfig.setMaxWaitMillis(1000 * 100);

        // 获取连接检测
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);

        pool = new JedisPool(jedisPoolConfig, redisHost, redisPort, 3000, redisPassword, dbNumber);
    }

    @Override
    public void initConfig(Map<String, Object> configs) {
        if (configs == null || configs.size() == 0) {
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            for (Map.Entry<String, Object> stringObjectEntry : configs.entrySet()) {
                String key = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                if (value != null) {
                    jedis.set(CONFIG_PREFIX + key, value.toString());
                }
            }
        }
    }

    @Override
    public Map<String, Object> getAllConfigs() {

        // 找到缓存所有配置
        try (Jedis jedis = pool.getResource()) {
            Set<String> keys = jedis.keys(CONFIG_PREFIX + "*");
            HashMap<String, Object> result = new HashMap<>();
            if (ObjectUtil.isNotEmpty(keys)) {
                for (String key : keys) {
                    result.put(key.substring(CONFIG_PREFIX.length()), jedis.get(key));
                }
            }
            return result;
        }
    }

    @Override
    public Set<String> getAllConfigKeys() {
        try (Jedis jedis = pool.getResource()) {
            Set<String> keys = jedis.keys(CONFIG_PREFIX + "*");
            Set<String> result = new HashSet<>();
            if (ObjectUtil.isNotEmpty(keys)) {
                for (String key : keys) {
                    result.add(key.substring(CONFIG_PREFIX.length()));
                }
            }
            return result;
        }
    }

    @Override
    public void putConfig(String key, Object value) {
        try (Jedis jedis = pool.getResource()) {
            if (ObjectUtil.isNotEmpty(value)) {
                jedis.set(CONFIG_PREFIX + key, value.toString());
            }
        }
    }

    @Override
    public void deleteConfig(String key) {
        try (Jedis jedis = pool.getResource()) {
            if (ObjectUtil.isNotEmpty(key)) {
                jedis.del(CONFIG_PREFIX + key);
            }
        }
    }

    @Override
    public <T> T getConfigValue(String configCode, Class<T> clazz) throws ConfigException {
        try (Jedis jedis = pool.getResource()) {
            String configValue = jedis.get(CONFIG_PREFIX + configCode);
            if (ObjectUtil.isEmpty(configValue)) {
                String format = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), configCode);
                log.warn(format);
                throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST.getErrorCode(), format);
            } else {
                try {
                    return Convert.convert(clazz, configValue);
                } catch (Exception e) {
                    String format = StrUtil.format(ConfigExceptionEnum.CONVERT_ERROR.getUserTip(), configCode, configValue, clazz.toString());
                    log.warn(format);
                    throw new ConfigException(ConfigExceptionEnum.CONVERT_ERROR.getErrorCode(), format);
                }
            }
        }
    }

    @Override
    public <T> T getConfigValueNullable(String configCode, Class<T> clazz) {
        try (Jedis jedis = pool.getResource()) {
            String configValue = jedis.get(CONFIG_PREFIX + configCode);
            if (ObjectUtil.isEmpty(configValue)) {
                String format = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), configCode);
                log.warn(format);
                return null;
            } else {
                try {
                    return Convert.convert(clazz, configValue);
                } catch (Exception e) {
                    String format = StrUtil.format(ConfigExceptionEnum.CONVERT_ERROR.getUserTip(), configCode, configValue, clazz.toString());
                    log.warn(format);
                    return null;
                }
            }
        }
    }

    @Override
    public <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        T value = this.getConfigValueNullable(configCode, clazz);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

}
