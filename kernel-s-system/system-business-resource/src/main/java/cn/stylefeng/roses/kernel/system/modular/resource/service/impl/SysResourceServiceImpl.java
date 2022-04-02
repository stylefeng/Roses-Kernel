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
package cn.stylefeng.roses.kernel.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.LoginUserApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.scanner.api.ResourceReportApi;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ReportResourceParam;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import cn.stylefeng.roses.kernel.system.api.ResourceServiceApi;
import cn.stylefeng.roses.kernel.system.api.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.LayuiApiResourceTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ResourceRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleResourceDTO;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.factory.ResourceFactory;
import cn.stylefeng.roses.kernel.system.modular.resource.mapper.SysResourceMapper;
import cn.stylefeng.roses.kernel.system.modular.resource.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/23 22:45
 */
@Service
@Slf4j
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService, ResourceReportApi, ResourceServiceApi {

    @Resource
    private SysResourceMapper resourceMapper;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource(name = "resourceCache")
    private CacheOperatorApi<ResourceDefinition> resourceCache;

    @Override
    public PageResult<SysResource> findPage(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> wrapper = createWrapper(resourceRequest);
        Page<SysResource> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysResource> findList(ResourceRequest resourceRequest) {

        LambdaQueryWrapper<SysResource> wrapper = createWrapper(resourceRequest);

        // 只查询code和name
        wrapper.select(SysResource::getResourceCode, SysResource::getResourceName);

        List<SysResource> menuResourceList = this.list(wrapper);

        // 增加返回虚拟菜单的情况
        SysResource sysResource = new SysResource();
        sysResource.setResourceCode("");
        sysResource.setResourceName("虚拟目录(空)");
        menuResourceList.add(0, sysResource);

        return menuResourceList;
    }

    @Override
    public List<ResourceTreeNode> getRoleResourceTree(Long roleId, Boolean treeBuildFlag) {

        // 查询当前角色已有的接口
        List<SysRoleResourceDTO> resourceList = roleServiceApi.getRoleResourceList(Collections.singletonList(roleId));

        // 该角色已拥有权限
        List<String> alreadyList = new ArrayList<>();
        for (SysRoleResourceDTO sysRoleResponse : resourceList) {
            alreadyList.add(sysRoleResponse.getResourceCode());
        }

        return this.getResourceList(alreadyList, treeBuildFlag);
    }

    @Override
    public List<ResourceTreeNode> getResourceList(List<String> resourceCodes, Boolean treeBuildFlag) {
        List<ResourceTreeNode> res = new ArrayList<>();

        // 获取所有的资源
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.select(SysResource::getAppCode, SysResource::getModularCode, SysResource::getModularName, SysResource::getResourceCode, SysResource::getUrl, SysResource::getResourceName);

        // 只查询需要授权的接口
        sysResourceLambdaQueryWrapper.eq(SysResource::getRequiredPermissionFlag, YesOrNotEnum.Y.getCode());

        LoginUserApi loginUserApi = LoginContext.me();
        if (!loginUserApi.getSuperAdminFlag()) {
            // 获取权限列表
            List<Long> roleIds = loginUserApi.getLoginUser().getSimpleRoleInfoList().parallelStream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toList());
            Set<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
            if (!resourceCodeList.isEmpty()) {
                sysResourceLambdaQueryWrapper.in(SysResource::getResourceCode, resourceCodeList);
            }
        }

        List<SysResource> allResource = this.list(sysResourceLambdaQueryWrapper);

        // 根据模块名称把资源分类
        Map<String, List<SysResource>> modularMap = new HashMap<>();
        for (SysResource sysResource : allResource) {
            List<SysResource> sysResources = modularMap.get(sysResource.getModularName());

            // 没有就新建一个
            if (ObjectUtil.isEmpty(sysResources)) {
                sysResources = new ArrayList<>();
                modularMap.put(sysResource.getModularName(), sysResources);
            }
            // 把自己加入进去
            sysResources.add(sysResource);
        }

        // 创建一级节点
        for (Map.Entry<String, List<SysResource>> entry : modularMap.entrySet()) {
            ResourceTreeNode item = new ResourceTreeNode();
            item.setResourceFlag(false);
            String id = IdWorker.get32UUID();
            item.setCode(id);
            item.setParentCode(RuleConstants.TREE_ROOT_ID.toString());
            item.setNodeName(entry.getKey());

            // 设置临时变量，统计半开状态
            int checkedNumber = 0;

            //创建二级节点
            for (SysResource resource : entry.getValue()) {
                ResourceTreeNode subItem = new ResourceTreeNode();
                // 判断是否已经拥有
                if (!resourceCodes.contains(resource.getResourceCode())) {
                    subItem.setChecked(false);
                } else {
                    checkedNumber++;

                    // 让父类也选择
                    item.setChecked(true);
                    subItem.setChecked(true);
                }
                subItem.setResourceFlag(true);
                subItem.setNodeName(resource.getResourceName());
                subItem.setCode(resource.getResourceCode());
                subItem.setParentCode(id);
                res.add(subItem);
            }

            // 统计选中的数量
            if (checkedNumber == entry.getValue().size()) {
                item.setChecked(true);
                item.setIndeterminate(false);
            } else if (checkedNumber == 0) {
                item.setChecked(false);
                item.setIndeterminate(false);
            } else {
                item.setChecked(false);
                item.setIndeterminate(true);
            }

            res.add(item);
        }

        // 根据map组装资源树
        if (treeBuildFlag) {
            return new DefaultTreeBuildFactory<ResourceTreeNode>().doTreeBuild(res);
        } else {
            return res;
        }
    }

    @Override
    public List<LayuiApiResourceTreeNode> getApiResourceTree(ResourceRequest resourceRequest) {

        // 1. 获取所有的资源
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());
        sysResourceLambdaQueryWrapper.select(SysResource::getAppCode, SysResource::getModularCode, SysResource::getModularName, SysResource::getResourceCode, SysResource::getUrl, SysResource::getResourceName);

        // 查询条件
        if (ObjectUtil.isNotEmpty(resourceRequest.getResourceName())) {
            sysResourceLambdaQueryWrapper
                    .like(SysResource::getUrl, resourceRequest.getResourceName())
                    .or()
                    .like(SysResource::getResourceName, resourceRequest.getResourceName());
        }

        List<SysResource> allResource = this.list(sysResourceLambdaQueryWrapper);

        // 2. 按应用和模块编码设置map
        Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = divideResources(allResource);

        // 3. 创建模块code和模块name的映射
        Map<String, String> modularCodeName = createModularCodeName(allResource);

        // 4. 根据map组装资源树
        return createResourceTree(appModularResources, modularCodeName);
    }

    @Override
    public ResourceDefinition getApiResourceDetail(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, resourceRequest.getResourceCode());
        SysResource sysResource = this.getOne(sysResourceLambdaQueryWrapper);
        if (sysResource != null) {

            // 实体转化为ResourceDefinition
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource);

            // 填充具体的提示信息
            return ResourceFactory.fillResourceDetail(resourceDefinition);
        } else {
            return null;
        }
    }

    @Override
    public void deleteResourceByProjectCode(String projectCode) {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysResource::getProjectCode, projectCode);
        this.remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportResources(@RequestBody ReportResourceParam reportResourceReq) {
        this.reportResourcesAndGetResult(reportResourceReq);
    }

    @Override
    public List<SysResourcePersistencePojo> reportResourcesAndGetResult(ReportResourceParam reportResourceReq) {
        String projectCode = reportResourceReq.getProjectCode();
        Map<String, Map<String, ResourceDefinition>> resourceDefinitions = reportResourceReq.getResourceDefinitions();

        if (ObjectUtil.isEmpty(projectCode) || resourceDefinitions == null) {
            return new ArrayList<>();
        }

        //根据project删除该项目下的所有资源
        this.deleteResourceByProjectCode(projectCode);

        //获取当前应用的所有资源
        ArrayList<SysResource> allResources = new ArrayList<>();
        ArrayList<ResourceDefinition> resourceDefinitionArrayList = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResourceDefinition>> appModularResources : resourceDefinitions.entrySet()) {
            Map<String, ResourceDefinition> value = appModularResources.getValue();
            for (Map.Entry<String, ResourceDefinition> modularResources : value.entrySet()) {
                resourceDefinitionArrayList.add(modularResources.getValue());
                SysResource resource = ResourceFactory.createResource(modularResources.getValue());
                allResources.add(resource);
            }
        }

        //将资源存入库中
        this.saveBatch(allResources, allResources.size());

        //将资源存入缓存一份
        Map<String, ResourceDefinition> resourceDefinitionMap = ResourceFactory.orderedResourceDefinition(resourceDefinitionArrayList);
        for (Map.Entry<String, ResourceDefinition> entry : resourceDefinitionMap.entrySet()) {
            resourceCache.put(entry.getKey(), entry.getValue());
        }

        // 组装返回结果
        ArrayList<SysResourcePersistencePojo> finalResult = new ArrayList<>();
        for (SysResource item : allResources) {
            SysResourcePersistencePojo sysResourcePersistencePojo = new SysResourcePersistencePojo();
            BeanUtil.copyProperties(item, sysResourcePersistencePojo);
            finalResult.add(sysResourcePersistencePojo);
        }

        return finalResult;
    }

    @Override
    public ResourceDefinition getResourceByUrl(@RequestBody ResourceUrlParam resourceUrlReq) {
        if (ObjectUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        } else {

            // 先从缓存中查询
            ResourceDefinition tempCachedResourceDefinition = resourceCache.get(resourceUrlReq.getUrl());
            if (tempCachedResourceDefinition != null) {
                return tempCachedResourceDefinition;
            }

            // 缓存中没有去数据库查询
            List<SysResource> resources = resourceMapper.selectList(new QueryWrapper<SysResource>().eq("url", resourceUrlReq.getUrl()));

            if (resources == null || resources.isEmpty()) {
                return null;
            } else {

                // 获取接口资源信息
                SysResource resource = resources.get(0);
                ResourceDefinition resourceDefinition = new ResourceDefinition();
                BeanUtils.copyProperties(resource, resourceDefinition);

                // 获取是否需要登录的标记, 判断是否需要登录，如果是则设置为true,否则为false
                String requiredLoginFlag = resource.getRequiredLoginFlag();
                resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y.name().equals(requiredLoginFlag));

                // 获取请求权限的标记，判断是否有权限，如果有则设置为true,否则为false
                String requiredPermissionFlag = resource.getRequiredPermissionFlag();
                resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y.name().equals(requiredPermissionFlag));

                // 查询结果添加到缓存
                resourceCache.put(resourceDefinition.getUrl(), resourceDefinition);

                return resourceDefinition;
            }
        }
    }

    @Override
    public Set<String> getResourceUrlsListByCodes(Set<String> resourceCodes) {

        if (resourceCodes == null || resourceCodes.isEmpty()) {
            return new HashSet<>();
        }

        // 拼接in条件
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysResource::getResourceCode, resourceCodes);
        queryWrapper.select(SysResource::getUrl);

        // 获取资源详情
        List<SysResource> list = this.list(queryWrapper);
        return list.stream().map(SysResource::getUrl).collect(Collectors.toSet());
    }

    @Override
    public Integer getResourceCount() {
        return this.count();
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysResource> createWrapper(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isEmpty(resourceRequest)) {
            return queryWrapper;
        }

        // 根据应用编码查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(resourceRequest.getAppCode()), SysResource::getAppCode, resourceRequest.getAppCode());

        // 根据资源名称
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceRequest.getResourceName()), SysResource::getResourceName, resourceRequest.getResourceName());

        // 根据资源url
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceRequest.getUrl()), SysResource::getUrl, resourceRequest.getUrl());

        return queryWrapper;
    }

    /**
     * 划分数据库中的资源，切分成应用和模块分类的集合
     *
     * @return 第一个key是应用名称，第二个key是模块名称，值是应用对应的模块对应的资源列表
     * @author fengshuonan
     * @date 2020/12/18 15:34
     */
    private Map<String, Map<String, List<LayuiApiResourceTreeNode>>> divideResources(List<SysResource> sysResources) {
        HashMap<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources = new HashMap<>();
        for (SysResource sysResource : sysResources) {

            // 查询应用下有无资源
            String appCode = sysResource.getAppCode();
            Map<String, List<LayuiApiResourceTreeNode>> modularResource = appModularResources.get(appCode);

            // 该应用下没资源就创建一个map
            if (modularResource == null) {
                modularResource = new HashMap<>();
            }

            // 查询当前资源的模块，有没有在appModularResources存在
            List<LayuiApiResourceTreeNode> resourceTreeNodes = modularResource.get(sysResource.getModularCode());
            if (resourceTreeNodes == null) {
                resourceTreeNodes = new ArrayList<>();
            }

            // 将当前资源放入资源集合
            LayuiApiResourceTreeNode resourceTreeNode = new LayuiApiResourceTreeNode();
            resourceTreeNode.setResourceFlag(true);
            resourceTreeNode.setTitle(sysResource.getResourceName());
            resourceTreeNode.setId(sysResource.getResourceCode());
            resourceTreeNode.setParentId(sysResource.getModularCode());
            resourceTreeNode.setUrl(sysResource.getUrl());
            resourceTreeNode.setSpread(false);
            resourceTreeNode.setSlotsValue();
            resourceTreeNodes.add(resourceTreeNode);

            modularResource.put(sysResource.getModularCode(), resourceTreeNodes);
            appModularResources.put(appCode, modularResource);
        }
        return appModularResources;
    }

    /**
     * 创建模块code和name的映射
     *
     * @author fengshuonan
     * @date 2020/12/21 11:23
     */
    private Map<String, String> createModularCodeName(List<SysResource> resources) {
        HashMap<String, String> modularCodeName = new HashMap<>();
        for (SysResource resource : resources) {
            modularCodeName.put(resource.getModularCode(), resource.getModularName());
        }
        return modularCodeName;
    }

    /**
     * 根据归好类的资源，创建资源树
     *
     * @author fengshuonan
     * @date 2020/12/18 15:45
     */
    private List<LayuiApiResourceTreeNode> createResourceTree(Map<String, Map<String, List<LayuiApiResourceTreeNode>>> appModularResources, Map<String, String> modularCodeName) {

        List<LayuiApiResourceTreeNode> finalTree = new ArrayList<>();

        // 按应用遍历应用模块资源集合
        for (String appName : appModularResources.keySet()) {

            // 创建当前应用节点
            LayuiApiResourceTreeNode appNode = new LayuiApiResourceTreeNode();
            appNode.setId(appName);
            appNode.setTitle(appName);
            appNode.setSpread(true);
            appNode.setResourceFlag(false);
            appNode.setSlotsValue();
            appNode.setParentId(TreeConstants.DEFAULT_PARENT_ID.toString());

            // 遍历当前应用下的模块资源
            Map<String, List<LayuiApiResourceTreeNode>> modularResources = appModularResources.get(appName);

            // 创建模块节点
            ArrayList<LayuiApiResourceTreeNode> modularNodes = new ArrayList<>();
            for (String modularCode : modularResources.keySet()) {
                LayuiApiResourceTreeNode modularNode = new LayuiApiResourceTreeNode();
                modularNode.setId(modularCode);
                modularNode.setTitle(modularCodeName.get(modularCode));
                modularNode.setParentId(appName);
                modularNode.setSpread(false);
                modularNode.setResourceFlag(false);
                modularNode.setChildren(modularResources.get(modularCode));
                modularNode.setSlotsValue();
                modularNodes.add(modularNode);
            }

            // 当前应用下添加模块的资源
            appNode.setChildren(modularNodes);

            // 添加到最终结果
            finalTree.add(appNode);
        }

        return finalTree;
    }

}
