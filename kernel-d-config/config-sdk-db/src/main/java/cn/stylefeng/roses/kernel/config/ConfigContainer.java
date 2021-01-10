package cn.stylefeng.roses.kernel.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigApi;
import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;
import cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;


/**
 * 系统配置表的实现类
 *
 * @author fengshuonan
 * @date 2020/10/17 14:56
 */
@Slf4j
public class ConfigContainer implements ConfigApi {

    /**
     * 系统配置的Map缓存
     */
    private static final Dict CONFIG_CONTAINER = Dict.create();

    @Override
    public void initConfig(Map<String, Object> configs) {
        if (configs == null || configs.size() == 0) {
            return;
        }
        CONFIG_CONTAINER.putAll(configs);
    }

    @Override
    public Map<String, Object> getAllConfigs() {
        return CONFIG_CONTAINER;
    }

    @Override
    public Set<String> getAllConfigKeys() {
        return CONFIG_CONTAINER.keySet();
    }

    @Override
    public void putConfig(String key, Object value) {
        CONFIG_CONTAINER.put(key, value);
    }

    @Override
    public void deleteConfig(String key) {
        CONFIG_CONTAINER.remove(key);
    }

    @Override
    public <T> T getConfigValue(String configCode, Class<T> clazz) throws ConfigException {
        String configValue = CONFIG_CONTAINER.getStr(configCode);
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

    @Override
    public <T> T getConfigValueNullable(String configCode, Class<T> clazz) {
        String configValue = CONFIG_CONTAINER.getStr(configCode);
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
