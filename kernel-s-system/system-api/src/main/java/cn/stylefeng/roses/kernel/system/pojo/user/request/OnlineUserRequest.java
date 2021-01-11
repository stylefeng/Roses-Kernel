package cn.stylefeng.roses.kernel.system.pojo.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 当前的在线用户的信息请求
 *
 * @author fengshuonan
 * @date 2021/1/11 22:30
 */
@Data
public class OnlineUserRequest {

    /**
     * 用户的token
     */
    @NotBlank(message = "参数token不能为空")
    private String token;

    /**
     * 用户账号
     */
    private String account;

}
