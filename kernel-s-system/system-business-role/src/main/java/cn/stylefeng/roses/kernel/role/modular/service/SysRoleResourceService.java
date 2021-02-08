package cn.stylefeng.roses.kernel.role.modular.service;

import cn.stylefeng.roses.kernel.role.modular.entity.SysRoleResource;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色菜单service接口
 *
 * @author majianguo
 * @date 2020/11/5 上午11:17
 */
public interface SysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 授权资源
     *
     * @param sysRoleRequest 授权参数
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    void grantResource(SysRoleRequest sysRoleRequest);

    /**
     * 根据资源id集合删除角色关联的资源
     *
     * @param resourceIds 资源id集合
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    void deleteRoleResourceListByResourceIds(List<Long> resourceIds);

    /**
     * 根据角色id删除对应的角色资源信息
     *
     * @param roleId 角色id
     * @author majianguo
     * @date 2020/11/5 上午11:18
     */
    void deleteRoleResourceListByRoleId(Long roleId);

}
