package cn.stylefeng.roses.kernel.resource.modular.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.resource.api.ResourceReportApi;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ReportResourceParam;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceUrlParam;
import cn.stylefeng.roses.kernel.resource.modular.cache.ResourceCache;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.resource.modular.factory.ResourceFactory;
import cn.stylefeng.roses.kernel.resource.modular.mapper.SysResourceMapper;
import cn.stylefeng.roses.kernel.resource.modular.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.resource.modular.service.SysResourceService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.ResourceServiceApi;
import cn.stylefeng.roses.kernel.system.pojo.resource.request.ResourceRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.stylefeng.roses.kernel.system.constants.SystemConstants.DEFAULT_PARENT_ID;

/**
 * 资源表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/23 22:45
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService, ResourceReportApi, ResourceServiceApi {

    @Autowired
    private SysResourceMapper resourceMapper;

    @Autowired
    private ResourceCache resourceCache;

    @Override
    public Page<SysResource> getResourceList(ResourceRequest resourceRequest) {
        Page<SysResource> page = PageFactory.defaultPage();
        LambdaQueryWrapper<SysResource> wrapper = createWrapper(resourceRequest);
        return this.page(page, wrapper);
    }

    @Override
    public List<SysResource> getMenuResourceList(ResourceRequest resourceRequest) {

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
    public void deleteResourceByProjectCode(String projectCode) {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysResource::getProjectCode, projectCode);
        this.remove(wrapper);
    }

    @Override
    public List<ResourceTreeNode> getResourceTree() {

        // 1. 获取所有的资源
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.select(SysResource::getAppCode, SysResource::getModularCode, SysResource::getModularName, SysResource::getResourceCode, SysResource::getUrl, SysResource::getResourceName);
        List<SysResource> allResource = this.list(sysResourceLambdaQueryWrapper);

        // 2. 按应用和模块编码设置map
        Map<String, Map<String, List<ResourceTreeNode>>> appModularResources = divideResources(allResource);

        // 3. 根据map组装资源树
        return createResourceTree(appModularResources);
    }

    @Override
    public ResourceDefinition getResourceDetail(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, resourceRequest.getResourceCode());
        SysResource sysResource = this.getOne(sysResourceLambdaQueryWrapper);
        if (sysResource != null) {
            return ResourceFactory.createResourceDefinition(sysResource);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportResources(@RequestBody ReportResourceParam reportResourceReq) {

        String projectCode = reportResourceReq.getProjectCode();
        Map<String, Map<String, ResourceDefinition>> resourceDefinitions = reportResourceReq.getResourceDefinitions();

        if (ObjectUtil.isEmpty(projectCode) || resourceDefinitions == null) {
            return;
        }

        //根据project删除该项目下的所有资源
        this.deleteResourceByProjectCode(projectCode);

        //获取当前应用的所有资源
        ArrayList<SysResource> allResources = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResourceDefinition>> appModularResources : resourceDefinitions.entrySet()) {
            Map<String, ResourceDefinition> value = appModularResources.getValue();
            for (Map.Entry<String, ResourceDefinition> modularResources : value.entrySet()) {
                SysResource resource = ResourceFactory.createResource(modularResources.getValue());
                allResources.add(resource);
            }
        }

        //将资源存入库中
        this.saveBatch(allResources, allResources.size());

        //将资源存入缓存一份
        resourceCache.saveResourcesToCache(allResources);
    }

    @Override
    public ResourceDefinition getResourceByUrl(@RequestBody ResourceUrlParam resourceUrlReq) {
        if (ObjectUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        } else {

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

                return resourceDefinition;
            }
        }
    }

    @Override
    public List<ResourceDefinition> getResourceListByIds(List<String> resourceCodes) {

        ArrayList<ResourceDefinition> resourceDefinitions = new ArrayList<>();

        if (resourceCodes == null || resourceCodes.isEmpty()) {
            return resourceDefinitions;
        }

        // 拼接in条件
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysResource::getResourceCode, resourceCodes);

        // 获取资源详情
        List<SysResource> list = this.list(queryWrapper);
        for (SysResource sysResource : list) {
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource);
            resourceDefinitions.add(resourceDefinition);
        }

        return resourceDefinitions;
    }

    @Override
    public List<String> getResourceUrlsListByIds(List<String> resourceIds) {

        ArrayList<String> resourceUrls = new ArrayList<>();

        if (resourceIds == null || resourceIds.isEmpty()) {
            return resourceUrls;
        }

        // 拼接in条件
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysResource::getResourceId, resourceIds);
        queryWrapper.select(SysResource::getUrl);

        // 获取资源详情
        List<SysResource> list = this.list(queryWrapper);
        return list.stream().map(SysResource::getUrl).collect(Collectors.toList());
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysResource> createWrapper(ResourceRequest resourceRequest) {
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(resourceRequest)) {
            // 根据应用编码查询
            if (ObjectUtil.isNotEmpty(resourceRequest.getAppCode())) {
                queryWrapper.eq(SysResource::getAppCode, resourceRequest.getAppCode());
            }

            // 根据资源名称
            if (ObjectUtil.isNotEmpty(resourceRequest.getResourceName())) {
                queryWrapper.like(SysResource::getResourceName, resourceRequest.getResourceName());
            }
        }

        return queryWrapper;
    }

    /**
     * 划分数据库中的资源，切分成应用和模块分类的集合
     *
     * @return 第一个key是应用名称，第二个key是模块名称，值是应用对应的模块对应的资源列表
     * @author fengshuonan
     * @date 2020/12/18 15:34
     */
    private Map<String, Map<String, List<ResourceTreeNode>>> divideResources(List<SysResource> sysResources) {
        HashMap<String, Map<String, List<ResourceTreeNode>>> appModularResources = new HashMap<>();
        for (SysResource sysResource : sysResources) {

            // 查询应用下有无资源
            String appCode = sysResource.getAppCode();
            Map<String, List<ResourceTreeNode>> modularResource = appModularResources.get(appCode);

            // 该应用下没资源就创建一个map
            if (modularResource == null) {
                modularResource = new HashMap<>();
            }

            // 查询当前资源的模块，有没有在appModularResources存在
            List<ResourceTreeNode> resourceTreeNodes = modularResource.get(sysResource.getModularCode());
            if (resourceTreeNodes == null) {
                resourceTreeNodes = new ArrayList<>();
            }

            // 将当前资源放入资源集合
            ResourceTreeNode resourceTreeNode = new ResourceTreeNode();
            resourceTreeNode.setResourceFlag(true);
            resourceTreeNode.setNodeName(sysResource.getUrl() + "(" + sysResource.getResourceName() + ")");
            resourceTreeNode.setCode(sysResource.getResourceCode());
            resourceTreeNode.setParentCode(sysResource.getModularCode());
            resourceTreeNodes.add(resourceTreeNode);

            modularResource.put(sysResource.getModularCode(), resourceTreeNodes);
            appModularResources.put(appCode, modularResource);
        }
        return appModularResources;
    }

    /**
     * 根据归好类的资源，创建资源树
     *
     * @author fengshuonan
     * @date 2020/12/18 15:45
     */
    private List<ResourceTreeNode> createResourceTree(Map<String, Map<String, List<ResourceTreeNode>>> appModularResources) {

        List<ResourceTreeNode> finalTree = new ArrayList<>();

        // 按应用遍历应用模块资源集合
        for (String appName : appModularResources.keySet()) {

            // 创建当前应用节点
            ResourceTreeNode appNode = new ResourceTreeNode();
            appNode.setCode(appName);
            appNode.setNodeName(appName);
            appNode.setResourceFlag(false);
            appNode.setParentCode(DEFAULT_PARENT_ID.toString());

            // 遍历当前应用下的模块资源
            Map<String, List<ResourceTreeNode>> modularResources = appModularResources.get(appName);

            // 创建模块节点
            ArrayList<ResourceTreeNode> modularNodes = new ArrayList<>();
            for (String modularName : modularResources.keySet()) {
                ResourceTreeNode modularNode = new ResourceTreeNode();
                modularNode.setCode(modularName);
                modularNode.setNodeName(modularName);
                modularNode.setResourceFlag(false);
                modularNode.setParentCode(appName);
                modularNode.setChildren(modularResources.get(modularName));
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