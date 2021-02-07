package cn.stylefeng.roses.kernel.monitor.api.exception.enums;

import cn.stylefeng.roses.kernel.monitor.api.constants.MonitorConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 监控模块异常枚举
 *
 * @author fengshuonan
 * @date 2021/1/31 22:35
 */
@Getter
public enum MonitorExceptionEnum implements AbstractExceptionEnum {

    /**
     * 演示环境无法操作
     */
    DEMO_OPERATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MonitorConstants.MONITOR_EXCEPTION_STEP_CODE + "01", "演示环境无法操作！");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    MonitorExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
