package cn.stylefeng.roses.kernel.system.modular.role.service.impl;

import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRoleResource;
import cn.stylefeng.roses.kernel.system.modular.role.mapper.SysRoleResourceMapper;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleResourceService;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色菜单service接口实现类
 *
 * @author majianguo
 * @date 2020/11/5 上午11:32
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantResource(SysRoleRequest sysRoleRequest) {

        Long roleId = sysRoleRequest.getRoleId();

        // 删除所拥有角色关联的资源
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleResource::getRoleId, roleId);
        this.remove(queryWrapper);

        // 授权资源
        List<String> grantResourceList = sysRoleRequest.getGrantResourceList();
        ArrayList<SysRoleResource> sysRoleResources = new ArrayList<>();

        // 批量保存角色授权资源
        for (String resourceId : grantResourceList) {
            SysRoleResource sysRoleMenu = new SysRoleResource();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setResourceCode(resourceId);
            sysRoleResources.add(sysRoleMenu);
        }
        this.saveBatch(sysRoleResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleResourceListByResourceIds(List<Long> resourceIds) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleResource::getResourceCode, resourceIds);
        this.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleResourceListByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleResource::getRoleId, roleId);
        this.remove(queryWrapper);
    }

}
