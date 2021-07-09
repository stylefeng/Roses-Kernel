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
package cn.stylefeng.roses.kernel.config.api.exception.enums;

import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 系统配置表相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum ConfigExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据库操作未知异常
     */
    DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "01", "配置表信息操作异常"),

    /**
     * 系统配置表不存在该配置
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONFIG_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "02", "系统配置表不存在该配置，配置名称：{}，系统将使用默认配置"),

    /**
     * 系统配置表获取值时，强转类型异常
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONVERT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "03", "获取系统配置值时，强转类型异常，配置名称：{}，配置值：{}，转化类型：{}"),

    /**
     * 获取不到application.yml中的数据库配置
     */
    APP_DB_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "04", "获取不到application.yml中的数据库配置，无法从数据库加载系统配置表"),

    /**
     * 初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类
     */
    CLASS_NOT_FOUND_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "06", "初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类"),

    /**
     * 初始化系统配置表失败，执行查询语句失败
     */
    CONFIG_SQL_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "07", "初始化系统配置表失败，执行查询语句失败"),

    /**
     * 系统参数配置编码重复
     */
    CONFIG_CODE_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "08", "系统参数配置编码重复，请检查code参数"),

    /**
     * 删除失败，不能删除系统参数
     */
    CONFIG_SYS_CAN_NOT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "删除失败，不能删除系统参数"),

    /**
     * 配置容器是空，请先初始化配置容器
     */
    CONFIG_CONTAINER_IS_NULL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "10", "配置容器为空，请先初始化配置容器，请调用ConfigContext.setConfigApi()初始化"),

    /**
     * 初始化配置失败，参数为空
     */
    CONFIG_INIT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "11", "初始化配置失败，参数为空"),

    /**
     * 初始化配置失败，系统已经初始化
     */
    CONFIG_INIT_ALREADY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "12", "初始化配置失败，系统配置已经初始化");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ConfigExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
