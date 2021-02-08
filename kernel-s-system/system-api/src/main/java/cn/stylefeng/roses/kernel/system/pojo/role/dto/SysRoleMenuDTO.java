package cn.stylefeng.roses.kernel.system.pojo.role.dto;

import lombok.Data;

/**
 * 角色菜单关联返回数据
 *
 * @author majianguo
 * @date 2021/1/9 18:07
 */
@Data
public class SysRoleMenuDTO {

    /**
     * 主键
     */
    private Long roleMenuId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}
