package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleMenuButtonDTO;
import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleMenuDTO;
import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleResourceDTO;

import java.util.List;
import java.util.Set;

/**
 * 角色服务对外部模块的接口
 *
 * @author fengshuonan
 * @date 2020/11/5 19:17
 */
public interface RoleServiceApi {

    /**
     * 获取角色，通过传递角色id列表
     *
     * @param roleIds 角色id列表
     * @return 角色信息列表
     * @author fengshuonan
     * @date 2020/11/21 9:17
     */
    List<SysRoleDTO> getRolesByIds(List<Long> roleIds);

    /**
     * 获取角色对应的组织机构范围集合
     *
     * @param roleIds 角色id集合
     * @return 组织机构id集合
     * @author fengshuonan
     * @date 2020/11/21 9:56
     */
    List<Long> getRoleDataScopes(List<Long> roleIds);

    /**
     * 获取某些角色对应的菜单id集合
     *
     * @param roleIds 角色id集合
     * @return 菜单id集合
     * @author fengshuonan
     * @date 2020/11/22 23:00
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取角色的资源code集合
     *
     * @param roleIdList 角色id集合
     * @return 资源code集合
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    List<String> getRoleResourceCodeList(List<Long> roleIdList);

    /**
     * 获取角色的资源code集合
     *
     * @param roleIdList 角色id集合
     * @return 资源code集合
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    List<SysRoleResourceDTO> getRoleResourceList(List<Long> roleIdList);

    /**
     * 获取角色对应的按钮编码集合
     *
     * @param roleIdList 角色id集合
     * @return 角色拥有的按钮编码集合
     * @author fengshuonan
     * @date 2021/1/9 11:08
     */
    Set<String> getRoleButtonCodes(List<Long> roleIdList);

    /**
     * 获取角色拥有的菜单
     *
     * @param roleIdList 角色集合
     * @author majianguo
     * @date 2021/1/9 17:33
     */
    List<SysRoleMenuDTO> getRoleMenuList(List<Long> roleIdList);

    /**
     * 获取角色拥有的菜单按钮
     *
     * @param roleIdList 角色集合
     * @author majianguo
     * @date 2021/1/9 17:33
     */
    List<SysRoleMenuButtonDTO> getRoleMenuButtonList(List<Long> roleIdList);

}
