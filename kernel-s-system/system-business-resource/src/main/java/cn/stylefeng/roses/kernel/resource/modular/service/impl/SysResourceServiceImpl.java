package cn.stylefeng.roses.kernel.resource.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // 查询为菜单的
        wrapper.eq(SysResource::getMenuFlag, YesOrNotEnum.Y.getCode());

        // 只查询code和name
        wrapper.select(SysResource::getCode, SysResource::getName);

        List<SysResource> menuResourceList = this.list(wrapper);

        // 增加返回虚拟菜单的情况
        SysResource sysResource = new SysResource();
        sysResource.setCode("");
        sysResource.setName("虚拟目录(空)");
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
                resourceDefinition.setRequiredLogin(YesOrNotEnum.Y.name().equals(requiredLoginFlag));

                // 获取请求权限的标记，判断是否有权限，如果有则设置为true,否则为false
                String requiredPermissionFlag = resource.getRequiredPermissionFlag();
                resourceDefinition.setRequiredPermission(YesOrNotEnum.Y.name().equals(requiredPermissionFlag));

                // 获取是否是菜单的flag，如果是则设置为true,否则为false
                String menuFlag = resource.getMenuFlag();
                resourceDefinition.setMenuFlag(YesOrNotEnum.Y.name().equals(menuFlag));

                return resourceDefinition;
            }
        }
    }

    @Override
    public List<ResourceDefinition> getResourceListByIds(List<String> resourceIds) {

        ArrayList<ResourceDefinition> resourceDefinitions = new ArrayList<>();

        if (resourceIds == null || resourceIds.isEmpty()) {
            return resourceDefinitions;
        }

        // 拼接in条件
        LambdaQueryWrapper<SysResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysResource::getId, resourceIds);

        // 获取资源详情
        List<SysResource> list = this.list(queryWrapper);
        for (SysResource sysResource : list) {
            ResourceDefinition resourceDefinition = new ResourceDefinition();
            BeanUtil.copyProperties(sysResource, resourceDefinition);
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
        queryWrapper.in(SysResource::getId, resourceIds);
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
                queryWrapper.like(SysResource::getName, resourceRequest.getResourceName());
            }

            // 根据是否是菜单查询
            if (ObjectUtil.isNotEmpty(resourceRequest.getMenuFlag())) {
                queryWrapper.like(SysResource::getMenuFlag, resourceRequest.getMenuFlag());
            }
        }

        return queryWrapper;
    }

}