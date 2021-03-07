package cn.stylefeng.roses.kernel.system.api.pojo.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 系统用户结果
 *
 * @author fengshuonan
 * @date 2020/4/2 9:19
 */
@Data
public class SysUserDTO {

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
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别（M-男，F-女）
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String tel;

    /**
     * 用户所属机构
     */
    private Long orgId;

    /**
     * 用户所属机构的职务
     */
    private Long positionId;

    /**
     * 状态
     */
    private Integer statusFlag;

    /**
     * 用户角色id
     */
    private List<Long> grantRoleIdList;

}
