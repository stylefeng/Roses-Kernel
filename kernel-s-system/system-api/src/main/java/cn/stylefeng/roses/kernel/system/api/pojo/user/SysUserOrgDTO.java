package cn.stylefeng.roses.kernel.system.api.pojo.user;

import lombok.Data;

/**
 * 企业员工表，用户-组织机构的关联
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Data
public class SysUserOrgDTO {

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
