package cn.stylefeng.roses.kernel.role.modular.service;

import cn.stylefeng.roses.kernel.role.modular.entity.SysRoleDataScope;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;

import java.util.List;
import java.util.Set;

/**
 * 系统角色数据范围service接口
 *
 * @author majianguo
 * @date 2020/11/5 上午11:21
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 授权数据
     *
     * @param sysRoleRequest 授权参数
     * @author majianguo
     * @date 2020/11/5 上午11:20
     */
    void grantDataScope(SysRoleRequest sysRoleRequest);

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author majianguo
     * @date 2020/11/5 上午11:21
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

    /**
     * 根据机构id集合删除对应的角色-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author majianguo
     * @date 2020/11/5 上午11:21
     */
    void deleteRoleDataScopeListByOrgIdList(Set<Long> orgIdList);

    /**
     * 根据角色id删除对应的角色-数据范围关联信息
     *
     * @param roleId 角色id
     * @author majianguo
     * @date 2020/11/5 上午11:21
     */
    void deleteRoleDataScopeListByRoleId(Long roleId);

}
