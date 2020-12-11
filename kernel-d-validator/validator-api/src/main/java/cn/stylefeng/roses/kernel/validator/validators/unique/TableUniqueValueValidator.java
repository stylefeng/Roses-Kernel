package cn.stylefeng.roses.kernel.validator.validators.unique;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.unique.service.TableUniqueValueService;
import cn.stylefeng.roses.kernel.validator.context.RequestGroupContext;
import cn.stylefeng.roses.kernel.validator.context.RequestParamIdContext;
import cn.stylefeng.roses.kernel.validator.pojo.UniqueValidateParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证表的的某个字段值是否在是唯一值
 *
 * @author fengshuonan
 * @date 2020/11/4 14:39
 */
public class TableUniqueValueValidator implements ConstraintValidator<TableUniqueValue, String> {

    /**
     * 表名称，例如 sys_user
     */
    private String tableName;

    /**
     * 列名称，例如 user_code
     */
    private String columnName;

    /**
     * 是否开启逻辑删除校验，默认是关闭的
     * <p>
     * 关于为何开启逻辑删除校验：
     * <p>
     * 若项目中某个表包含控制逻辑删除的字段，我们在进行唯一值校验的时候要排除这种状态的记录，所以需要用到这个功能
     */
    private boolean excludeLogicDeleteItems;

    /**
     * 逻辑删除的字段名称
     */
    private String logicDeleteFieldName;

    /**
     * 默认逻辑删除的值（Y是已删除）
     */
    private String logicDeleteValue;

    @Override
    public void initialize(TableUniqueValue constraintAnnotation) {
        this.tableName = constraintAnnotation.tableName();
        this.columnName = constraintAnnotation.columnName();
        this.excludeLogicDeleteItems = constraintAnnotation.excludeLogicDeleteItems();
        this.logicDeleteFieldName = constraintAnnotation.logicDeleteFieldName();
        this.logicDeleteValue = constraintAnnotation.logicDeleteValue();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext context) {

        if (ObjectUtil.isNull(fieldValue)) {
            return true;
        }

        Class<?> validateGroupClass = RequestGroupContext.get();

        // 如果属于add group，则校验库中所有行
        if (BaseRequest.add.class.equals(validateGroupClass)) {
            UniqueValidateParam addParam = createAddParam(fieldValue);
            return TableUniqueValueService.getFiledUniqueFlag(addParam);
        }

        // 如果属于edit group，校验时需要排除当前修改的这条记录
        if (BaseRequest.edit.class.equals(validateGroupClass)) {
            UniqueValidateParam editParam = createEditParam(fieldValue);
            return TableUniqueValueService.getFiledUniqueFlag(editParam);
        }

        // 默认校验所有的行
        UniqueValidateParam addParam = createAddParam(fieldValue);
        return TableUniqueValueService.getFiledUniqueFlag(addParam);
    }

    /**
     * 创建校验新增的参数
     *
     * @author fengshuonan
     * @date 2020/8/17 21:55
     */
    private UniqueValidateParam createAddParam(String fieldValue) {
        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .excludeCurrentRecord(Boolean.FALSE)
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue).build();
    }

    /**
     * 创建修改的参数校验
     *
     * @author fengshuonan
     * @date 2020/8/17 21:56
     */
    private UniqueValidateParam createEditParam(String fieldValue) {
        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .excludeCurrentRecord(Boolean.TRUE)
                .id(RequestParamIdContext.get())
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue).build();
    }

}
