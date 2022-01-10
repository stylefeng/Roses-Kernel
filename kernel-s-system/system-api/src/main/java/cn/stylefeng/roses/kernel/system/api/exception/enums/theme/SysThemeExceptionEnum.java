package cn.stylefeng.roses.kernel.system.api.exception.enums.theme;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统主题异常类
 *
 * @author xixiaowei
 * @date 2021/12/23 17:00
 */
@Getter
public enum SysThemeExceptionEnum implements AbstractExceptionEnum {

    /**
     * 系统主题不存在
     */
    THEME_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "121", "系统主题不存在"),

    /**
     * 已启用的系统主题不允许删除
     */
    THEME_NOT_ALLOW_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "122", "已启用的系统主题不允许删除"),

    /**
     * 禁用的系统主题模板不允许使用
     */
    THEME_TEMPLATE_IS_DISABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "123", "禁用的系统主题模板不允许使用"),

    /**
     * 唯一启用的系统主题不允许禁用
     */
    UNIQUE_ENABLE_NOT_DISABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "124", "唯一启用的系统主题不允许禁用"),

    /**
     * 编码前缀为GUNS的是系统内置属性，不能删除
     */
    THEME_IS_SYSTEM(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "125", "编码前缀为GUNS的是系统内置属性，不能删除");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysThemeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
