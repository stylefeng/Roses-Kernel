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
package cn.stylefeng.roses.kernel.system.modular.resource.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ResourceDefinition和SysResource实体互转
 *
 * @author fengshuonan
 * @date 2019-05-29-14:37
 */
public class ResourceFactory {

    /**
     * ResourceDefinition转化为SysResource实体
     *
     * @author fengshuonan
     * @date 2020/12/9 14:14
     */
    public static SysResource createResource(ResourceDefinition resourceDefinition) {
        SysResource resource = new SysResource();
        BeanUtil.copyProperties(resourceDefinition, resource, CopyOptions.create().ignoreError());
        resource.setResourceCode(resourceDefinition.getResourceCode());

        if (resourceDefinition.getRequiredLoginFlag()) {
            resource.setRequiredLoginFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredLoginFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getRequiredPermissionFlag()) {
            resource.setRequiredPermissionFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredPermissionFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getViewFlag()) {
            resource.setViewFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setViewFlag(YesOrNotEnum.N.name());
        }

        // 转化校验组
        if (ObjectUtil.isNotEmpty(resourceDefinition.getValidateGroups())) {
            resource.setValidateGroups(JSON.toJSONString(resourceDefinition.getValidateGroups(), SerializerFeature.WriteClassName));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getParamFieldDescriptions())) {
            resource.setParamFieldDescriptions(JSON.toJSONString(resourceDefinition.getParamFieldDescriptions(), SerializerFeature.WriteClassName));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(resourceDefinition.getResponseFieldDescriptions())) {
            resource.setResponseFieldDescriptions(JSON.toJSONString(resourceDefinition.getResponseFieldDescriptions(), SerializerFeature.WriteClassName));
        }

        return resource;
    }

    /**
     * SysResource实体转化为ResourceDefinition对象
     *
     * @author fengshuonan
     * @date 2020/12/9 14:15
     */
    public static ResourceDefinition createResourceDefinition(SysResource sysResource) {

        ResourceDefinition resourceDefinition = new ResourceDefinition();

        // 拷贝公共属性
        BeanUtil.copyProperties(sysResource, resourceDefinition, CopyOptions.create().ignoreError());

        // 设置是否需要登录标识，Y为需要登录
        resourceDefinition.setRequiredLoginFlag(YesOrNotEnum.Y.name().equals(sysResource.getRequiredLoginFlag()));

        // 设置是否需要权限认证标识，Y为需要权限认证
        resourceDefinition.setRequiredPermissionFlag(YesOrNotEnum.Y.name().equals(sysResource.getRequiredPermissionFlag()));

        // 设置是否是视图类型
        resourceDefinition.setViewFlag(YesOrNotEnum.Y.name().equals(sysResource.getViewFlag()));

        // 转化校验组
        if (ObjectUtil.isNotEmpty(sysResource.getValidateGroups())) {
            resourceDefinition.setValidateGroups(JSON.parseObject(sysResource.getValidateGroups(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口参数的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getParamFieldDescriptions())) {
            resourceDefinition.setParamFieldDescriptions(JSON.parseObject(sysResource.getParamFieldDescriptions(), Set.class, Feature.SupportAutoType));
        }

        // 转化接口返回结果的字段描述
        if (ObjectUtil.isNotEmpty(sysResource.getResponseFieldDescriptions())) {
            resourceDefinition.setResponseFieldDescriptions(JSON.parseObject(sysResource.getResponseFieldDescriptions(), FieldMetadata.class, Feature.SupportAutoType));
        }

        return resourceDefinition;
    }

    /**
     * ResourceDefinition转化为api界面的详情信息
     *
     * @author fengshuonan
     * @date 2021/1/16 16:09
     */
    public static ResourceDefinition fillResourceDetail(ResourceDefinition resourceDefinition) {

        // 这个接口的校验组信息
        Set<String> validateGroups = resourceDefinition.getValidateGroups();

        // 接口的请求参数信息
        Set<FieldMetadata> paramFieldDescriptions = resourceDefinition.getParamFieldDescriptions();
        if(paramFieldDescriptions != null && paramFieldDescriptions.size() > 0){
            for (FieldMetadata fieldMetadata : paramFieldDescriptions) {
                fillDetailMessage(validateGroups, fieldMetadata);
            }
        }

        // 接口的响应参数信息
        FieldMetadata responseFieldDescriptions = resourceDefinition.getResponseFieldDescriptions();
        fillDetailMessage(validateGroups, responseFieldDescriptions);

        return resourceDefinition;
    }

    /**
     * 填充字段里详细的提示信息
     *
     * @author fengshuonan
     * @date 2021/1/16 18:00
     */
    public static void fillDetailMessage(Set<String> validateGroups, FieldMetadata fieldMetadata) {
        if (validateGroups == null || validateGroups.isEmpty()) {
            return;
        }

        if (fieldMetadata == null) {
            return;
        }
        StringBuilder finalValidateMessages = new StringBuilder();
        Map<String, Set<String>> groupAnnotations = fieldMetadata.getGroupValidationMessage();
        if (groupAnnotations != null) {
            for (String validateGroup : validateGroups) {
                Set<String> validateMessage = groupAnnotations.get(validateGroup);
                if (validateMessage != null && !validateMessage.isEmpty()) {
                    finalValidateMessages.append(StrUtil.join("，", validateMessage));
                }
            }
        }
        fieldMetadata.setValidationMessages(finalValidateMessages.toString());

        // 递归填充子类型的详细提示信息
        if (fieldMetadata.getGenericFieldMetadata() != null && !fieldMetadata.getGenericFieldMetadata().isEmpty()) {
            for (FieldMetadata metadata : fieldMetadata.getGenericFieldMetadata()) {
                fillDetailMessage(validateGroups, metadata);
            }
        }
    }

    /**
     * 将资源的集合整理成一个map，key是url，value是ResourceDefinition
     *
     * @author fengshuonan
     * @date 2021/5/17 16:21
     */
    public static Map<String, ResourceDefinition> orderedResourceDefinition(List<ResourceDefinition> sysResourceList) {

        HashMap<String, ResourceDefinition> result = new HashMap<>();

        if (ObjectUtil.isEmpty(sysResourceList)) {
            return result;
        }

        for (ResourceDefinition sysResource : sysResourceList) {
            String url = sysResource.getUrl();
            result.put(url, sysResource);
        }

        return result;
    }

}
