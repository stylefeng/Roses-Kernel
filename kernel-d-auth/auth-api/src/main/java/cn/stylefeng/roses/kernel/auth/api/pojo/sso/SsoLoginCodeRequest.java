package cn.stylefeng.roses.kernel.auth.api.pojo.sso;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 单点登录，获取ssoLoginCode的请求参数封装
 *
 * @author fengshuonan
 * @date 2021/1/27 16:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SsoLoginCodeRequest extends BaseRequest {

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
