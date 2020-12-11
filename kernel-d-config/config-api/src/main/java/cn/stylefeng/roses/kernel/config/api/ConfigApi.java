package cn.stylefeng.roses.kernel.config.api;

import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;

import java.util.Map;
import java.util.Set;

/**
 * 系统配置表相关的api
 * <p>
 * 系统配置表的实现可以用内存，数据库或redis
 *
 * @author fengshuonan
 * @date 2020/10/17 10:27
 */
public interface ConfigApi {

    /**
     * 初始化配置表中的所有配置
     *
     * @param configs 配置表的所有配置
     * @author fengshuonan
     * @date 2020/10/17 14:11
     */
    void initConfig(Map<String, Object> configs);

    /**
     * 获取配置表中所有配置
     *
     * @return 系统配置表中所有的配置
     * @author fengshuonan
     * @date 2020/10/17 14:07
     */
    Map<String, Object> getAllConfigs();

    /**
     * 获取所有配置的名称集合
     *
     * @return 所有配置的名称
     * @author fengshuonan
     * @date 2020/10/17 14:31
     */
    Set<String> getAllConfigKeys();

    /**
     * 往配置表中添加一个配置
     * <p>
     * 如果有某个配置，则会覆盖某个配置
     *
     * @param key   配置标识
     * @param value 配置具体值
     * @author fengshuonan
     * @date 2020/10/17 14:14
     */
    void putConfig(String key, Object value);

    /**
     * 删除一个配置项
     *
     * @param key 配置名称
     * @author fengshuonan
     * @date 2020/10/17 18:45
     */
    void deleteConfig(String key);

    /**
     * 获取config表中的配置，如果为空，抛出异常
     *
     * @param configCode 变量名称，对应sys_config表中的code
     * @param clazz      返回变量值的类型
     * @return 配置的值
     * @throws ConfigException 如果值为空抛出异常会
     * @author stylefeng
     * @date 2020/6/20 22:03
     */
    <T> T getConfigValue(String configCode, Class<T> clazz) throws ConfigException;

    /**
     * 获取config表中的配置，如果为空，返回null
     *
     * @param configCode 变量名称，对应sys_config表中的code
     * @param clazz      返回变量值的类型
     * @return 配置的值
     * @author stylefeng
     * @date 2020/6/20 22:03
     */
    <T> T getConfigValueNullable(String configCode, Class<T> clazz);

    /**
     * 获取config表中的配置，如果为空返回默认值
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param defaultValue 如果结果为空返回此默认值
     * @return 配置的值
     * @author stylefeng
     * @date 2020/6/20 22:03
     */
    <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue);

}
