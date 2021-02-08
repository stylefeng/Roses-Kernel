package cn.stylefeng.roses.kernel.system.exception.enums.user;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统用户相关异常枚举
 *
 * @author luojie
 * @date 2020/11/6 10:09
 */
@Getter
public enum SysUserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "71", "{} 用户不存在"),

    /**
     * 账号已存在
     */
    USER_ACCOUNT_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "72", "账号已存在，请检查account参数"),

    /**
     * 原密码错误
     */
    USER_PWD_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "73", "原密码错误，请重新输入"),

    /**
     * 新密码与原密码相同
     */
    USER_PWD_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "74", "新密码与原密码相同，请更换新密码"),

    /**
     * 不能删除超级管理员
     */
    USER_CAN_NOT_DELETE_ADMIN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "75", "不能删除超级管理员"),

    /**
     * 不能修改超级管理员状态
     */
    USER_CAN_NOT_UPDATE_ADMIN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "76", "不能修改超级管理员状态"),

    /**
     * 请求状态值为空
     */
    REQUEST_USER_STATUS__EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "77", "请求状态值为空"),

    /**
     * 请求状值为非正确状态值
     */
    REQUEST_USER_STATUS_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "78", "请求状态值不合法，用户状态参数不合法，参数值：{}"),

    /**
     * 更新用户状态错误
     */
    UPDATE_USER_STATUS_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "79", "更新用户状态错误，更新生效数量0"),

    /**
     * 当前用户未分配菜单
     */
    USER_NOT_HAVE_MENUS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "710", "当前用户未分配菜单"),

    /**
     * 用户未绑定角色
     */
    USER_NOT_BIND_ROLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "711", "用户未绑定角色"),

    /**
     * 系统错误，账号存在多个
     */
    ACCOUNT_HAVE_MANY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "712", "系统错误，账号存在多个，账号为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysUserExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
