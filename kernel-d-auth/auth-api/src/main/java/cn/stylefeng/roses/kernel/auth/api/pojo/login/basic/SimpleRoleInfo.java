package cn.stylefeng.roses.kernel.auth.api.pojo.login.basic;

import lombok.Data;

/**
 * 用户基本信息
 *
 * @author fengshuonan
 * @date 2020/12/26 18:14
 */
@Data
public class SimpleRoleInfo {

    /**
     * 主键
     */
    private Long roleId;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 编码
     */
    private String roleCode;

}
