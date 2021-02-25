package cn.stylefeng.roses.kernel.system.modular.role.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色按钮关联
 *
 * @author fengshuonan
 * @date 2021/01/09 11:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu_button")
public class SysRoleMenuButton extends BaseEntity {

    /**
     * 主键
     */
    @TableId("role_button_id")
    private Long roleButtonId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 按钮id
     */
    @TableField("button_id")
    private Long buttonId;

    /**
     * 按钮编码
     */
    @TableField("button_code")
    private String buttonCode;

}
