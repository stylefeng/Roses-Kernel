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
package cn.stylefeng.roses.kernel.dsctn.api.exception.enums;

import cn.stylefeng.roses.kernel.dsctn.api.constants.DatasourceContainerConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 数据源容器异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/31 22:10
 */
@Getter
public enum DatasourceContainerExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询库中所有数据源信息错误
     */
    QUERY_DBS_DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "01", "查询库中所有数据源信息错误，具体错误为：{}"),

    /**
     * 插入数据源信息错误
     */
    INSERT_DBS_DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "02", "插入数据源信息错误，具体错误为：{}"),

    /**
     * 删除数据源信息错误
     */
    DELETE_DBS_DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "03", "删除数据源信息错误，具体错误为：{}"),

    /**
     * 根据数据库查询结果，创建DruidProperties失败
     */
    CREATE_PROP_DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "04", "根据数据库查询结果，创建DruidProperties失败，具体错误为：{}"),

    /**
     * 数据源连接信息存在空值，无法从初始化数据源容器
     */
    DB_CONNECTION_INFO_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "05", "数据源连接信息存在空值，无法从初始化数据源容器，url：{}，username:{}，pwd：***"),

    /**
     * 初始化数据源容器异常
     */
    INIT_DATASOURCE_CONTAINER_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "06", "初始化数据源容器异常，具体错误为：{}"),

    /**
     * 检验数据库连接失败
     */
    VALIDATE_DATASOURCE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "07", "检验数据库连接失败，请检查连接是否可用，url为：{}，异常为：{}"),

    /**
     * 添加数据源失败，当前环境已经存在同名数据源
     */
    DATASOURCE_NAME_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "08", "添加数据源失败，当前环境已经存在同名数据源，dbName为：{}"),

    /**
     * 初始化数据源异常
     */
    INIT_DATASOURCE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "09", "初始化数据源异常"),

    /**
     * 修改数据源失败，不能修改数据源名称
     */
    EDIT_DATASOURCE_NAME_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "10", "修改数据源失败，不能修改数据源名称，原名称为={}"),

    /**
     * 租户数据源不能删除
     */
    TENANT_DATASOURCE_CANT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "11", "租户数据源不能删除"),

    /**
     * 数据源信息不存在
     */
    DATASOURCE_INFO_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "12", "数据源信息不存在，数据源id为：{}"),

    /**
     * 主数据源不能删除
     */
    MASTER_DATASOURCE_CANT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DatasourceContainerConstants.DS_CTN_EXCEPTION_STEP_CODE + "13", "主数据源不能删除！");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DatasourceContainerExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
