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
package cn.stylefeng.roses.kernel.system.modular.resource.cache;

import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.factory.ResourceFactory;
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
