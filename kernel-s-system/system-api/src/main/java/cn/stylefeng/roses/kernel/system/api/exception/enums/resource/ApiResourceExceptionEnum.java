package cn.stylefeng.roses.kernel.system.api.exception.enums.resource;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 接口信息异常相关枚举
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Getter
public enum ApiResourceExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    APIRESOURCE_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "01", "查询结果不存在"),

    /**
     * 不允许对资源节点进行操作
     */
    OPERATIONS_RESOURCE_NODESNOT_ALLOWED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "02", "不允许对资源节点进行操作"),

    /**
     * 不允许添加视图资源
     */
    ADDING_VIEW_RESOURCES_NOT_ALLOWED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "03", "不允许添加视图资源"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ApiResourceExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}