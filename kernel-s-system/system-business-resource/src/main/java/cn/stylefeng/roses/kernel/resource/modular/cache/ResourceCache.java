package cn.stylefeng.roses.kernel.resource.modular.cache;

import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.resource.modular.factory.ResourceFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源缓存
 *
 * @author fengshuonan
 * @date 2019-09-10-17:29
 */
@Component
public class ResourceCache {

    private final List<ResourceDefinition> resourceDefinitions = new ArrayList<>();

    /**
     * 保存资源存储到缓存
     *
     * @author fengshuonan
     * @date 2020/11/24 20:06
     */
    public void saveResourcesToCache(List<SysResource> sysResources) {
        if (sysResources == null || sysResources.size() == 0) {
            return;
        }

        for (SysResource sysResource : sysResources) {
            ResourceDefinition resourceDefinition = ResourceFactory.createResourceDefinition(sysResource);
            resourceDefinitions.add(resourceDefinition);
        }
    }

    /**
     * 获取缓存的所有资源
     *
     * @author fengshuonan
     * @date 2020/12/9 14:22
     */
    public List<ResourceDefinition> getAllCaches() {
        return resourceDefinitions;
    }

}
