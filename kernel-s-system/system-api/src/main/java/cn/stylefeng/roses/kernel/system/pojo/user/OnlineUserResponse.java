package cn.stylefeng.roses.kernel.system.pojo.user;

import lombok.Data;

import java.util.Date;


/**
 * 当前的在线用户的信息封装
 *
 * @author fengshuonan
 * @date 2021/1/11 22:30
 */
@Data
public class OnlineUserResponse {

    /**
     * 用户的token
     */
    private String token;

    /**
     * 主键
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 登录的时间
     */
    private Date loginTime;

}
