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
package cn.stylefeng.roses.kernel.system.modular.menu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuResource;
import cn.stylefeng.roses.kernel.system.modular.menu.mapper.SysMenuResourceMapper;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuResourceService;
import cn.stylefeng.roses.kernel.system.modular.resource.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
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
 * 系统资源信息关联
 *
 * @author fengshuonan
 * @date 2021/8/8 21:38
 */
@Service
public class SysMenuResourceServiceImpl extends ServiceImpl<SysMenuResourceMapper, SysMenuResource> implements SysMenuResourceService {

    @Resource
    private SysResourceService sysResourceService;

    @Override
    public List<ResourceTreeNode> getMenuResourceTree(Long businessId) {
        LambdaQueryWrapper<SysMenuResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenuResource::getBusinessId, businessId);
        List<SysMenuResource> list = this.list(wrapper);

        List<String> resourceCodes = list.stream().map(SysMenuResource::getResourceCode).collect(Collectors.toList());
        return sysResourceService.getResourceList(resourceCodes, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenuResourceBind(SysMenuResourceRequest sysMenuResourceRequest) {
        // 先将该业务下，模块下的所有资源删除掉
        List<String> modularTotalResource = sysMenuResourceRequest.getModularTotalResource();
        if (ObjectUtil.isNotEmpty(modularTotalResource)) {
            LambdaUpdateWrapper<SysMenuResource> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(SysMenuResource::getResourceCode, modularTotalResource);
            wrapper.eq(SysMenuResource::getBusinessId, sysMenuResourceRequest.getBusinessId());
            this.remove(wrapper);
        }

        // 再将该业务下，需要绑定的资源添加上
        List<String> selectedResource = sysMenuResourceRequest.getSelectedResource();
        if (ObjectUtil.isNotEmpty(selectedResource)) {
            ArrayList<SysMenuResource> menuResources = new ArrayList<>();
            for (String resourceCode : selectedResource) {
                SysMenuResource sysMenuResource = new SysMenuResource();
                sysMenuResource.setBusinessType(sysMenuResourceRequest.getBusinessType());
                sysMenuResource.setBusinessId(sysMenuResourceRequest.getBusinessId());
                sysMenuResource.setResourceCode(resourceCode);
                menuResources.add(sysMenuResource);
            }
            this.saveBatch(menuResources, menuResources.size());
        }
    }

}
