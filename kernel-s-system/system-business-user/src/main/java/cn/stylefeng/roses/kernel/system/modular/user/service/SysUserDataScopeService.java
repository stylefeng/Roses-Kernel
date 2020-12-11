package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.request.SysUserRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户数据范围service接口
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
public interface SysUserDataScopeService extends IService<SysUserDataScope> {

    /**
     * 授权数据范围（组织机构id集合）给某个用户
     *
     * @param sysUserRequest 授权参数
     * @author fengshuonan
     * @date 2020/11/21 14:49
     */
    void grantData(SysUserRequest sysUserRequest);

    /**
     * 获取用户的数据范围id集合
     *
     * @param uerId 用户id
     * @return 数据范围id集合
     * @author luojie
     * @date 2020/11/6 15:01
     */
    List<Long> getUserDataScopeIdList(Long uerId);

    /**
     * 根据用户id删除对应的用户-数据范围关联信息
     *
     * @param userId 用户id
     * @author luojie
     * @date 2020/11/6 15:01
     */
    void deleteUserDataScopeListByUserId(Long userId);
}
