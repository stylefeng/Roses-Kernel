package cn.stylefeng.roses.kernel.system.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.Getter;

/**
 * 企业员工异常
 *
 * @author fengshuonan
 * @date 2020/11/19 23:07
 */
@Getter
public enum SysUserOrgExceptionEnum implements AbstractExceptionEnum {

    /**
     * 企业员工信息不存在
     */
    EMPLOYEE_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "21", "企业员工信息不存在,用户id：{}"),

    /**
     * 用户绑定多个或未绑定机构信息
     */
    EMPLOYEE_MANY_MAIN_NOT_FOUND(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "22", "用户绑定多个或未绑定机构信息"),

    /**
     * 用户未设置主部门，或主部门信息为多个
     */
    EMPLOYEE_NOT_OR_MANY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "23", "用户未设置主部门，或主部门信息为多个"),

    /**
     * 用户组织或部门不存在
     */
    USER_ORG_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "24", "用户组织或部门不存在：用户组织id：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysUserOrgExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
