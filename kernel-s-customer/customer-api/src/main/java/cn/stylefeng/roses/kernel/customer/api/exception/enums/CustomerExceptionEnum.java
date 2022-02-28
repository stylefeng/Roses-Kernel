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
package cn.stylefeng.roses.kernel.customer.api.exception.enums;

import cn.stylefeng.roses.kernel.customer.api.constants.CustomerConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * C端用户异常枚举
 *
 * @author fengshuonan
 * @date 2021/6/7 11:30
 */
@Getter
public enum CustomerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询不到对应用户
     */
    CANT_FIND_CUSTOMER(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "01", "查询不到对应用户，用户信息：{}"),

    /**
     * 用户状态异常
     */
    CUSTOMER_STATUS_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "02", "用户被禁用，请联系管理员！{}"),

    /**
     * 用户未激活
     */
    CUSTOMER_NOT_VERIFIED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "03", "用户未激活，请查阅注册邮箱中的激活邮件并点击链接！"),

    /**
     * 账号重复，请更换账号
     */
    ACCOUNT_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "04", "账号重复，请更换账号"),

    /**
     * 邮箱重复，请更换邮箱
     */
    EMAIL_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "05", "邮箱重复，请更换邮箱"),

    /**
     * 邮箱验证码错误，请重新输入邮箱验证码
     */
    EMAIL_VERIFY_COD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "06", "邮箱验证码错误，请重新输入邮箱验证码"),

    /**
     * 注册时，发送邮件失败，请联系管理员
     */
    EMAIL_SEND_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "07", "注册失败，网络异常！请联系管理员！"),

    /**
     * 激活用户失败
     */
    ACTIVE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "07", "激活用户失败！用户激活码无效！"),

    /**
     * 用户原密码错误
     */
    PWD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "08", "更改密码失败，原密码错误!"),

    /**
     * 尚未开通秘钥
     */
    NO_SECRET(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "09", "尚未开通秘钥"),

    /**
     * 秘钥已过期
     */
    SECRET_EXPIRED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + CustomerConstants.CUSTOMER_EXCEPTION_STEP_CODE + "10", "秘钥已过期");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    CustomerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
