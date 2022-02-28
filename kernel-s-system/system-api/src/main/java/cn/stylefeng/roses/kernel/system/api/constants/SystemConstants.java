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
 * 系统管理模块的常量
 *
 * @author fengshuonan
 * @date 2020/11/4 15:48
 */
public interface SystemConstants {

    /**
     * 系统管理模块的名称
     */
    String SYSTEM_MODULE_NAME = "kernel-s-system";

    /**
     * 异常枚举的步进值
     */
    String SYSTEM_EXCEPTION_STEP_CODE = "18";

    /**
     * 默认的系统版本号
     */
    String DEFAULT_SYSTEM_VERSION = "20210101";

    /**
     * 默认多租户的开关：关闭
     */
    Boolean DEFAULT_TENANT_OPEN = false;

    /**
     * 默认的系统的名称
     */
    String DEFAULT_SYSTEM_NAME = "Guns快速开发平台";

    /**
     * 超级管理员的角色编码
     */
    String SUPER_ADMIN_ROLE_CODE = "superAdmin";

    /**
     * 初始化超级管理员的监听器顺序
     */
    Integer SUPER_ADMIN_INIT_LISTENER_SORT = 400;

    /**
     * 主题编码相关的系统变量前缀
     */
    String THEME_CODE_SYSTEM_PREFIX = "GUNS";

    /**
     * 系统内置主题模板的编码
     */
    String THEME_GUNS_PLATFORM = "GUNS_PLATFORM";

}
