package cn.stylefeng.roses.kernel.system.modular.role.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色资源关联
 *
 * @author fengshuonan
 * @date 2020/11/5 下午4:30
 */
@Data
@TableName("sys_role_resource")
public class SysRoleResource extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_resource_id", type = IdType.ASSIGN_ID)
    private Long roleResourceId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 资源编码
     */
    @TableField("resource_code")
    private String resourceCode;

}
