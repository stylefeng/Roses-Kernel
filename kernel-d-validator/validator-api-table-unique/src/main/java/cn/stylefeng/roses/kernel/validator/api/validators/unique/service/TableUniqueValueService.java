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
package cn.stylefeng.roses.kernel.validator.api.validators.unique.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import cn.stylefeng.roses.kernel.db.api.context.DbOperatorContext;
import cn.stylefeng.roses.kernel.validator.api.exception.ParamValidateException;
import cn.stylefeng.roses.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import cn.stylefeng.roses.kernel.validator.api.pojo.UniqueValidateParam;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.constants.TenantConstants;

/**
 * 判断表中字段是否是唯一值的业务封装
 *
 * @author fengshuonan
 * @date 2020/11/4 15:06
 */
public class TableUniqueValueService {

    /**
     * 判断表中某个字段是否已经存在该值
     *
     * @author fengshuonan
     * @date 2020/11/4 15:08
     */
    public static boolean getFiledUniqueFlag(UniqueValidateParam uniqueValidateParam) {
        try {
            return doValidate(uniqueValidateParam);
        } catch (Exception exception) {
            throw new ParamValidateException(ValidatorExceptionEnum.UNIQUE_VALIDATE_SQL_ERROR, exception.getMessage());
        }
    }

    private static boolean doValidate(UniqueValidateParam uniqueValidateParam) {
        DbOperatorApi dbOperatorApi = DbOperatorContext.me();

        int resultCount = 0;

        // 参数校验
        paramValidate(uniqueValidateParam);

        // 不排除当前记录，不排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {
            String sqlTemplate = "select count(*) from {} where {} = {0}";
            String finalSql = StrUtil.format(sqlTemplate, calcTenantTableName(uniqueValidateParam.getTableName()), uniqueValidateParam.getColumnName());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue());
        }

        // 不排除当前记录，排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {
            String sqlTemplate = "select count(*) from {} where {} = {0}  and ({} is null or {} <> '{}')";
            String finalSql = StrUtil.format(sqlTemplate,
                    calcTenantTableName(uniqueValidateParam.getTableName()),
                    uniqueValidateParam.getColumnName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteValue());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue());
        }

        // 排除当前记录，不排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // id判空
            paramIdValidate(uniqueValidateParam);

            String sqlTemplate = "select count(*) from {} where {} = {0} and {} <> {1}";
            String finalSql = StrUtil.format(sqlTemplate, calcTenantTableName(uniqueValidateParam.getTableName()), uniqueValidateParam.getColumnName(), uniqueValidateParam.getIdFieldName());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue(), uniqueValidateParam.getId());
        }

        // 排除当前记录，排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // id判空
            paramIdValidate(uniqueValidateParam);

            String sqlTemplate = "select count(*) from {} where {} = {0} and {} <> {1} and ({} is null or {} <> '{}')";
            String finalSql = StrUtil.format(sqlTemplate,
                    calcTenantTableName(uniqueValidateParam.getTableName()),
                    uniqueValidateParam.getColumnName(),
                    uniqueValidateParam.getIdFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteValue());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue(), uniqueValidateParam.getId());
        }

        // 如果大于0，代表不是唯一的当前校验的值
        return resultCount <= 0;
    }


    /**
     * 几个参数的为空校验
     *
     * @author fengshuonan
     * @date 2020/11/4 15:11
     */
    private static void paramValidate(UniqueValidateParam uniqueValidateParam) {
        if (StrUtil.isBlank(uniqueValidateParam.getTableName())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue注解上tableName属性为空");
        }
        if (StrUtil.isBlank(uniqueValidateParam.getColumnName())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue注解上columnName属性为空");
        }
        if (ObjectUtil.isEmpty(uniqueValidateParam.getValue())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue被校验属性的值为空");
        }
    }

    /**
     * id参数的为空校验
     *
     * @author fengshuonan
     * @date 2020/11/4 15:16
     */
    private static void paramIdValidate(UniqueValidateParam uniqueValidateParam) {
        if (uniqueValidateParam.getId() == null) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, StrUtil.toCamelCase(uniqueValidateParam.getIdFieldName()) + "参数值为空");
        }
    }

    /**
     * 计算携带租户code情况下的表名称
     *
     * @author fengshuonan
     * @date 2021/9/24 17:01
     */
    private static String calcTenantTableName(String originTableName) {

        // 获取租户编码
        LoginUser loginUser = LoginContext.me().getLoginUser();
        String tenantCode = loginUser.getTenantCode();

        // 如果是主数据源可以忽略
        if (tenantCode == null || TenantConstants.MASTER_DATASOURCE_NAME.equals(tenantCode)) {
            return originTableName;
        }

        return TenantConstants.TENANT_DB_PREFIX + tenantCode + "." + originTableName;
    }

}
