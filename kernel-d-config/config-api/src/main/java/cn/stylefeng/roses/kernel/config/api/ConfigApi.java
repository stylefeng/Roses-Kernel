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
