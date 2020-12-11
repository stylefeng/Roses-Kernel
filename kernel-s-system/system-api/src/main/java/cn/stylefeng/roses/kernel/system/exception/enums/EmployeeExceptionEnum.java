package cn.stylefeng.roses.kernel.system.exception.enums;

import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.Getter;

/**
 * 企业员工异常
 *
 * @author fengshuonan
 * @date 2020/11/19 23:07
 */
@Getter
public enum EmployeeExceptionEnum implements AbstractExceptionEnum {

    /**
     * 企业员工信息不存在
     */
    EMPLOYEE_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "21", "企业员工信息不存在,用户id：{}"),

    /**
     * 用户存在多个主部门信息（系统不允许）
     */
    EMPLOYEE_MANY_MAIN_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "22", "获取用户主部门失败，用户存在多个主部门信息,用户id：{}"),

    /**
     * 用户未设置主部门，或主部门信息为多个
     */
    EMPLOYEE_NOT_OR_MANY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "23", "用户未设置主部门，或主部门信息为多个");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    EmployeeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
