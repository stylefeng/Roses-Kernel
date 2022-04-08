package cn.stylefeng.roses.kernel.system.api.pojo.login.v3;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

/**
 * 角色信息
 *
 * @author fengshuonan
 * @date 2022/4/8 15:06
 */
@Data
public class IndexRoleInfo {

    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long roleId;

    /**
     * 角色名称
     */
    @ChineseDescription("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @ChineseDescription("角色编码")
    private String roleCode;

}
