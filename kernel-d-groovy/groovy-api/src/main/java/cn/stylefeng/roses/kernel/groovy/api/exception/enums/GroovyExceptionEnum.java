package cn.stylefeng.roses.kernel.groovy.api.exception.enums;

import cn.stylefeng.roses.kernel.groovy.api.constants.GroovyConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * groovy脚本执行异常的枚举
 *
 * @author fengshuonan
 * @date 2021/1/22 16:36
 */
@Getter
public enum GroovyExceptionEnum implements AbstractExceptionEnum {

    /**
     * groovy脚本执行异常的枚举
     */
    GROOVY_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + GroovyConstants.GROOVY_EXCEPTION_STEP_CODE + "01", "执行groovy类中方法出错");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    GroovyExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
