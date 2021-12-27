package cn.stylefeng.roses.kernel.system.api.exception.enums.theme;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统主题模板异常类
 *
 * @author xixiaowei
 * @date 2021/12/23 17:01
 */
@Getter
public enum SysThemeTemplateExceptionEnum implements AbstractExceptionEnum {

    /**
     * 系统主题模板不存在
     */
    TEMPLATE_NOT_EXIT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "111", "系统主题模板不存在"),

    /**
     * 已启用的系统主题模板不允许删除
     */
    TEMPLATE_IS_ENABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "112", "已启用的系统主题模板不允许删除"),

    /**
     * 系统主题模板正在被使用，不允许操作
     */
    TEMPLATE_IS_USED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "113", "系统主题模板正在被使用，不允许操作"),

    /**
     * 系统主题模板没有属性，不允许启用
     */
    TEMPLATE_NOT_ATTRIBUTE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "114", "系统主题模板没有属性，不允许启用");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysThemeTemplateExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
