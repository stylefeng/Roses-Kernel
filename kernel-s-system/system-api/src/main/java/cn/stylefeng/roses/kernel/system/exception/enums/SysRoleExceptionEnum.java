package cn.stylefeng.roses.kernel.system.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统角色相关异常枚举
 *
 * @author majianguo
 * @date 2020/11/5 上午11:06
 */
@Getter
public enum SysRoleExceptionEnum implements AbstractExceptionEnum {

    /**
     * 角色不存在
     */
    ROLE_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "71", "角色不存在"),

    /**
     * 角色编码重复
     */
    ROLE_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "72", "角色编码重复，请检查code参数"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "73", "角色名称重复，请检查name参数"),

    /**
     * 超级管理员不能被删除
     */
    SUPER_ADMIN_CANT_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "74", "超级管理员不能被删除"),

    /**
     * 超级管理员不能被删除
     */
    SYSTEM_ROLE_CANT_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "75", "系统角色不能被删除"),

    /**
     * 必须选择公司范围集合
     */
    PLEASE_FILL_DATA_SCOPE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "76", "指定部门类型的数据范围必须选择组织机构");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysRoleExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
