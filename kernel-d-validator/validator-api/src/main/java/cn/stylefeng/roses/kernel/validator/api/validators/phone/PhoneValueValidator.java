package cn.stylefeng.roses.kernel.validator.api.validators.phone;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验手机号是否为11位
 *
 * @author fengshuonan
 * @date 2020/10/31 14:53
 */
public class PhoneValueValidator implements ConstraintValidator<PhoneValue, String> {

    private Boolean required;

    @Override
    public void initialize(PhoneValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String phoneValue, ConstraintValidatorContext context) {

        if (StrUtil.isEmpty(phoneValue)) {
            if (required) {
                return false;
            } else {
                return true;
            }
        } else {
            return phoneValue.length() == 11;
        }
    }
}
