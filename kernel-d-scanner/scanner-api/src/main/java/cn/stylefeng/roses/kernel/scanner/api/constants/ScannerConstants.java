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
package cn.stylefeng.roses.kernel.scanner.api.constants;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * 资源扫描模块的常量
 *
 * @author fengshuonan
 * @date 2020/11/3 13:50
 */
public interface ScannerConstants {

    /**
     * 资源模块的名称
     */
    String RESOURCE_MODULE_NAME = "kernel-d-scanner";

    /**
     * 异常枚举的步进值
     */
    String RESOURCE_EXCEPTION_STEP_CODE = "17";

    /**
     * 资源前缀标识
     */
    String RESOURCE_CACHE_KEY = "GUNS_RESOURCE_CACHES";

    /**
     * 资源汇报的监听器的顺序
     */
    Integer REPORT_RESOURCE_LISTENER_SORT = 200;

    /**
     * 视图类型的控制器url路径开头
     */
    String VIEW_CONTROLLER_PATH_START_WITH = "/view";

    /**
     * FieldMetadata类全路径
     */
    String FIELD_METADATA_CLASS_ALL_PATH = "cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata";

    /**
     * DevOps平台资源汇报接口token超时时间
     */
    Long DEVOPS_REPORT_TIMEOUT_SECONDS = 5L;

    /**
     * DevOps平台资源汇报接口连接超时时间
     */
    Integer DEVOPS_REPORT_CONNECTION_TIMEOUT_SECONDS = 3;

    /**
     * DevOps平台资源汇报路径
     */
    String DEVOPS_REQUEST_PATH = "/scannerResource/addExternalResource";

    /**
     * 不需要解析的字段
     */
    List<String> DONT_PARSE_FIELD = ListUtil.list(false, "serialVersionUID", "delFlag", "createTime", "createUser", "updateTime", "updateUser");

    /**
     * 用在为validateGroups字段的标识，@Validate注解，不带class类时候的标识
     */
    String DEFAULT_VALIDATED = "default-all";
}
