package cn.stylefeng.roses.kernel.system.api.exception.enums.title;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

@Getter
public enum SysTitleExceptionEnum implements AbstractExceptionEnum {

    /**
     * 标题图片配置不存在
     */
    TITLE_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "101", "标题图片配置不存在，id为：{}"),

    /**
     * 启用的配置不允许被删除
     */
    ENABLE_NOT_ALLOW_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "102", "启用的配置不允许被删除，id为：{}"),

    /**
     * 未启用的配置不允许被编辑
     */
    DISABLE_NOT_ALLOW_EDIT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "103", "未启用的配置不允许被编辑，id为：{}"),

    /**
     * 唯一启用的配置不允许被删除
     */
    UNIQUE_ENABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "104", "唯一启用的配置不允许禁用，id为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysTitleExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
