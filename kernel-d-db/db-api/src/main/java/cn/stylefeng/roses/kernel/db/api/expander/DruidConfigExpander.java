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
package cn.stylefeng.roses.kernel.db.api.expander;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.db.api.constants.DbConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Druid数据源的一些配置
 *
 * @author fengshuonan
 * @date 2021/1/10 11:32
 */
@Slf4j
public class DruidConfigExpander {

    /**
     * Druid监控界面的url映射
     *
     * @author fengshuonan
     * @date 2021/1/10 11:32
     */
    public static String getDruidUrlMappings() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_URL_MAPPINGS", String.class, DbConstants.DEFAULT_DRUID_URL_MAPPINGS);
    }

    /**
     * Druid控制台账号
     *
     * @author fengshuonan
     * @date 2021/1/10 11:32
     */
    public static String getDruidAdminAccount() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_ACCOUNT", String.class, DbConstants.DEFAULT_DRUID_ADMIN_ACCOUNT);
    }

    /**
     * Druid控制台账号密码
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminPassword() {
        String sysDruidPassword = ConfigContext.me().getConfigValueNullable("SYS_DRUID_PASSWORD", String.class);

        // 没配置就返回一个随机密码
        if (sysDruidPassword == null) {
            String randomString = RandomUtil.randomString(20);
            log.info("Druid密码未在系统配置表设置，Druid密码为：{}", randomString);
            return randomString;
        } else {
            return sysDruidPassword;
        }
    }

    /**
     * Druid控制台的监控数据是否可以重置清零
     *
     * @return true-可以重置，false-不可以
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminResetFlag() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_RESET_ENABLE", String.class, DbConstants.DEFAULT_DRUID_ADMIN_RESET_ENABLE);
    }

    /**
     * druid web url统计的拦截范围
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterUrlPattern() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_URL_PATTERN", String.class, DbConstants.DRUID_WEB_STAT_FILTER_URL_PATTERN);
    }

    /**
     * druid web url统计的排除拦截表达式
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterExclusions() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_EXCLUSIONS", String.class, DbConstants.DRUID_WEB_STAT_FILTER_EXCLUSIONS);
    }

    /**
     * druid web url统计的session统计开关
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterSessionStatEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE", String.class, DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE);
    }

    /**
     * druid web url统计的session名称
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterSessionName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME);
    }

    /**
     * druid web url统计的session最大监控数
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterSessionStatMaxCount() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT", String.class, DbConstants.DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT);
    }

    /**
     * druid web url统计的cookie名称
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterPrincipalCookieName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME);
    }

    /**
     * druid web url统计的是否开启监控单个url调用的sql列表
     *
     * @author fengshuonan
     * @date 2021/1/10 11:34
     */
    public static String getDruidAdminWebStatFilterProfileEnable() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DRUID_WEB_STAT_FILTER_PROFILE_ENABLE", String.class, DbConstants.DRUID_WEB_STAT_FILTER_PROFILE_ENABLE);
    }

}
