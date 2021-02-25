package cn.stylefeng.roses.kernel.system.api.exception.enums.menu;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;

/**
 * 系统菜单相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/3/26 10:12
 */
public enum SysMenuExceptionEnum implements AbstractExceptionEnum {

    /**
     * 本菜单无法修改应用，非一级菜单，不能改变所属应用
     */
    CANT_MOVE_APP(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "32", "本菜单无法修改应用，非一级菜单，不能改变所属应用"),

    /**
     * 菜单不存在
     */
    MENU_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "33", "菜单不存在，菜单id：{}");

    private final String errorCode;

    private final String userTip;

    SysMenuExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getUserTip() {
        return userTip;
    }

}
