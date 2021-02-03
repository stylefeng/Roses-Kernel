package cn.stylefeng.roses.kernel.system.pojo.userOrg;

import lombok.Data;

/**
 * 用户组织机构关联
 *
 * @author chenjinlong
 * @date 2021/2/3 10:51
 */
@Data
public class UserOrgResponse {

    /**
     * 主键
     */
    private Long userOrgId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 所属机构id
     */
    private Long orgId;

    /**
     * 职位id
     */
    private Long positionId;
}
