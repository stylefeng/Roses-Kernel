package cn.stylefeng.roses.kernel.validator.validators.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_NAME;
import static cn.stylefeng.roses.kernel.validator.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_VALUE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证表的的某个字段值是否在是唯一值
 * <p>
 * 一般用来校验code字段，例如同一个表中，code字段不能重复
 *
 * @author fengshuonan
 * @date 2020/11/4 14:06
 */
@Documented
@Constraint(validatedBy = TableUniqueValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableUniqueValue {

    String message() default "库中存在重复编码，请更换该编码值";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 表名称，例如 sys_user
     */
    String tableName();

    /**
     * 列名称，例如 user_code
     */
    String columnName();

    /**
     * 数据库主键id的字段名，例如 user_id
     */
    String idFieldName() default "id";

    /**
     * 是否开启逻辑删除校验，默认是关闭的
     * <p>
     * 关于为何开启逻辑删除校验：
     * <p>
     * 若项目中某个表包含控制逻辑删除的字段，我们在进行唯一值校验的时候要排除这种状态的记录，所以需要用到这个功能
     */
    boolean excludeLogicDeleteItems() default false;

    /**
     * 逻辑删除的字段名称
     */
    String logicDeleteFieldName() default DEFAULT_LOGIC_DELETE_FIELD_NAME;

    /**
     * 默认逻辑删除的值（Y是已删除）
     */
    String logicDeleteValue() default DEFAULT_LOGIC_DELETE_FIELD_VALUE;

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TableUniqueValue[] value();
    }
}
