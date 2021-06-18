package cn.stylefeng.roses.kernel.system.api.pojo.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 校验token是否正确的请求参数
 *
 * @author fengshuonan
 * @date 2021/6/18 15:29
 */
@Data
public class ValidateTokenRequest {

    /**
     * 用户的登录token
     */
    @NotBlank(message = "token不能为空")
    private String token;

}
