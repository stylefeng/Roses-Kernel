package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserRoleMapper;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户角色service接口实现类
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<SysUserRole> getUserRoles(Long userId) {
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> list = this.list(wrapper);

        // 账号下没有绑定角色
        if (list.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_BIND_ROLE);
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {

        // 获取用户id
        Long userId = sysUserRequest.getUserId();

        // 删除该用户的所有角色
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        this.remove(queryWrapper);

        // 为该用户授权角色
        List<Long> roleIdList = sysUserRequest.getGrantRoleIdList();

        ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        this.saveBatch(sysUserRoles);
    }

    @Override
    public void deleteUserRoleListByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);

        this.remove(queryWrapper);
    }
}