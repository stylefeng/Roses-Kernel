package cn.stylefeng.roses.kernel.customer.modular.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.scanner.api.annotation.field.ChineseDescription;
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
    @NotNull(message = "主键id不能为空", groups = {edit.class, delete.class})
    @ChineseDescription("主键id")
    private Long customerId;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空", groups = {add.class, edit.class})
    @ChineseDescription("账号")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {add.class, edit.class})
    @ChineseDescription("密码")
    private String password;

    /**
     * 昵称（显示名称）
     */
    @NotBlank(message = "昵称（显示名称）不能为空", groups = {add.class, edit.class})
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
     * 用户头像的文件全名
     */
    @ChineseDescription("用户头像的文件全名")
    private String avatarObjectName;

    /**
     * 用户积分
     */
    @ChineseDescription("用户积分")
    private Integer score;

}