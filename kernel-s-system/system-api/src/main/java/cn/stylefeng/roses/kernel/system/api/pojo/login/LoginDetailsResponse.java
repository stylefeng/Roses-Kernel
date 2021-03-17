package cn.stylefeng.roses.kernel.system.api.pojo.login;

import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleAuthDetail;
import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleUserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登陆信息详情相应结果
 *
 * @author majianguo
 * @date 2021/1/7 11:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetailsResponse {

    /**
     * 登录人的token
     */
    private String token;

    /**
     * 到期时间
     */
    private Long expireAt;

    /**
     * 用户基本信息
     */
    private SimpleUserDetail user;

    /**
     * 权限信息
     */
    private List<SimpleAuthDetail> permissions;

    /**
     * 角色信息
     */
    private List<SimpleAuthDetail> roles;

    /**
     * 登录人的ws-url
     */
    private String wsUrl;

}
