package cn.stylefeng.roses.kernel.customer.api.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息封装
 *
 * @author fengshuonan
 * @date 2021/6/8 21:23
 */
@Data
public class CustomerInfo {

    /**
     * 主键id
     */
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 账号
     */
    @ChineseDescription("帐号")
    private String account;

    /**
     * 昵称（显示名称）
     */
    @ChineseDescription("昵称（显示名称）")
    private String nickName;

    /**
     * 邮箱
     */
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 手机
     */
    @ChineseDescription("手机")
    private String telephone;

    /**
     * 用户头像（文件表id）
     */
    @ChineseDescription("用户头像（文件表id）")
    private Long avatar;

    /**
     * 用户头像的全部url
     */
    @ChineseDescription("用户头像的全部url")
    private String avatarObjectUrl;

    /**
     * 用户积分
     */
    @ChineseDescription("用户积分")
    private Integer score;

    /**
     * 用户秘钥
     */
    @ChineseDescription("用户秘钥")
    private String secretKey;

    /**
     * 是否是会员
     */
    @ChineseDescription("是否会员")
    private Boolean memberFlag;

    /**
     * 会员截止日期，到期时间
     */
    @ChineseDescription("会员截止日期，到期时间")
    private Date memberExpireTime;

    /**
     * 用户状态（1：启用，2：禁用，3：冻结）
     */
    @ChineseDescription("用户状态")
    private Integer statusFlag;

}
