package cn.stylefeng.roses.kernel.auth.api.pojo.auth;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 登录的请求参数
 *
 * @author fengshuonan
 * @date 2020/10/19 14:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequest extends BaseRequest {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 记住我，不传就是false
     */
    private Boolean rememberMe = false;

    /**
     * 验证码图形对应的缓存key
     */
    private String verKey;

    /**
     * 用户输入的验证码的值
     */
    private String verCode;

}
