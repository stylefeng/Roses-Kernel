package cn.stylefeng.roses.kernel.validator.validators.status;

import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验状态，判断是否为 StatusEnum 中的值
 *
 * @author fengshuonan
 * @date 2020/10/31 14:52
 */
public class StatusValueValidator implements ConstraintValidator<StatusValue, Integer> {

    private Boolean required;

    @Override
    public void initialize(StatusValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer statusValue, ConstraintValidatorContext context) {

        // 如果是必填的
        if (required && statusValue == null) {
            return false;
        }

        // 如果不是必填，为空的话就通过
        if (!required && statusValue == null) {
            return true;
        }

        // 校验值是否是枚举中的值
        StatusEnum statusEnum = StatusEnum.codeToEnum(statusValue);
        return statusEnum != null;

    }
}
