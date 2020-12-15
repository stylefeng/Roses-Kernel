package cn.stylefeng.roses.kernel.rule.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请求失败的结果包装类
 *
 * @author fengshuonan
 * @date 2020/10/16 16:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseData extends ResponseData {

    /**
     * 异常的具体类名称
     */
    private String exceptionClazz;

    public ErrorResponseData(String code, String message) {
        super(Boolean.FALSE, code, message, null);
    }

    public ErrorResponseData(String code, String message, Object object) {
        super(Boolean.FALSE, code, message, object);
    }

}
