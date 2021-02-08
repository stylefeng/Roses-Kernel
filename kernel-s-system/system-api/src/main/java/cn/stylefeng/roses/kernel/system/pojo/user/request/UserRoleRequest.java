package cn.stylefeng.roses.kernel.system.pojo.user.request;

import lombok.Data;

/**
 * 用户角色
 *
 * @author chenjinlong
 * @date 2021/2/3 14:53
 */
@Data
public class UserRoleRequest {

    /**
     * 主键
     */
    private Long userRoleId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

}
