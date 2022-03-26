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
package cn.stylefeng.roses.kernel.scanner;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.scanner.api.ResourceCollectorApi;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的资源收集器，默认搜集到内存Map中
 *
 * @author fengshuonan
 * @date 2020/10/19 20:33
 */
public class DefaultResourceCollector implements ResourceCollectorApi {

    /**
     * 以资源编码为key，存放资源集合
     */
    private final Map<String, ResourceDefinition> resourceDefinitions = new ConcurrentHashMap<>();

    /**
     * 第一个key以模块编码（控制器名称下划线分割），第二个key是资源编码，存放资源集合
     */
    private final Map<String, Map<String, ResourceDefinition>> modularResourceDefinitions = new ConcurrentHashMap<>();

    /**
     * key以模块编码（控制器名称下划线分割），value是控制器的中文名称
     */
    private final Map<String, String> controllerCodeNameDict = new HashMap<>();

    /**
     * key是请求的url，value是资源信息
     */
    private final Map<String, ResourceDefinition> urlDefineResources = new ConcurrentHashMap<>();

    @Override
    public void collectResources(List<ResourceDefinition> apiResource) {
        if (apiResource != null && apiResource.size() > 0) {
            for (ResourceDefinition resourceDefinition : apiResource) {
                ResourceDefinition alreadyFlag = resourceDefinitions.get(resourceDefinition.getResourceCode());
                if (alreadyFlag != null) {
                    throw new RuntimeException("资源扫描过程中存在重复资源！\n已存在资源：" + alreadyFlag + "\n新资源为： " + resourceDefinition);
                }
                resourceDefinitions.put(resourceDefinition.getResourceCode(), resourceDefinition);
                urlDefineResources.put(resourceDefinition.getUrl(), resourceDefinition);

                // 存储模块资源
                Map<String, ResourceDefinition> modularResources = modularResourceDefinitions.get(StrUtil.toUnderlineCase(resourceDefinition.getModularCode()));
                if (modularResources == null) {
                    modularResources = new HashMap<>();
                    modularResources.put(resourceDefinition.getResourceCode(), resourceDefinition);
                    modularResourceDefinitions.put(StrUtil.toUnderlineCase(resourceDefinition.getModularCode()), modularResources);
                } else {
                    modularResources.put(resourceDefinition.getResourceCode(), resourceDefinition);
                }

                // 添加资源code-中文名称字典
                this.bindResourceName(resourceDefinition.getResourceCode(), resourceDefinition.getResourceName());
            }
        }
    }

    @Override
    public ResourceDefinition getResource(String resourceCode) {
        return resourceDefinitions.get(resourceCode);
    }

    @Override
    public List<ResourceDefinition> getAllResources() {
        Set<Map.Entry<String, ResourceDefinition>> entries = resourceDefinitions.entrySet();
        ArrayList<ResourceDefinition> resourceDefinitions = new ArrayList<>();
        for (Map.Entry<String, ResourceDefinition> entry : entries) {
            resourceDefinitions.add(entry.getValue());
        }
        return resourceDefinitions;
    }

    @Override
    public List<ResourceDefinition> getResourcesByModularCode(String code) {
        Map<String, ResourceDefinition> stringResourceDefinitionMap = modularResourceDefinitions.get(code);
        ArrayList<ResourceDefinition> resourceDefinitions = new ArrayList<>();
        for (String key : stringResourceDefinitionMap.keySet()) {
            ResourceDefinition resourceDefinition = stringResourceDefinitionMap.get(key);
            resourceDefinitions.add(resourceDefinition);
        }
        return resourceDefinitions;
    }

    @Override
    public String getResourceName(String code) {
        return controllerCodeNameDict.get(code);
    }

    @Override
    public void bindResourceName(String code, String name) {
        controllerCodeNameDict.putIfAbsent(code, name);
    }

    @Override
    public Map<String, Map<String, ResourceDefinition>> getModularResources() {
        return this.modularResourceDefinitions;
    }

    @Override
    public String getResourceUrl(String code) {
        ResourceDefinition resourceDefinition = this.resourceDefinitions.get(code);
        if (resourceDefinition == null) {
            return null;
        } else {
            return resourceDefinition.getUrl();
        }
    }

    @Override
    public ResourceDefinition getResourceByUrl(String url) {
        return this.urlDefineResources.get(url);
    }

    @Override
    public Integer getAllResourceCount() {
        return this.resourceDefinitions.size();
    }

}
