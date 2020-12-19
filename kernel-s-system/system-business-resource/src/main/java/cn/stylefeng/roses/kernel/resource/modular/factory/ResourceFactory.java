package cn.stylefeng.roses.kernel.resource.modular.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.BeanUtils;

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
        BeanUtils.copyProperties(resourceDefinition, resource);
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
            resourceDefinition.setResponseFieldDescriptions(JSON.parseObject(sysResource.getResponseFieldDescriptions(), Set.class, Feature.SupportAutoType));
        }

        return resourceDefinition;
    }

}
