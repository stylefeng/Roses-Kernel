package cn.stylefeng.roses.kernel.rule.pojo.response;

import lombok.Data;

/**
 * http响应结果封装
 *
 * @author fengshuonan
 * @date 2020/10/17 17:33
 */
@Data
public class ResponseData {

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, String code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
