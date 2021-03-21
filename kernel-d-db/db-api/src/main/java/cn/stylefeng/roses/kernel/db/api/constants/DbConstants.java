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
package cn.stylefeng.roses.kernel.db.api.constants;

/**
 * 数据库模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:05
 */
public interface DbConstants {

    /**
     * db模块的名称
     */
    String DB_MODULE_NAME = "kernel-d-db";

    /**
     * 异常枚举的步进值
     */
    String DB_EXCEPTION_STEP_CODE = "02";

    /**
     * druid默认servlet的映射url
     */
    String DEFAULT_DRUID_URL_MAPPINGS = "/druid/*";

    /**
     * druid控制台账号
     */
    String DEFAULT_DRUID_ADMIN_ACCOUNT = "admin";

    /**
     * druid控制台的监控数据默认不能清空
     */
    String DEFAULT_DRUID_ADMIN_RESET_ENABLE = "false";

    /**
     * druid web url统计的拦截范围
     */
    String DRUID_WEB_STAT_FILTER_URL_PATTERN = "/*";

    /**
     * druid web url统计的排除拦截表达式
     */
    String DRUID_WEB_STAT_FILTER_EXCLUSIONS = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

    /**
     * druid web url统计的session统计开关
     */
    String DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE = "false";

    /**
     * druid web url统计的session名称
     */
    String DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME = "";

    /**
     * druid web url统计的session最大监控数
     */
    String DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT = "1000";

    /**
     * druid web url统计的cookie名称
     */
    String DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME = "";

    /**
     * druid web url统计的 是否开启监控单个url调用的sql列表
     */
    String DRUID_WEB_STAT_FILTER_PROFILE_ENABLE = "true";

}
