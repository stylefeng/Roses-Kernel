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
package cn.stylefeng.roses.kernel.log.api.exception.enums;

import cn.stylefeng.roses.kernel.log.api.constants.LogConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 日志异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/27 16:18
 */
@Getter
public enum LogExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询或者删除日志时，传入的参数中没有app名称
     */
    APP_NAME_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "01", "应用名称不能为空！"),

    /**
     * 查询或者删除日志时，传入的参数中没有查询时间
     */
    BEGIN_DATETIME_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "02", "开始时间不能为空,请填写精确到日的时间！"),

    /**
     * 查询或者删除日志时，传入的参数中没有查询时间
     */
    END_DATETIME_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "03", "结束时间不能为空,请填写精确到日的时间！"),

    /**
     * 初始化日志记录表失败，执行查询语句失败
     */
    LOG_SQL_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "04", "初始化日志记录表失败，执行查询语句失败"),

    /**
     * 被查询日志不存在
     */
    LOG_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + LogConstants.LOG_EXCEPTION_STEP_CODE + "05", "被查询日志不存在，日志id：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    LogExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
