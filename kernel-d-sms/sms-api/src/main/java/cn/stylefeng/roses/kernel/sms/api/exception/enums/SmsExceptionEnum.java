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
package cn.stylefeng.roses.kernel.sms.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.sms.api.constants.SmsConstants;
import lombok.Getter;

/**
 * 短信发送的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 17:19
 */
@Getter
public enum SmsExceptionEnum implements AbstractExceptionEnum {

    /**
     * 阿里云短信发送异常
     */
    ALIYUN_SMS_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "01", "阿里云短信发送异常，错误码：{}，错误信息：{}"),

    /**
     * 阿里云短信发送accesskey错误
     */
    ALIYUN_SMS_KEY_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "02", "初始化sms客户端错误，accessKey错误，accessKeyId：{}"),

    /**
     * 短信发送请求参数为空
     */
    SEND_SMS_PARAM_NULL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "03", "短信发送请求参数为空，参数为：{}"),

    /**
     * 腾讯云短信发送异常
     */
    TENCENT_SMS_PARAM_NULL(RuleConstants.THIRD_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "04", "腾讯云短信发送异常，错误码：{}，错误信息：{}"),

    /**
     * 短信验证失败，库中找不到短信发送的记录
     */
    SMS_VALIDATE_ERROR_NOT_EXISTED_RECORD(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "05", "验证失败，库中没有该短信发送记录"),

    /**
     * 短信验证失败，验证码失效，可能这个短信验证码用过了
     */
    SMS_VALIDATE_ERROR_INVALIDATE_STATUS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "06", "验证失败，短信验证码失效，请检查是否被使用过"),

    /**
     * 短信验证失败，验证码错误
     */
    SMS_VALIDATE_ERROR_INVALIDATE_CODE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "07", "验证失败，短信验证码错误"),

    /**
     * 短信验证失败，验证码超时
     */
    SMS_VALIDATE_ERROR_INVALIDATE_TIME(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SmsConstants.SMS_EXCEPTION_STEP_CODE + "08", "验证失败，验证码超时");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SmsExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
