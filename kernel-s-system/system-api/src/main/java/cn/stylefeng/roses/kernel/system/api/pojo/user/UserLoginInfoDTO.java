package cn.stylefeng.roses.kernel.system.api.pojo.user;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import lombok.Data;

/**
 * 用户登录需要用到的用户详细信息封装
 *
 * @author fengshuonan
 * @date 2020/10/21 9:15
 */
@Data
public class UserLoginInfoDTO {

    /**
     * 加密后的密码
     */
    private String userPasswordHexed;

    /**
     * 用户状态，状态在UserStatusEnum维护
     */
    private Integer userStatus;

    /**
     * 用户登录信息，用于保存当前登陆用户
     */
    private LoginUser loginUser;

}
