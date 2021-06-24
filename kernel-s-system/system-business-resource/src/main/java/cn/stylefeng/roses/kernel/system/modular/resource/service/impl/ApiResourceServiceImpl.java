package cn.stylefeng.roses.kernel.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.FieldMetadata;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.resource.ApiResourceExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceFieldRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiGroup;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResourceField;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.factory.ResourceFactory;
import cn.stylefeng.roses.kernel.system.modular.resource.mapper.ApiResourceMapper;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiGroupService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceFieldService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 接口信息业务实现层
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Service
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource> implements ApiResourceService {

    @Autowired
    private ApiGroupService apiGroupService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private ApiResourceFieldService apiResourceFieldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ApiResourceRequest apiResourceRequest) {
        // 如果选择的不是分组，就不允许操作
        ApiGroup apiGroup = this.apiGroupService.getById(apiResourceRequest.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiResourceExceptionEnum.OPERATIONS_RESOURCE_NODESNOT_ALLOWED);
        }

        List<SysResource> resourceList = new ArrayList<>();

        // 资源查询条件
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 如果是控制器名称，则查询模块下的所有资源
        if (!apiResourceRequest.getResourceCode().contains("$")) {
            sysResourceLambdaQueryWrapper.eq(SysResource::getModularCode, apiResourceRequest.getResourceCode());
            sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());
            List<SysResource> sysResourceList = sysResourceService.list(sysResourceLambdaQueryWrapper);
            resourceList.addAll(sysResourceList);
        } else {

            // 根据资源CODE查询资源信息
            sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, apiResourceRequest.getResourceCode());
            SysResource sysResources = sysResourceService.getOne(sysResourceLambdaQueryWrapper);

            // 如果是视图就报错
            if (YesOrNotEnum.Y.getCode().equals(sysResources.getViewFlag())) {
                throw new SystemModularException(ApiResourceExceptionEnum.ADDING_VIEW_RESOURCES_NOT_ALLOWED);
            }

            resourceList.add(sysResources);
        }

        // 默认生成排序
        BigDecimal index = BigDecimal.ZERO;

        // 查询当前分组下面的排序最大值
        LambdaQueryWrapper<ApiResource> apiResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceLambdaQueryWrapper.eq(ApiResource::getGroupId, apiResourceRequest.getGroupId());
        apiResourceLambdaQueryWrapper.orderByDesc(ApiResource::getResourceSort);
        apiResourceLambdaQueryWrapper.last("LIMIT 1");
        ApiResource resource = this.getOne(apiResourceLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(resource)) {
            index = resource.getResourceSort();
        }

        // 处理所有资源
        for (SysResource sysResource : resourceList) {
            // 转换对象
            ApiResource apiResource = BeanUtil.toBean(apiResourceRequest, ApiResource.class);

            // 设置请求方式
            apiResource.setRequestMethod(sysResource.getHttpMethod());

            // 设置资源名称
            apiResource.setApiAlias(sysResource.getResourceName());

            // 设置资源code
            apiResource.setResourceCode(sysResource.getResourceCode());

            // 设置排序
            if (ObjectUtil.isEmpty(apiResourceRequest.getResourceSort())) {
                index = index.add(BigDecimal.ONE);
                apiResource.setResourceSort(index);
            }

            // 保存数据
            this.save(apiResource);

            // 获取参数
            ResourceDefinition fillResourceDetail = ResourceFactory.fillResourceDetail(ResourceFactory.createResourceDefinition(sysResource));

            // 所有字段集合
            List<ApiResourceField> apiResourceFieldList = new ArrayList<>();

            // 处理所有请求字段
            if (ObjectUtil.isNotEmpty(fillResourceDetail.getParamFieldDescriptions())) {
                for (FieldMetadata fieldMetadata : fillResourceDetail.getParamFieldDescriptions()) {
                    ApiResourceField conversion = this.conversion(sysResource, apiResource.getApiResourceId(), fieldMetadata);
                    conversion.setFieldLocation("request");
                    apiResourceFieldList.add(conversion);
                }
            }

            // 处理所有响应字段
            if (ObjectUtil.isNotEmpty(fillResourceDetail.getResponseFieldDescriptions())) {
                for (FieldMetadata fieldDescription : fillResourceDetail.getResponseFieldDescriptions()) {
                    ApiResourceField conversion = this.conversion(sysResource, apiResource.getApiResourceId(), fieldDescription);
                    conversion.setFieldLocation("response");
                    apiResourceFieldList.add(conversion);
                }
            }

            // 保存
            if (ObjectUtil.isNotEmpty(apiResourceFieldList)) {
                this.apiResourceFieldService.saveBatch(apiResourceFieldList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);
        this.removeById(apiResource.getApiResourceId());

        // 删除所有字段信息
        LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceFieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        this.apiResourceFieldService.remove(apiResourceFieldLambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(ApiResourceRequest apiResourceRequest) {

        // 查询旧数据
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);

        // 如果选择的不是分组，就不允许操作
        ApiGroup apiGroup = this.apiGroupService.getById(apiResource.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiResourceExceptionEnum.OPERATIONS_RESOURCE_NODESNOT_ALLOWED);
        }

        // 更新接口资源数据
        BeanUtil.copyProperties(apiResourceRequest, apiResource);

        // 去掉排序字段
        this.updateById(apiResource);

        // 删除所有的字段数据
        LambdaQueryWrapper<ApiResourceField> fieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        this.apiResourceFieldService.remove(fieldLambdaQueryWrapper);

        // 添加新的字段信息
        List<ApiResourceFieldRequest> apiResourceFieldRequestList = apiResourceRequest.getApiResourceFieldRequestList();
        if (ObjectUtil.isNotEmpty(apiResourceFieldRequestList)) {
            List<ApiResourceField> apiResourceFields = apiResourceFieldRequestList.stream().map(item -> {
                ApiResourceField apiResourceField = BeanUtil.toBean(item, ApiResourceField.class);
                apiResourceField.setApiResourceId(apiResource.getApiResourceId());
                return apiResourceField;
            }).collect(Collectors.toList());
            this.apiResourceFieldService.saveBatch(apiResourceFields);
        }
    }

    @Override
    public ApiResource detail(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.queryApiResource(apiResourceRequest);

        // 查询所有字段信息
        LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiResourceFieldLambdaQueryWrapper.eq(ApiResourceField::getApiResourceId, apiResource.getApiResourceId());
        List<ApiResourceField> apiResourceFields = this.apiResourceFieldService.list(apiResourceFieldLambdaQueryWrapper);
        // 过滤创建时间和创建人
        apiResourceFields.removeIf(resourceField -> "createTime".equalsIgnoreCase(resourceField.getFieldCode()) || "createUser".equalsIgnoreCase(resourceField.getFieldCode()) || "updateTime".equalsIgnoreCase(resourceField.getFieldCode()) || "updateUser".equalsIgnoreCase(resourceField.getFieldCode()));

        List<ApiResourceFieldRequest> apiResourceFieldList = new ArrayList<>();
        for (ApiResourceField apiResourceField : apiResourceFields) {
            // 转换为前端对象
            ApiResourceFieldRequest apiResourceFieldRequest = BeanUtil.toBean(apiResourceField, ApiResourceFieldRequest.class);
            apiResourceFieldRequest.setChildren(JSON.parseObject(apiResourceField.getFieldSubInfo(), Set.class, Feature.SupportAutoType));
            apiResourceFieldList.add(apiResourceFieldRequest);
        }

        apiResource.setApiResourceFieldList(apiResourceFieldList);

        return apiResource;
    }

    @Override
    public PageResult<ApiResource> findPage(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> wrapper = createWrapper(apiResourceRequest);
        Page<ApiResource> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public ApiResource record(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = BeanUtil.toBean(apiResourceRequest, ApiResource.class);

        String resultBody = null;

        try {
            // 请求对象
            HttpRequest httpRequest;

            // 判断请求方式
            if (RequestMethod.GET.toString().equalsIgnoreCase(apiResourceRequest.getRequestMethod())) {
                httpRequest = HttpRequest.get(apiResourceRequest.getRequestUrl());
                // 处理参数
                JSONObject jsonObject = JSONObject.parseObject(apiResourceRequest.getLastRequestContent());
                for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                    httpRequest.form(stringObjectEntry.getKey(), stringObjectEntry.getValue());
                }
            } else {
                httpRequest = HttpRequest.post(apiResourceRequest.getRequestUrl()).body(apiResourceRequest.getLastRequestContent());
            }

            // 请求头处理
            if (ObjectUtil.isNotEmpty(apiResourceRequest.getLastRequestHeader())) {
                for (Map.Entry<String, String> stringStringEntry : apiResourceRequest.getLastRequestHeader().entrySet()) {
                    String[] values = stringStringEntry.getValue().split(SymbolConstant.COMMA);
                    for (String value : values) {
                        httpRequest.header(stringStringEntry.getKey(), value, false);
                    }
                }
            }

            // 发送请求
            resultBody = httpRequest.timeout(15000).execute().body();

        } catch (Exception e) {
            resultBody = ExceptionUtil.stacktraceToString(e, 10000);
        } finally {
            // 请求头处理
            if (ObjectUtil.isNotEmpty(apiResourceRequest.getLastRequestHeader())) {
                apiResource.setLastRequestHeader(JSON.toJSONString(apiResourceRequest.getLastRequestHeader()));
            }
            apiResource.setLastResponseContent(resultBody);
            this.updateById(apiResource);
        }
        return apiResource;
    }

    @Override
    public List<ApiResourceFieldRequest> allField(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getResourceCode, apiResourceRequest.getResourceCode());
        SysResource sysResources = sysResourceService.getOne(sysResourceLambdaQueryWrapper);

        // 获取参数
        ResourceDefinition fillResourceDetail = ResourceFactory.fillResourceDetail(ResourceFactory.createResourceDefinition(sysResources));

        // 把所有字段加进去
        List<ApiResourceFieldRequest> apiResourceFieldList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(fillResourceDetail.getParamFieldDescriptions())) {
            // 处理所有请求字段
            for (FieldMetadata fieldMetadata : fillResourceDetail.getParamFieldDescriptions()) {
                ApiResourceField conversion = this.conversion(sysResources, null, fieldMetadata);
                conversion.setFieldLocation("request");

                // 转换为前端对象
                ApiResourceFieldRequest apiResourceFieldRequest = BeanUtil.toBean(conversion, ApiResourceFieldRequest.class);
                apiResourceFieldRequest.setChildren(JSON.parseObject(conversion.getFieldSubInfo(), Set.class, Feature.SupportAutoType));
                apiResourceFieldList.add(apiResourceFieldRequest);
            }

            // 处理所有响应字段
            for (FieldMetadata fieldDescription : fillResourceDetail.getResponseFieldDescriptions()) {
                ApiResourceField conversion = this.conversion(sysResources, null, fieldDescription);
                conversion.setFieldLocation("response");

                // 转换为前端对象
                ApiResourceFieldRequest apiResourceFieldRequest = BeanUtil.toBean(conversion, ApiResourceFieldRequest.class);
                apiResourceFieldRequest.setChildren(JSON.parseObject(conversion.getFieldSubInfo(), Set.class, Feature.SupportAutoType));
                apiResourceFieldList.add(apiResourceFieldRequest);
            }
        }
        return apiResourceFieldList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResource reset(ApiResourceRequest apiResourceRequest) {

        ApiResource oldApiResource = this.getById(apiResourceRequest.getApiResourceId());

        // 删除原有的
        this.del(apiResourceRequest);

        // 新增一个新的
        apiResourceRequest.setResourceSort(oldApiResource.getResourceSort());
        this.add(apiResourceRequest);

        // 查询并返回结果
        return this.getById(apiResourceRequest.getApiResourceId());
    }

    @Override
    public List<ApiResource> dataList(ApiGroupRequest apiGroupRequest) {
        return this.baseMapper.dataList(apiGroupRequest);
    }

    @Override
    public List<ApiResource> findList(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> wrapper = this.createWrapper(apiResourceRequest);
        return this.list(wrapper);
    }

    /**
     * 获取信息
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private ApiResource queryApiResource(ApiResourceRequest apiResourceRequest) {
        ApiResource apiResource = this.getById(apiResourceRequest.getApiResourceId());
        if (ObjectUtil.isEmpty(apiResource)) {
            throw new SystemModularException(ApiResourceExceptionEnum.APIRESOURCE_NOT_EXISTED);
        }
        return apiResource;
    }

    /**
     * 创建查询wrapper
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private LambdaQueryWrapper<ApiResource> createWrapper(ApiResourceRequest apiResourceRequest) {
        LambdaQueryWrapper<ApiResource> queryWrapper = new LambdaQueryWrapper<>();

        Long apiResourceId = apiResourceRequest.getApiResourceId();
        Long groupId = apiResourceRequest.getGroupId();
        String apiAlias = apiResourceRequest.getApiAlias();
        String resourceCode = apiResourceRequest.getResourceCode();
        String lastRequestContent = apiResourceRequest.getLastRequestContent();
        String lastResponseContent = apiResourceRequest.getLastResponseContent();
        BigDecimal resourceSort = apiResourceRequest.getResourceSort();
        String createTime = apiResourceRequest.getCreateTime();
        Long createUser = apiResourceRequest.getCreateUser();
        String updateTime = apiResourceRequest.getUpdateTime();
        Long updateUser = apiResourceRequest.getUpdateUser();

        queryWrapper.eq(ObjectUtil.isNotNull(apiResourceId), ApiResource::getApiResourceId, apiResourceId);
        queryWrapper.eq(ObjectUtil.isNotNull(groupId), ApiResource::getGroupId, groupId);
        queryWrapper.like(ObjectUtil.isNotEmpty(apiAlias), ApiResource::getApiAlias, apiAlias);
        queryWrapper.like(ObjectUtil.isNotEmpty(resourceCode), ApiResource::getResourceCode, resourceCode);
        queryWrapper.like(ObjectUtil.isNotEmpty(lastRequestContent), ApiResource::getLastRequestContent, lastRequestContent);
        queryWrapper.like(ObjectUtil.isNotEmpty(lastResponseContent), ApiResource::getLastResponseContent, lastResponseContent);
        queryWrapper.eq(ObjectUtil.isNotNull(resourceSort), ApiResource::getResourceSort, resourceSort);
        queryWrapper.eq(ObjectUtil.isNotNull(createTime), ApiResource::getCreateTime, createTime);
        queryWrapper.eq(ObjectUtil.isNotNull(createUser), ApiResource::getCreateUser, createUser);
        queryWrapper.eq(ObjectUtil.isNotNull(updateTime), ApiResource::getUpdateTime, updateTime);
        queryWrapper.eq(ObjectUtil.isNotNull(updateUser), ApiResource::getUpdateUser, updateUser);

        return queryWrapper;
    }

    /**
     * 转换字段信息
     *
     * @return {@link ApiResourceField}
     * @author majianguo
     * @date 2021/5/22 下午2:44
     **/
    private ApiResourceField conversion(SysResource sysResource, Long apiResourceId, FieldMetadata fieldMetadata) {
        ApiResourceField item = new ApiResourceField();
        item.setApiResourceId(apiResourceId);
        item.setFieldCode(fieldMetadata.getFieldName());
        item.setFieldName(fieldMetadata.getChineseName());
        if (fieldMetadata.getFieldClassPath().contains("java.util")) {
            item.setFieldType("list");
        } else if (fieldMetadata.getFieldClassPath().contains("java.io")) {
            item.setFieldType("file");
        } else if (fieldMetadata.getFieldClassPath().contains("java.")) {
            item.setFieldType("string");
        } else {
            item.setFieldType("object");
        }

        // 是否必填
        item.setFieldRequired(this.isRequired(sysResource, fieldMetadata) ? YesOrNotEnum.Y.getCode() : YesOrNotEnum.N.getCode());

        // 字段校验消息
        item.setFieldValidationMsg(fieldMetadata.getValidationMessages());

        // 如果还有子字段，则处理字段
        Set<FieldMetadata> genericFieldMetadata = fieldMetadata.getGenericFieldMetadata();
        if (ObjectUtil.isNotEmpty(genericFieldMetadata)) {
            Set<ApiResourceField> apiResourceFieldSet = new HashSet<>();
            for (FieldMetadata genericFieldMetadatum : genericFieldMetadata) {
                ApiResourceField conversion = this.conversion(sysResource, apiResourceId, genericFieldMetadatum);
                // 如果是list设置嵌套类型里面的全是非必填
                if ("list".equals(item.getFieldType())) {
                    conversion.setFieldRequired(YesOrNotEnum.N.getCode());
                }
                apiResourceFieldSet.add(conversion);
            }
            item.setFieldSubInfo(JSON.toJSONString(apiResourceFieldSet, SerializerFeature.WriteClassName));
        }

        return item;
    }

    /**
     * 是否必填
     *
     * @author majianguo
     * @date 2021/5/27 下午3:15
     **/
    private boolean isRequired(SysResource sysResource, FieldMetadata fieldMetadata) {
        Set<String> annotations = fieldMetadata.getAnnotations();
        if (ObjectUtil.isNotEmpty(annotations)) {
            // 资源校验注解
            Set<String> validateGroups = JSON.parseObject(sysResource.getValidateGroups(), Set.class, Feature.SupportAutoType);
            if (ObjectUtil.isNotEmpty(validateGroups)) {
                // 注解分组
                Map<String, Set<String>> groupValidationMessage = fieldMetadata.getGroupValidationMessage();
                for (String annotationName : annotations) {
                    if ("NotBlank".equalsIgnoreCase(annotationName) || "NotNull".equalsIgnoreCase(annotationName)) {
                        // 查询注解的所有分组，判断当前资源是否符合
                        Set<String> annotationGroups = groupValidationMessage.keySet();
                        for (String group : annotationGroups) {
                            // 当前分组在资源校验注解中，则设置必填
                            if (validateGroups.contains(group)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}