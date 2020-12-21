package cn.stylefeng.roses.kernel.system.modular.user.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.date.DateValue;
import cn.stylefeng.roses.kernel.validator.validators.status.StatusValue;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 系统用户参数
 *
 * @author luojie
 * @date 2020/11/6 15:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "userId不能为空", groups = {edit.class, delete.class, detail.class, start.class, stop.class, grantRole.class, grantData.class, updateInfo.class, updatePwd.class, resetPwd.class, changeStatus.class, updateAvatar.class})
    private Long userId;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空，请检查account参数", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "账号存在重复，请检查account参数",
            groups = {add.class, edit.class},
            tableName = "sys_user",
            columnName = "account",
            idFieldName = "user_id",
            excludeLogicDeleteItems = true)
    private String account;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空，请检查password参数", groups = {updatePwd.class})
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空，请检查newPassword参数", groups = {updatePwd.class})
    private String newPassword;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = {add.class, edit.class})
    private String realName;

    /**
     * 头像
     */
    @NotNull(message = "头像不能为空，请检查avatar参数", groups = {updateAvatar.class})
    private Long avatar;

    /**
     * 生日
     */
    @DateValue(message = "生日格式不正确，请检查birthday参数", groups = {add.class, edit.class, updateInfo.class})
    private String birthday;

    /**
     * 性别（M-男，F-女）
     */
    @NotNull(message = "性别不能为空，请检查sex参数", groups = {updateInfo.class})
    private String sex;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误，请检查email参数", groups = {updateInfo.class})
    private String email;

    /**
     * 手机
     */
    @NotNull(message = "手机号码不能为空，请检查phone参数", groups = {add.class, edit.class, updateInfo.class})
    @Size(min = 11, max = 11, message = "手机号码格式错误，请检查手机是否是11位", groups = {add.class, edit.class, updateInfo.class})
    private String phone;

    /**
     * 电话
     */
    private String tel;

    /**
     * 授权角色，角色id集合
     */
    @NotNull(message = "授权角色不能为空，请检查grantRoleIdList参数", groups = {grantRole.class})
    private List<Long> grantRoleIdList;

    /**
     * 授权数据范围，组织机构id集合
     */
    @NotNull(message = "授权数据不能为空，请检查grantOrgIdList参数", groups = {grantData.class})
    private List<Long> grantOrgIdList;

    /**
     * 用户所属机构
     */
    @NotNull(message = "用户所属机构不能为空", groups = {add.class, edit.class})
    private Long orgId;

    /**
     * 用户所属机构的职务
     */
    @NotNull(message = "用户职务不能为空", groups = {add.class, edit.class})
    private Long positionId;

    /**
     * 状态（字典 1正常 2冻结）
     */
    @NotNull(message = "状态不能为空，请检查statusFlag参数", groups = updateStatus.class)
    @StatusValue(message = "状态不正确，请检查状态值是否正确", groups = updateStatus.class)
    private Integer statusFlag;

    /**
     * 参数校验分组：修改密码
     */
    public @interface updatePwd {

    }

    /**
     * 参数校验分组：重置密码
     */
    public @interface resetPwd {

    }

    /**
     * 参数校验分组：修改头像
     */
    public @interface updateAvatar {

    }

    /**
     * 参数校验分组：停用
     */
    public @interface stop {

    }

    /**
     * 参数校验分组：启用
     */
    public @interface start {

    }

    /**
     * 参数校验分组：更新信息
     */
    public @interface updateInfo {

    }

    /**
     * 参数校验分组：授权角色
     */
    public @interface grantRole {

    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantData {

    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface changeStatus {

    }

}


