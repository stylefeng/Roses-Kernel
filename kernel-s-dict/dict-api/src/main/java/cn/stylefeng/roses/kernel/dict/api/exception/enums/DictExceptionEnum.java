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
package cn.stylefeng.roses.kernel.dict.api.exception.enums;

import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 字典模块相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/29 11:55
 */
@Getter
public enum DictExceptionEnum implements AbstractExceptionEnum {

    /**
     * 同类字典类型下，字典编码重复
     */
    DICT_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "01", "同类字典类型下，字典编码重复，字典类型：{}，字典编码：{}"),

    /**
     * 同类字典类型下，字典名称重复
     */
    DICT_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "02", "同类字典类型下，字典编码重复，字典类型：{}，字典名称：{}"),

    /**
     * 父级id不存在，输入的父级id不合理
     */
    PARENT_DICT_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "03", "父级id不存在，输入的父级id不合理，父级id：{}"),

    /**
     * 字典不存在
     */
    DICT_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "字典不存在，字典id：{}"),

    /**
     * 错误的字典状态
     */
    WRONG_DICT_STATUS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "05", "字典状态错误，字典状态：{}"),

    /**
     * 字典类型编码重复
     */
    DICT_TYPE_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "06", "字典类型编码重复，字典类型编码：{}"),

    /**
     * 系统字典不允许操作
     */
    SYSTEM_DICT_NOT_ALLOW_OPERATION(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "07", "系统字典不允许操作，如需操作请联系超级管理员！"),

    /**
     * 字典类型不存在
     */
    DICT_TYPE_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "08", "字典类型不存在，字典类型id：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DictExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
