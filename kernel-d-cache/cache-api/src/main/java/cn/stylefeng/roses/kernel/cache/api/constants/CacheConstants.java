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
package cn.stylefeng.roses.kernel.cache.api.constants;

/**
 * 缓存模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/22 16:55
 */
public interface CacheConstants {

    /**
     * 缓存模块的名称
     */
    String CACHE_MODULE_NAME = "kernel-d-cache";

    /**
     * 缓存模块的异常步进值
     */
    String CACHE_EXCEPTION_STEP_CODE = "07";

    /**
     * 缓存的分割符号
     */
    String CACHE_DELIMITER = ":";

    /**
     * 给hutool缓存用的无限过期时间
     */
    Long NONE_EXPIRED_TIME = 1000L * 3600 * 24 * 999;

    /**
     * 默认缓存的过期时间，10分钟
     */
    Long DEFAULT_CACHE_TIMEOUT = 1000L * 60 * 10;

    /**
     * 默认object对象缓存的缓存前缀
     */
    String DEFAULT_OBJECT_CACHE_PREFIX = "DEFAULT:OBJECTS:";

    /**
     * 默认String对象缓存的缓存前缀
     */
    String DEFAULT_STRING_CACHE_PREFIX = "DEFAULT:STRINGS:";

}
