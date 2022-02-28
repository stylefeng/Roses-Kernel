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
package cn.stylefeng.roses.kernel.system.api.constants;

/**
 * 缓存前缀相关的常量
 *
 * @author fengshuonan
 * @date 2021/7/29 22:55
 */
public interface SystemCachesConstants {

    /**
     * 用户缓存的前缀
     */
    String USER_CACHE_PREFIX = "user:";

    /**
     * 用户缓存过期时间(1小时)
     */
    Long USER_CACHE_TIMEOUT_SECONDS = 3600L;

    /**
     * 用户绑定的角色的缓存前缀
     */
    String USER_ROLES_CACHE_PREFIX = "user_roles:";

    /**
     * 角色信息的缓存
     */
    String ROLE_INFO_CACHE_PREFIX = "role:";

    /**
     * 用户组织机构缓存的前缀
     */
    String USER_ORG_CACHE_PREFIX = "user_org:";

    /**
     * 角色绑定资源的缓存
     */
    String ROLE_RESOURCE_CACHE_PREFIX = "role_resource:";

    /**
     * 角色绑定的数据范围的缓存
     */
    String ROLE_DATA_SCOPE_CACHE_PREFIX = "role_data_scope:";

    /**
     * 系统主题的缓存
     */
    String SYSTEM_THEME_CACHE_PREFIX = "system_cache:";

}
