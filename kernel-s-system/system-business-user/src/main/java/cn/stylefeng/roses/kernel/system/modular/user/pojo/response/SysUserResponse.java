package cn.stylefeng.roses.kernel.system.modular.user.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeResponse;
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
public class SysUserResponse {

    /**
     * 主键
     */
    private Long id;

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
    private String name;

    /**
     * 头像
     */
    private Long avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别(字典 1男 2女 3未知)
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
     * 电话
     */
    private String tel;

    /**
     * 用户员工信息
     */
    private List<SysEmployeeResponse> sysEmployeeResponse;

    /**
     * 状态
     */
    private Integer statusFlag;
}
