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
package cn.stylefeng.roses.kernel.system.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.system.api.constants.StatisticsCacheConstants;
import cn.stylefeng.roses.kernel.system.api.constants.SystemCachesConstants;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserOrgDTO;
import cn.stylefeng.roses.kernel.system.modular.role.cache.RoleDataScopeMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.role.cache.RoleMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.role.cache.RoleResourceMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import cn.stylefeng.roses.kernel.system.modular.home.cache.InterfaceStatisticsMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.theme.cache.ThemeMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.theme.pojo.DefaultTheme;
import cn.stylefeng.roses.kernel.system.modular.user.cache.SysUserMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.user.cache.UserOrgMemoryCache;
import cn.stylefeng.roses.kernel.system.modular.user.cache.UserRoleMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 系统管理缓存的自动配置（默认内存缓存）
 *
 * @author fengshuonan
 * @date 2021/2/28 10:29
 */
@Configuration
public class GunsSystemCacheAutoConfiguration {

    /**
     * 用户的缓存，非在线用户缓存，此缓存为了加快查看用户相关操作
     *
     * @author fengshuonan
     * @date 2021/2/28 10:30
     */
    @Bean
    @ConditionalOnMissingBean(name = "sysUserCacheOperatorApi")
    public CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi() {
        TimedCache<String, SysUserDTO> sysUserTimedCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new SysUserMemoryCache(sysUserTimedCache);
    }

    /**
     * 用户角色对应的缓存
     *
     * @author fengshuonan
     * @date 2021/7/29 23:00
     */
    @Bean
    @ConditionalOnMissingBean(name = "userRoleCacheApi")
    public CacheOperatorApi<List<Long>> userRoleCacheApi() {
        TimedCache<String, List<Long>> userRoleCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new UserRoleMemoryCache(userRoleCache);
    }

    /**
     * 角色信息对应的缓存
     *
     * @author fengshuonan
     * @date 2021/7/29 23:00
     */
    @Bean
    @ConditionalOnMissingBean(name = "roleInfoCacheApi")
    public CacheOperatorApi<SysRole> roleInfoCacheApi() {
        TimedCache<String, SysRole> roleCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new RoleMemoryCache(roleCache);
    }

    /**
     * 用户组织机构的缓存
     *
     * @author fengshuonan
     * @date 2021/7/30 23:09
     */
    @Bean
    @ConditionalOnMissingBean(name = "userOrgCacheApi")
    public CacheOperatorApi<SysUserOrgDTO> userOrgCacheApi() {
        TimedCache<String, SysUserOrgDTO> roleCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new UserOrgMemoryCache(roleCache);
    }

    /**
     * 用户资源绑定的缓存
     *
     * @author fengshuonan
     * @date 2021/7/30 23:29
     */
    @Bean
    @ConditionalOnMissingBean(name = "roleResourceCacheApi")
    public CacheOperatorApi<List<String>> roleResourceCacheApi() {
        TimedCache<String, List<String>> roleCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new RoleResourceMemoryCache(roleCache);
    }

    /**
     * 角色绑定的数据范围的缓存
     *
     * @author fengshuonan
     * @date 2021/7/31 17:59
     */
    @Bean
    @ConditionalOnMissingBean(name = "roleDataScopeCacheApi")
    public CacheOperatorApi<List<Long>> roleDataScopeCacheApi() {
        TimedCache<String, List<Long>> roleCache = CacheUtil.newTimedCache(SystemCachesConstants.USER_CACHE_TIMEOUT_SECONDS * 1000);
        return new RoleDataScopeMemoryCache(roleCache);
    }

    /**
     * 主题的缓存
     *
     * @author fengshuonan
     * @date 2021/7/31 17:59
     */
    @Bean
    @ConditionalOnMissingBean(name = "themeCacheApi")
    public CacheOperatorApi<DefaultTheme> themeCacheApi() {
        TimedCache<String, DefaultTheme> themeCache = CacheUtil.newTimedCache(Long.MAX_VALUE);
        return new ThemeMemoryCache(themeCache);
    }

    /**
     * 接口统计的缓存
     *
     * @author xixiaowei
     * @date 2022/2/9 16:53
     */
    @Bean
    @ConditionalOnMissingBean(name = "requestCountCacheApi")
    public CacheOperatorApi<Map<Long,Integer>> requestCountCacheApi() {
        TimedCache<String, Map<Long,Integer>> timedCache = CacheUtil.newTimedCache(StatisticsCacheConstants.INTERFACE_STATISTICS_CACHE_TIMEOUT_SECONDS);
        return new InterfaceStatisticsMemoryCache(timedCache);
    }
}
