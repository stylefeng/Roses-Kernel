package cn.stylefeng.roses.kernel.system.pojo.role.request;

import lombok.Data;

/**
 * 角色数据范围
 *
 * @author chenjinlong
 * @date 2021/2/4 15:17
 */
@Data
public class SysRoleDataScopeRequest {

    /**
     * 主键
     */
    private Long roleDataScopeId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 机构id
     */
    private Long organizationId;
}
