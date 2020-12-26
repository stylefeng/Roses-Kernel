package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.request.SysUserRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户角色service接口
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取账号绑定的角色
     *
     * @param userId 用户id
     * @author fengshuonan
     * @date 2020/12/22 16:52
     */
    List<SysUserRole> getUserRoles(Long userId);

    /**
     * 给某个用户授权角色
     *
     * @param sysUserRequest 用户和角色id集合
     * @author fengshuonan
     * @date 2020/11/21 14:44
     */
    void grantRole(SysUserRequest sysUserRequest);

    /**
     * 根据用户id删除对应的用户-角色表关联信息
     *
     * @param userId 用户id
     * @author luojie
     * @date 2020/11/6 15:03
     */
    void deleteUserRoleListByUserId(Long userId);

}
