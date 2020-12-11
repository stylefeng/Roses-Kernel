package cn.stylefeng.roses.kernel.validator.utils;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.validator.exception.ParamValidateException;
import cn.stylefeng.roses.kernel.validator.exception.enums.ValidatorExceptionEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * 参数校验器，静态方法调用
 * <p>
 * 手动验证带有校验注解的参数是否合法
 *
 * @author fengshuonan
 * @date 2020/10/31 14:13
 */
public class ValidatorUtil {

    /**
     * 验证器实例
     */
    private static final Validator VALIDATOR_INSTANCE = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验参数是否合法，返回校验的结果
     *
     * @param object 被校验的包装类参数
     * @param groups 校验组
     * @return 参数校验的结果，为ConstraintViolation的集合
     * @author fengshuonan
     * @date 2020/10/31 14:13
     */
    public static Set<ConstraintViolation<Object>> validate(Object object, Class<?>... groups) {
        return VALIDATOR_INSTANCE.validate(object, groups);
    }

    /**
     * 校验参数是否合法，直接返回成功和失败
     *
     * @param object 被校验的包装类参数
     * @param groups 校验组
     * @return true-参数合法，false-参数不合法
     * @author fengshuonan
     * @date 2020/10/31 14:13
     */
    public static boolean simpleValidate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR_INSTANCE.validate(object, groups);
        return constraintViolations.isEmpty();
    }

    /**
     * 校验参数是否合法，不返回结果，有问题直接抛出异常
     *
     * @param object 被校验的包装类参数
     * @param groups 校验组
     * @author fengshuonan
     * @date 2020/10/31 14:13
     */
    public static void validateThrowMessage(Object object, Class<?>... groups) {
        String errorMessage = validateGetMessage(object, groups);
        if (errorMessage != null) {
            throw new ParamValidateException(ValidatorExceptionEnum.PARAM_VALIDATE_ERROR, errorMessage);
        }
    }

    /**
     * 校验参数是否合法
     * <p>
     * 不合法会返回不合法的提示，合法的话会返回null
     *
     * @param object 被校验的包装类参数
     * @param groups 校验组
     * @author fengshuonan
     * @date 2020/10/31 14:13
     */
    public static String validateGetMessage(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR_INSTANCE.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (Iterator<ConstraintViolation<Object>> it = constraintViolations.iterator(); it.hasNext(); ) {
                ConstraintViolation<Object> violation = it.next();
                errorMessage.append(violation.getMessage());
                if (it.hasNext()) {
                    errorMessage.append(", ");
                }
            }
            return StrUtil.format(ValidatorExceptionEnum.PARAM_VALIDATE_ERROR.getUserTip(), errorMessage);
        }
        return null;
    }

}