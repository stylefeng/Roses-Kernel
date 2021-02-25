package cn.stylefeng.roses.kernel.system.api.pojo.role.dto;

import lombok.Data;

/**
 * 角色按钮关联结果
 *
 * @author majianguo
 * @date 2021/1/9 17:33
 */
@Data
public class SysRoleMenuButtonDTO {

    /**
     * 主键
     */
    private Long roleButtonId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 按钮id
     */
    private Long buttonId;

    /**
     * 按钮编码
     */
    private String buttonCode;

}
