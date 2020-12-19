package cn.stylefeng.roses.kernel.system.modular.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 系统用户角色表
 *
 * @author luojie
 * @date 2020/11/6 09:46
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {

    /**
     * 主键
     */
    @TableId(value = "user_role_id", type = IdType.ASSIGN_ID)
    private Long userRoleId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

}
