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
package cn.stylefeng.roses.kernel.system.starter.init;

import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRoleResource;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleResourceService;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化admin管理员的服务
 *
 * @author fengshuonan
 * @date 2020/12/17 21:56
 */
@Service
public class InitAdminService {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysResourceService sysResourceService;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    /**
     * 初始化超级管理员，超级管理员拥有最高权限
     *
     * @author fengshuonan
     * @date 2020/12/17 21:57
     */
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdmin() {

        // 找到超级管理员的角色id
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleCode, SystemConstants.SUPER_ADMIN_ROLE_CODE);
        SysRole superAdminRole = sysRoleService.getOne(queryWrapper);

        // 删除这个角色绑定的所有资源
        LambdaUpdateWrapper<SysRoleResource> sysRoleResourceLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        sysRoleResourceLambdaUpdateWrapper.eq(SysRoleResource::getRoleId, superAdminRole.getRoleId());
        sysRoleResourceService.remove(sysRoleResourceLambdaUpdateWrapper);

        // 找到所有Resource，将所有资源赋给这个角色
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.select(SysResource::getResourceCode);
        List<SysResource> resources = sysResourceService.list(sysResourceLambdaQueryWrapper);

        ArrayList<SysRoleResource> sysRoleResources = new ArrayList<>();
        for (SysResource resource : resources) {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setResourceCode(resource.getResourceCode());
            sysRoleResource.setRoleId(superAdminRole.getRoleId());
            sysRoleResources.add(sysRoleResource);
        }
        sysRoleResourceService.saveBatch(sysRoleResources, sysRoleResources.size());
    }

}
