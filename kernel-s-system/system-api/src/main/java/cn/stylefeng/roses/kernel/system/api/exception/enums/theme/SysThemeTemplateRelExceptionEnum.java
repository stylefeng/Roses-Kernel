package cn.stylefeng.roses.kernel.system.api.exception.enums.theme;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

@Getter
public enum SysThemeTemplateRelExceptionEnum implements AbstractExceptionEnum {

    /**
     * 系统主题模板属性关系不存在
     */
    RELATION_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "131", "系统主题模板属性关系不存在");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysThemeTemplateRelExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
