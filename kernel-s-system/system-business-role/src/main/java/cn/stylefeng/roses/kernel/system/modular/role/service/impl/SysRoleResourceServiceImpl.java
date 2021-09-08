/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.system.modular.role.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRoleResource;
import cn.stylefeng.roses.kernel.system.modular.role.mapper.SysRoleResourceMapper;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleResourceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色菜单service接口实现类
 *
 * @author majianguo
 * @date 2020/11/5 上午11:32
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {

    @Resource(name = "roleResourceCacheApi")
    private CacheOperatorApi<List<String>> roleResourceCacheApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantResource(SysRoleRequest sysRoleRequest) {

        Long roleId = sysRoleRequest.getRoleId();

        // 删除所拥有角色关联的资源
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleResource::getRoleId, roleId);
        this.remove(queryWrapper);

        // 清除角色缓存资源
        roleResourceCacheApi.remove(String.valueOf(roleId));

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
    public void grantResourceV2(SysRoleRequest sysRoleRequest) {
        // 先将该业务下，模块下的所有资源删除掉
        List<String> modularTotalResource = sysRoleRequest.getModularTotalResource();
        if (ObjectUtil.isNotEmpty(modularTotalResource)) {
            LambdaUpdateWrapper<SysRoleResource> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(SysRoleResource::getResourceCode, modularTotalResource);
            wrapper.eq(SysRoleResource::getRoleId, sysRoleRequest.getRoleId());
            this.remove(wrapper);
        }

        // 再将该业务下，需要绑定的资源添加上
        List<String> selectedResource = sysRoleRequest.getSelectedResource();
        if (ObjectUtil.isNotEmpty(selectedResource)) {
            ArrayList<SysRoleResource> sysRoleResources = new ArrayList<>();
            for (String resourceCode : selectedResource) {
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setRoleId(sysRoleRequest.getRoleId());
                sysRoleResource.setResourceCode(resourceCode);
                sysRoleResources.add(sysRoleResource);
            }
            this.saveBatch(sysRoleResources, sysRoleResources.size());
        }

        // 清除角色绑定的资源缓存
        roleResourceCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleResourceListByResourceIds(List<Long> resourceIds) {

        // 查询资源包含的角色
        LambdaQueryWrapper<SysRoleResource> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.select(SysRoleResource::getRoleId);
        wrapper1.in(SysRoleResource::getResourceCode, resourceIds);
        wrapper1.groupBy(SysRoleResource::getRoleId);
        List<SysRoleResource> toGetRoles = this.list(wrapper1);
        List<Long> roleIds = toGetRoles.stream().map(SysRoleResource::getRoleId).collect(Collectors.toList());
        for (Long roleId : roleIds) {
            // 清除角色绑定的资源缓存
            roleResourceCacheApi.remove(String.valueOf(roleId));
        }

        // 清除资源
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

        // 清除角色绑定的资源缓存
        roleResourceCacheApi.remove(String.valueOf(roleId));

    }

}
