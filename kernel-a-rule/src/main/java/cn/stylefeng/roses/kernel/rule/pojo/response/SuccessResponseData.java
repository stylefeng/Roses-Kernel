package cn.stylefeng.roses.kernel.rule.pojo.response;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;

/**
 * 响应成功的封装类
 *
 * @author fengshuonan
 * @date 2020/10/16 16:23
 */
public class SuccessResponseData extends ResponseData {

    public SuccessResponseData() {
        super(Boolean.TRUE, RuleConstants.SUCCESS_CODE, RuleConstants.SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(Object object) {
        super(Boolean.TRUE, RuleConstants.SUCCESS_CODE, RuleConstants.SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(String code, String message, Object object) {
        super(Boolean.TRUE, code, message, object);
    }
}
