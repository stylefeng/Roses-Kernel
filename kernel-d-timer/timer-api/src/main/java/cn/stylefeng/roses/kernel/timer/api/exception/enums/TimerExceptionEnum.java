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
package cn.stylefeng.roses.kernel.timer.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.timer.api.constants.TimerConstants;
import lombok.Getter;

/**
 * 定时任务的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/27 13:55
 */
@Getter
public enum TimerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 定时任务参数为空
     */
    PARAM_HAS_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "01", "定时任务参数为空，具体为空参数为：{}"),

    /**
     * 具体定时任务类找不到
     */
    CLASS_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "02", "定时任务类获取不到，请检查系统中是否有该类，具体类为：{}"),

    /**
     * 获取不到定时任务详情
     */
    JOB_DETAIL_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + TimerConstants.TIMER_EXCEPTION_STEP_CODE + "03", "定时任务详情获取不到，定时任务id为：{}");


    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    TimerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
