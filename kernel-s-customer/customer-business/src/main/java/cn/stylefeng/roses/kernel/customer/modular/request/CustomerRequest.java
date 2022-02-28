package cn.stylefeng.roses.kernel.customer.modular.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * C端用户表封装类
 *
 * @author fengshuonan
 * @date 2021/06/07 11:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    @NotBlank(message = "账号不能为空", groups = {add.class, edit.class, reg.class})
    private String account;

    /**
     * 密码，BCrypt
     */
    @ChineseDescription("密码")
    @NotBlank(message = "密码，BCrypt不能为空", groups = {add.class, edit.class, reg.class, resetPassword.class})
    private String password;

    /**
     * 昵称（显示名称）
     */
    @ChineseDescription("昵称")
    @NotBlank(message = "昵称（显示名称）不能为空", groups = {add.class, edit.class, reg.class})
    private String nickName;

    /**
     * 邮箱
     * <p>
     * 注册时，必填邮箱
     */
    @ChineseDescription("邮箱")
    @NotBlank(message = "邮箱不能为空", groups = {reg.class, sendResetPwdEmail.class, resetPassword.class})
    private String email;

    /**
     * 手机
     */
    @ChineseDescription("手机")
    private String telephone;

    /**
     * 邮箱或手机验证码
     */
    @ChineseDescription("邮箱或手机验证码")
    @NotBlank(message = "激活码不能为空", groups = {active.class, resetPassword.class})
    private String verifyCode;

    /**
     * 是否已经邮箱或手机验证通过：Y-通过，N-未通过
     */
    @ChineseDescription("是否已经邮箱或手机验证通过")
    private String verifiedFlag;

    /**
     * 用户头像（文件表id）
     */
    @ChineseDescription("用户头像")
    private Long avatar;

    /**
     * 用户头像的文件全名
     */
    @ChineseDescription("用户头像的文件全名")
    private String avatarObjectName;

    /**
     * 用户积分
     */
    @ChineseDescription("用户积分")
    private Integer score;

    /**
     * 用户状态：1-启用，2-禁用
     */
    @ChineseDescription("用户状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 用在图形验证码或者拖拽验证码
     */
    @ChineseDescription("用在图形验证码或者拖拽验证码")
    private String verKey;

    /**
     * 用在图形验证码或者拖拽验证码
     */
    @ChineseDescription("用在图形验证码或者拖拽验证码")
    private String verCode;

    /**
     * 注册账号
     */
    public @interface reg {
    }

    /**
     * 激活账号
     */
    public @interface active {
    }

    /**
     * 发送找回密码邮件
     */
    public @interface sendResetPwdEmail {
    }

    /**
     * 重置密码
     */
    public @interface resetPassword {
    }

}