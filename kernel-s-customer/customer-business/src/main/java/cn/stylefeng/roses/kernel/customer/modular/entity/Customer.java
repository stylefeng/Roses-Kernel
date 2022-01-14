package cn.stylefeng.roses.kernel.customer.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * C端用户表实例类
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@TableName("toc_customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "customer_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 账号
     */
    @TableField("account")
    @ChineseDescription("账号")
    private String account;

    /**
     * 密码，BCrypt
     */
    @TableField("password")
    @ChineseDescription("密码")
    private String password;

    /**
     * 旧网站密码（可选）
     */
    @TableField("old_password")
    @ChineseDescription("旧网站密码")
    private String oldPassword;

    /**
     * 旧网站密码盐（可选）
     */
    @TableField("old_password_salt")
    @ChineseDescription("旧网站密码盐")
    private String oldPasswordSalt;

    /**
     * 昵称（显示名称）
     */
    @TableField("nick_name")
    @ChineseDescription("昵称")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField("email")
    @ChineseDescription("邮箱")
    private String email;

    /**
     * 手机
     */
    @TableField("telephone")
    @ChineseDescription("手机")
    private String telephone;

    /**
     * 邮箱或手机验证码
     */
    @TableField("verify_code")
    @ChineseDescription("邮箱或手机验证码")
    private String verifyCode;

    /**
     * 是否已经邮箱或手机验证通过：Y-通过，N-未通过
     */
    @TableField("verified_flag")
    @ChineseDescription("是否已经邮箱或手机验证通过")
    private String verifiedFlag;

    /**
     * 用户头像（文件表id）
     */
    @TableField("avatar")
    @ChineseDescription("用户头像")
    private Long avatar;

    /**
     * 用户头像的文件全名
     */
    @TableField("avatar_object_name")
    @ChineseDescription("用户头像的文件全名")
    private String avatarObjectName;

    /**
     * 用户积分
     */
    @TableField("score")
    @ChineseDescription("用户积分")
    private Integer score;

    /**
     * 用户状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("用户状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 用户秘钥，用在调用会员校验等
     */
    @TableField("secret_key")
    @ChineseDescription("用户秘钥")
    private String secretKey;

    /**
     * 会员截止日期，到期时间
     */
    @TableField("member_expire_time")
    @ChineseDescription("会员截止日期，到期时间")
    private Date memberExpireTime;

    /**
     * 上次登录ip
     */
    @TableField("last_login_ip")
    @ChineseDescription("上次登录ip")
    private String lastLoginIp;

    /**
     * 上次登录时间
     */
    @TableField("last_login_time")
    @ChineseDescription("上次登录时间")
    private Date lastLoginTime;

}