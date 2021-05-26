package cn.stylefeng.roses.kernel.system.api.exception.enums.resource;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * 接口字段信息异常相关枚举
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Getter
public enum ApiResourceFieldExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    APIRESOURCEFIELD_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "01", "查询结果不存在");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ApiResourceFieldExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}