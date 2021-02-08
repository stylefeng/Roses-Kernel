package cn.stylefeng.roses.kernel.system.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import lombok.Getter;

/**
 * 系统管理模块相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/29 11:55
 */
@Getter
public enum OrganizationExceptionEnum implements AbstractExceptionEnum {

    /**
     * 添加组织架构失败，一级节点只有超级管理员可以添加
     */
    ADD_ONE_LEVEL_ORGANIZATION_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "41", "添加组织架构失败，一级节点只有超级管理员可以添加"),

    /**
     * 添加组织架构失败，数据权限不够
     */
    ADD_ORG_ERROR_DATA_SCOPE_NOT_ENOUGH(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "42", "添加组织架构失败，数据权限不够"),

    /**
     * 查询不到组织机构，错误的组织机构ID
     */
    CANT_FIND_ORG(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "43", "查询不到组织机构，错误的组织机构ID：{}"),

    /**
     * 删除机构失败，该机构下有绑定员工
     */
    DELETE_ORGANIZATION_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "44", "删除机构失败，该机构下有绑定员工");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    OrganizationExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
