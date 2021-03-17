package cn.stylefeng.roses.kernel.system.api.pojo.login.details;

import lombok.Data;

/**
 * 用于登录返回详情
 *
 * @author fengshuonan
 * @date 2021/1/7 17:06
 */
@Data
public class SimpleUserDetail {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 职位
     */
    private String position;

}
