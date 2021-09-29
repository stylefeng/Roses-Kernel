package cn.stylefeng.roses.kernel.auth.api.loginuser.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取登录用户信息的请求
 *
 * @author fengshuonan
 * @date 2021/9/29 11:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    /**
     * 当前登录用户的token
     */
    private String token;

}
