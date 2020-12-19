package cn.stylefeng.roses.kernel.dict.api.exception.enums;

import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 字典模块相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/29 11:55
 */
@Getter
public enum DictExceptionEnum implements AbstractExceptionEnum {

    /**
     * 同类字典类型下，字典编码重复
     */
    DICT_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "01", "同类字典类型下，字典编码重复，字典类型：{}，字典编码：{}"),

    /**
     * 同类字典类型下，字典名称重复
     */
    DICT_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "02", "同类字典类型下，字典编码重复，字典类型：{}，字典名称：{}"),

    /**
     * 父级id不存在，输入的父级id不合理
     */
    PARENT_DICT_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "03", "父级字典id不存在，输入的父级id不合理，父级id：{}"),

    /**
     * 字典不存在
     */
    DICT_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "字典不存在，字典id：{}"),

    /**
     * 错误的字典状态
     */
    WRONG_DICT_STATUS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "05", "字典状态错误，字典状态：{}"),

    /**
     * 字典类型编码重复
     */
    DICT_TYPE_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "06", "字典类型编码重复，字典类型编码：{}"),

    /**
     * 字典类型不存在
     */
    DICT_TYPE_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "07", "字典类型不存在，字典类型id：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DictExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
