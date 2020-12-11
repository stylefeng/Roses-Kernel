package cn.stylefeng.roses.kernel.rule.constants;

/**
 * 规则模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:25
 */
public interface RuleConstants {

    /**
     * 用户端操作异常的错误码分类编号
     */
    String USER_OPERATION_ERROR_TYPE_CODE = "A";

    /**
     * 业务执行异常的错误码分类编号
     */
    String BUSINESS_ERROR_TYPE_CODE = "B";

    /**
     * 第三方调用异常的错误码分类编号
     */
    String THIRD_ERROR_TYPE_CODE = "C";

    /**
     * 一级宏观码标识，宏观码标识代表一类错误码的统称
     */
    String FIRST_LEVEL_WIDE_CODE = "0001";

    /**
     * 请求成功的返回码
     */
    String SUCCESS_CODE = "00000";

    /**
     * 请求成功的返回信息
     */
    String SUCCESS_MESSAGE = "请求成功";

    /**
     * 规则模块的名称
     */
    String RULE_MODULE_NAME = "kernel-a-rule";

    /**
     * 异常枚举的步进值
     */
    String RULE_EXCEPTION_STEP_CODE = "01";


}
