package cn.stylefeng.roses.kernel.menu.modular.pojo;

import lombok.Data;

import java.util.List;

/**
 * 菜单被哪个权限绑定的详情
 *
 * @author fengshuonan
 * @date 2021/1/7 18:16
 */
@Data
public class AuthorityItem {

    /**
     * 权限编码
     */
    private List<String> permission;

    /**
     * 角色编码
     */
    private List<String> role;

}
