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
package cn.stylefeng.roses.kernel.system.api.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;

import static cn.stylefeng.roses.kernel.auth.api.constants.AuthConstants.DEFAULT_PASSWORD;

/**
 * 系统的一些基础信息
 *
 * @author fengshuonan
 * @date 2020/12/27 17:13
 */
public class SystemConfigExpander {

    /**
     * 获取系统发布的版本号（防止css和js的缓存）
     *
     * @author fengshuonan
     * @date 2020/12/27 17:14
     */
    public static String getReleaseVersion() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_RELEASE_VERSION", String.class, SystemConstants.DEFAULT_SYSTEM_VERSION);
    }

    /**
     * 获取租户是否开启的标识，默认是关的
     *
     * @author fengshuonan
     * @date 2020/12/27 17:21
     */
    public static Boolean getTenantOpen() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_TENANT_OPEN", Boolean.class, SystemConstants.DEFAULT_TENANT_OPEN);
    }

    /**
     * 获取系统名称
     *
     * @author fengshuonan
     * @date 2020/12/27 17:22
     */
    public static String getSystemName() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SYSTEM_NAME", String.class, SystemConstants.DEFAULT_SYSTEM_NAME);
    }

    /**
     * 获取默认密码
     *
     * @author luojie
     * @date 2020/11/6 10:05
     */
    public static String getDefaultPassWord() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_PASSWORD", String.class, DEFAULT_PASSWORD);
    }
    
    /**
     * 获取开发开关的状态
     *
     * @return {@link Boolean}
     * @author majianguo
     * @date 2022/1/17 14:59
     **/
    public static Boolean getDevSwitchStatus() {
        return ConfigContext.me().getSysConfigValueWithDefault("DEVOPS_DEV_SWITCH_STATUS", Boolean.class, Boolean.FALSE);
    }
}
