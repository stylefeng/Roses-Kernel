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
package cn.stylefeng.roses.kernel.rule.exception.enums.defaults;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.FIRST_LEVEL_WIDE_CODE;
import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.THIRD_ERROR_TYPE_CODE;

/**
 * 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题
 *
 * @author fengshuonan
 * @date 2020/10/15 17:31
 */
@Getter
public enum DefaultThirdExceptionEnum implements AbstractExceptionEnum {

    /**
     * 调用第三方服务出错（一级宏观错误码）
     */
    THIRD_PARTY_ERROR(THIRD_ERROR_TYPE_CODE + FIRST_LEVEL_WIDE_CODE, "第三方调用出现错误");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DefaultThirdExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
