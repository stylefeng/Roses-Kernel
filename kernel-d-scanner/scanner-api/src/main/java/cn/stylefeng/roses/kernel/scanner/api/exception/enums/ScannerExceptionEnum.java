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
package cn.stylefeng.roses.kernel.scanner.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.scanner.api.constants.ScannerConstants;
import lombok.Getter;

/**
 * 资源相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/11/3 13:55
 */
@Getter
public enum ScannerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取资源为空
     */
    RESOURCE_DEFINITION_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ScannerConstants.RESOURCE_MODULE_NAME + "01", "获取资源为空，请检查当前请求url是否存在对应的ResourceDefinition"),

    /**
     * 扫描资源过程中，存在不合法控制器名称，请将控制名称以Controller结尾
     */
    ERROR_CONTROLLER_NAME(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ScannerConstants.RESOURCE_MODULE_NAME + "02", "扫描资源过程中，存在不合法控制器名称，请将控制名称以Controller结尾，控制器名称：{}"),

    /**
     * 系统资源尚未初始化完毕，无法使用系统
     */
    SYSTEM_RESOURCE_URL_NOT_INIT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ScannerConstants.RESOURCE_MODULE_NAME + "03", "系统资源尚未初始化完毕，请稍后使用系统");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ScannerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
