package cn.stylefeng.roses.kernel.customer.api.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息修改的请求bean
 *
 * @author fengshuonan
 * @date 2021/6/18 16:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerInfoRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "用户id不能为空", groups = {changeAvatar.class, changePassword.class})
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 用户头像（文件表id）
     */
    @NotNull(message = "用户头像", groups = changeAvatar.class)
    @ChineseDescription("用户头像（文件表id）")
    private Long avatar;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空", groups = changePassword.class)
    @ChineseDescription("原密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空", groups = changePassword.class)
    @ChineseDescription("新密码")
    private String newPassword;

    /**
     * 修改密码
     */
    public @interface changePassword {
    }

    /**
     * 修改头像
     */
    public @interface changeAvatar {
    }

}
