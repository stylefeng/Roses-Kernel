package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.UserOrgServiceApi;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户组织机构关联信息
 *
 * @author fengshuonan
 * @date 2020/12/19 22:17
 */
public interface SysUserOrgService extends IService<SysUserOrg>, UserOrgServiceApi {

    /**
     * 更新用户组织机构绑定
     *
     * @author fengshuonan
     * @date 2020/12/19 22:29
     */
    void updateUserOrg(Long userId, Long orgId, Long positionId);

    /**
     * 删除用户对应的组织机构信息
     *
     * @author fengshuonan
     * @date 2020/12/19 22:38
     */
    void deleteUserOrg(Long userId);

}