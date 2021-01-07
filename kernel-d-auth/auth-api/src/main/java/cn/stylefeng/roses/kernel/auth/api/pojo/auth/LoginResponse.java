package cn.stylefeng.roses.kernel.auth.api.pojo.auth;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import lombok.Data;

/**
 * 登录操作的响应结果
 *
 * @author fengshuonan
 * @date 2020/10/19 14:17
 */
@Data
public class LoginResponse {

    /**
     * 登录人的信息
     */
    private LoginUser loginUser;

    /**
     * 登录人的token
     */
    private String token;

    /**
     * 到期时间
     */
    private Long expireAt;

    public LoginResponse(LoginUser loginUser, String token, Long expireAt) {
        this.loginUser = loginUser;
        this.token = token;
        this.expireAt = expireAt;
    }

}
