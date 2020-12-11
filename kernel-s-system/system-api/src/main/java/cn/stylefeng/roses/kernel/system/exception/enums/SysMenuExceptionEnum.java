package cn.stylefeng.roses.kernel.system.exception.enums;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;

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
    CANT_MOVE_APP(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "810", "本菜单无法修改应用，非一级菜单，不能改变所属应用"),

    /**
     * 菜单不存在
     */
    MENU_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "81", "菜单不存在"),

    /**
     * 菜单编码重复
     */
    MENU_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "82", "菜单编码重复，请检查code参数"),

    /**
     * 菜单名称重复
     */
    MENU_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "83", "菜单名称重复，请检查name参数"),

    /**
     * 路由地址为空
     */
    MENU_ROUTER_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "84", "路由地址为空，请检查router参数"),

    /**
     * 组件地址为空
     */
    MENU_COMPONENT_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "85", "组件地址为空，请检查component参数"),

    /**
     * 打开方式为空
     */
    MENU_OPEN_TYPE_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "86", "打开方式为空，请检查openType参数"),

    /**
     * 权限标识格式为空
     */
    MENU_PERMISSION_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "87", "权限标识为空，请检查permission参数"),

    /**
     * 权限标识格式错误
     */
    MENU_PERMISSION_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "88", "权限标识格式错误，请检查permission参数"),

    /**
     * 权限不存在
     */
    MENU_PERMISSION_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "89", "权限不存在，请检查permission参数"),

    /**
     * 父级菜单不能为当前节点，请从新选择父级菜单
     */
    PID_CANT_EQ_ID(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "811", "父级菜单不能为当前节点，请从新选择父级菜单");

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
