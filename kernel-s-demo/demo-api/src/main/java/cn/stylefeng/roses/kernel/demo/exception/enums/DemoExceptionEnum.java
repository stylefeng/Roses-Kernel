package cn.stylefeng.roses.kernel.demo.exception.enums;

import cn.stylefeng.roses.kernel.demo.constants.DemoConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 演示环境操作的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum DemoExceptionEnum implements AbstractExceptionEnum {

    /**
     * 演示环境无法操作
     */
    DEMO_OPERATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DemoConstants.DEMO_EXCEPTION_STEP_CODE + "01", "演示环境无法操作！");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DemoExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
