package cn.stylefeng.roses.kernel.validator.validators.unique.service;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import cn.stylefeng.roses.kernel.db.api.context.DbOperatorContext;
import cn.stylefeng.roses.kernel.validator.exception.ParamValidateException;
import cn.stylefeng.roses.kernel.validator.pojo.UniqueValidateParam;

import static cn.stylefeng.roses.kernel.validator.exception.enums.ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR;

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

        DbOperatorApi dbOperatorApi = DbOperatorContext.me();

        int resultCount = 0;

        // 参数校验
        paramValidate(uniqueValidateParam);

        // 不排除当前记录，不排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {
            resultCount = dbOperatorApi.selectCount(
                    "select count(*) from " + uniqueValidateParam.getTableName() + " where " + uniqueValidateParam.getColumnName() + " = {0}",
                    uniqueValidateParam.getValue());
        }

        // 不排除当前记录，排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {
            resultCount = dbOperatorApi.selectCount(
                    "select count(*) from " + uniqueValidateParam.getTableName()
                            + " where " + uniqueValidateParam.getColumnName() + " = {0} "
                            + " and "
                            + "(" + uniqueValidateParam.getLogicDeleteFieldName() + " is null || "
                            + uniqueValidateParam.getLogicDeleteFieldName() + " <> " + uniqueValidateParam.getLogicDeleteValue() + ")",
                    uniqueValidateParam.getValue());
        }

        // 排除当前记录，不排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // id判空
            paramIdValidate(uniqueValidateParam);

            resultCount = dbOperatorApi.selectCount(
                    "select count(*) from " + uniqueValidateParam.getTableName()
                            + " where " + uniqueValidateParam.getColumnName() + " = {0} "
                            + " and id <> {1}",
                    uniqueValidateParam.getValue(), uniqueValidateParam.getId());
        }

        // 排除当前记录，排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // id判空
            paramIdValidate(uniqueValidateParam);

            resultCount = dbOperatorApi.selectCount(
                    "select count(*) from " + uniqueValidateParam.getTableName()
                            + " where " + uniqueValidateParam.getColumnName() + " = {0} "
                            + " and id <> {1} "
                            + " and "
                            + "(" + uniqueValidateParam.getLogicDeleteFieldName() + " is null || "
                            + uniqueValidateParam.getLogicDeleteFieldName() + " <> " + uniqueValidateParam.getLogicDeleteValue() + ")",
                    uniqueValidateParam.getValue(), uniqueValidateParam.getId());
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
            String userTip = StrUtil.format(TABLE_UNIQUE_VALIDATE_ERROR.getUserTip(), "tableName表名");
            throw new ParamValidateException(TABLE_UNIQUE_VALIDATE_ERROR, userTip);
        }
        if (StrUtil.isBlank(uniqueValidateParam.getColumnName())) {
            String userTip = StrUtil.format(TABLE_UNIQUE_VALIDATE_ERROR.getUserTip(), "columnName字段名");
            throw new ParamValidateException(TABLE_UNIQUE_VALIDATE_ERROR, userTip);
        }
        if (StrUtil.isBlank(uniqueValidateParam.getValue())) {
            String userTip = StrUtil.format(TABLE_UNIQUE_VALIDATE_ERROR.getUserTip(), "字段值");
            throw new ParamValidateException(TABLE_UNIQUE_VALIDATE_ERROR, userTip);
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
            String userTip = StrUtil.format(TABLE_UNIQUE_VALIDATE_ERROR.getUserTip(), "id为空");
            throw new ParamValidateException(TABLE_UNIQUE_VALIDATE_ERROR, userTip);
        }
    }

}
