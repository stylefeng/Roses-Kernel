/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package cn.stylefeng.roses.kernel.role.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.role.modular.entity.SysRoleResource;
import cn.stylefeng.roses.kernel.role.modular.mapper.SysRoleMenuMapper;
import cn.stylefeng.roses.kernel.role.modular.service.SysRoleResourceService;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
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
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleResource> implements SysRoleResourceService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantResource(SysRoleRequest sysRoleRequest) {

        Long roleId = sysRoleRequest.getId();

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
            sysRoleMenu.setResourceId(resourceId);
            sysRoleResources.add(sysRoleMenu);
        }
        this.saveBatch(sysRoleResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleResourceListByResourceIds(List<Long> resourceIds) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleResource::getResourceId, resourceIds);
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
