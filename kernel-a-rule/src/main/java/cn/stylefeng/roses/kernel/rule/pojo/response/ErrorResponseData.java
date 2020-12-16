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

    /**
     * 异常的提示信息
     */
    private String exceptionTip;

    /**
     * 跟项目有关的具体异常位置
     * <p>
     * 一般是堆栈中第一个出现项目包名的地方
     */
    private String exceptionPlace;

    public ErrorResponseData(String code, String message) {
        super(Boolean.FALSE, code, message, null);
    }

    public ErrorResponseData(String code, String message, Object object) {
        super(Boolean.FALSE, code, message, object);
    }

}
