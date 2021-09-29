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
package cn.stylefeng.roses.kernel.system.api.exception.enums.user;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统用户相关异常枚举
 *
 * @author luojie
 * @date 2020/11/6 10:09
 */
@Getter
public enum SysUserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "71", "{} 用户不存在"),

    /**
     * 账号已存在
     */
    USER_ACCOUNT_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "72", "账号已存在，请检查account参数"),

    /**
     * 原密码错误
     */
    USER_PWD_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "73", "原密码错误，请重新输入"),

    /**
     * 新密码与原密码相同
     */
    USER_PWD_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "74", "新密码与原密码相同，请更换新密码"),

    /**
     * 不能删除超级管理员
     */
    USER_CAN_NOT_DELETE_ADMIN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "75", "不能删除超级管理员"),

    /**
     * 不能修改超级管理员状态
     */
    USER_CAN_NOT_UPDATE_ADMIN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "76", "不能修改超级管理员状态"),

    /**
     * 请求状态值为空
     */
    REQUEST_USER_STATUS__EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "77", "请求状态值为空"),

    /**
     * 请求状值为非正确状态值
     */
    REQUEST_USER_STATUS_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "78", "请求状态值不合法，用户状态参数不合法，参数值：{}"),

    /**
     * 更新用户状态错误
     */
    UPDATE_USER_STATUS_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "79", "更新用户状态错误，更新生效数量0"),

    /**
     * 当前用户未分配菜单
     */
    USER_NOT_HAVE_MENUS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "710", "当前用户未分配菜单"),

    /**
     * 用户未绑定角色
     */
    USER_NOT_BIND_ROLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "711", "用户未绑定角色"),

    /**
     * 系统错误，账号存在多个
     */
    ACCOUNT_HAVE_MANY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "712", "系统错误，账号存在多个，账号为：{}"),

    /**
     * 请求参数token为空
     */
    TOKEN_EMPTY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "713", "请求参数token为空");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysUserExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
