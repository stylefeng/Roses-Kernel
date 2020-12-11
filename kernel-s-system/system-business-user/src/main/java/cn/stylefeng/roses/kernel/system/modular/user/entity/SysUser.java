package cn.stylefeng.roses.kernel.system.modular.user.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户表
 *
 * @author luojie
 * @date 2020/11/6 09:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    @ExcelProperty(value = {"账号"}, index = 0)
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @ExcelProperty(value = {"密码"}, index = 1)
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @ExcelProperty(value = {"昵称"}, index = 2)
    @TableField("nick_name")
    private String nickName;

    /**
     * 姓名
     */
    @ExcelProperty(value = {"姓名"}, index = 3)
    @TableField("name")
    private String name;

    /**
     * 头像
     */
    @ExcelProperty(value = {"头像"}, index = 4)
    @TableField("avatar")
    private Long avatar;

    /**
     * 生日
     */
    @ExcelProperty(value = {"生日"}, index = 5)
    @TableField("birthday")
    private Date birthday;

    /**
     * 性别(字典 M男 F女 )
     */
    @ExcelProperty(value = {"性别"}, index = 6)
    @TableField("sex")
    private String sex;

    /**
     * 邮箱
     */
    @ExcelProperty(value = {"邮箱"}, index = 7)
    @TableField("email")
    private String email;

    /**
     * 手机
     */
    @ExcelProperty(value = {"手机"}, index = 8)
    @TableField("phone")
    private String phone;

    /**
     * 电话
     */
    @ExcelProperty(value = {"电话"}, index = 9)
    @TableField("tel")
    private String tel;

    /**
     * 最后登陆IP
     */
    @ExcelProperty(value = {"最后登陆IP"}, index = 10)
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @ExcelProperty(value = {"最后登陆时间"}, index = 11)
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 是否是超级管理员，超级管理员可以拥有所有权限（Y-是，N-否）
     */
    @ExcelProperty(value = {"是否是超级管理员"}, index = 12)
    @TableField("super_admin_flag")
    private String superAdminFlag;

    /**
     * 状态（字典 1正常 2禁用 3冻结）
     */
    @ExcelProperty(value = {"状态"}, index = 13)
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 删除标记（Y-已删除，N-未删除）
     */
    @ExcelProperty(value = {"删除标记"}, index = 14)
    @TableField("del_flag")
    private String delFlag;

}
