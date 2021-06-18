package cn.stylefeng.roses.kernel.system.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.resource.ApiGroupExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupTreeWrapper;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.TreeSortRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiGroup;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResourceField;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.enums.NodeEnums;
import cn.stylefeng.roses.kernel.system.modular.resource.enums.NodeTypeEnums;
import cn.stylefeng.roses.kernel.system.modular.resource.mapper.ApiGroupMapper;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiGroupService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceFieldService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceService;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 接口分组业务实现层
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@Service
public class ApiGroupServiceImpl extends ServiceImpl<ApiGroupMapper, ApiGroup> implements ApiGroupService {

    @Autowired
    private ApiResourceService apiResourceService;

    @Autowired
    private ApiResourceFieldService apiResourceFieldService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiGroup add(ApiGroupRequest apiGroupRequest) {
        ApiGroup apiGroup = new ApiGroup();
        BeanUtil.copyProperties(apiGroupRequest, apiGroup);
        this.setPids(apiGroup);
        this.save(apiGroup);
        return apiGroup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(ApiGroupRequest apiGroupRequest) {

        // 根节点不允许删除
        if (NodeEnums.ROOT_NODE.getId().equals(apiGroupRequest.getGroupId())) {
            throw new SystemModularException(ApiGroupExceptionEnum.ROOT_PROHIBIT_OPERATION);
        }

        ApiGroup apiGroup = this.queryApiGroup(apiGroupRequest);
        this.removeById(apiGroup.getGroupId());

        // 查询所有子节点数据
        LambdaQueryWrapper<ApiGroup> apiGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apiGroupLambdaQueryWrapper.like(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        List<ApiGroup> apiGroups = this.list(apiGroupLambdaQueryWrapper);

        // 把分组本身加入进去
        apiGroups.add(apiGroup);

        // 获取所有分组ID
        Set<Long> apiGroupIdSet = null;
        if (ObjectUtil.isNotEmpty(apiGroups)) {
            apiGroupIdSet = apiGroups.stream().map(ApiGroup::getGroupId).collect(Collectors.toSet());
        }

        if (ObjectUtil.isNotEmpty(apiGroupIdSet)) {
            // 查询所有分组下的接口
            LambdaQueryWrapper<ApiResource> apiResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apiResourceLambdaQueryWrapper.in(ApiResource::getGroupId, apiGroupIdSet);
            List<ApiResource> apiResourceList = this.apiResourceService.list(apiResourceLambdaQueryWrapper);

            if (ObjectUtil.isNotEmpty(apiResourceList)) {
                Set<Long> apiResourceSet = apiResourceList.stream().map(ApiResource::getApiResourceId).collect(Collectors.toSet());
                // 删除所有接口的字段
                LambdaQueryWrapper<ApiResourceField> apiResourceFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
                apiResourceFieldLambdaQueryWrapper.in(ApiResourceField::getApiResourceId, apiResourceSet);
                this.apiResourceFieldService.remove(apiResourceFieldLambdaQueryWrapper);
            }

            // 执行删除所有分组下的接口
            this.apiResourceService.remove(apiResourceLambdaQueryWrapper);
        }

        // 删除所有子节点数据
        this.remove(apiGroupLambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(ApiGroupRequest apiGroupRequest) {

        // 查询数据库中该条数据的信息
        ApiGroup oldApiGroup = this.queryApiGroup(apiGroupRequest);

        // 父节点不能是自己
        if (apiGroupRequest.getGroupPid().equals(oldApiGroup.getGroupId())) {
            throw new SystemModularException(ApiGroupExceptionEnum.PARENT_NODE_ITSELF);
        }

        // 父节点不能是自己的子节点
        LambdaQueryWrapper<ApiGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + oldApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        List<ApiGroup> apiGroups = this.list(lambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(apiGroups)) {
            if (apiGroups.stream().anyMatch(item -> item.getGroupId().equals(apiGroupRequest.getGroupPid()))) {
                throw new SystemModularException(ApiGroupExceptionEnum.PARENT_NODE_ITSELF);
            }
        }

        // 转换请求数据
        ApiGroup newApiGroup = BeanUtil.toBean(apiGroupRequest, ApiGroup.class);

        // 判断是否变换了父ID
        if (!newApiGroup.getGroupPid().equals(oldApiGroup.getGroupPid())) {
            // 处理pids
            this.setPids(newApiGroup);

            // 更新所有子部门的pids
            this.updatePids(newApiGroup, oldApiGroup);
        }

        this.updateById(newApiGroup);
    }

    @Override
    public ApiGroup detail(ApiGroupRequest apiGroupRequest) {
        return this.queryApiGroup(apiGroupRequest);
    }

    @Override
    public PageResult<ApiGroup> findPage(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> wrapper = createWrapper(apiGroupRequest);
        Page<ApiGroup> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<ApiGroupTreeWrapper> tree(ApiGroupRequest apiGroupRequest) {
        return this.createTree(this.peersTree(apiGroupRequest));
    }

    @Override
    public List<ApiGroupTreeWrapper> peersTree(ApiGroupRequest apiGroupRequest) {
        // 结果
        List<ApiGroupTreeWrapper> allApiGroupTreeWrapperList = new ArrayList<>();

        //　查询所有资源信息
        Map<String, SysResource> stringSysResourceMap = new HashMap<>();
        LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysResourceLambdaQueryWrapper.eq(SysResource::getViewFlag, YesOrNotEnum.N.getCode());

        List<SysResource> sysResources = this.sysResourceService.list(sysResourceLambdaQueryWrapper);
        for (SysResource sysResource : sysResources) {
            stringSysResourceMap.put(sysResource.getResourceCode(), sysResource);
        }

        // 查询所有分组
        LambdaQueryWrapper<ApiGroup> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(apiGroupRequest.getGroupId())) {
            wrapper.notLike(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroupRequest.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            wrapper.ne(ApiGroup::getGroupId, apiGroupRequest.getGroupId());
        }

        List<ApiGroup> apiGroups = this.list(wrapper);

        if (ObjectUtil.isNotEmpty(apiGroups)) {
            for (ApiGroup apiGroup : apiGroups) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiGroup.getGroupId());
                item.setPid(apiGroup.getGroupPid());
                item.setName(apiGroup.getGroupName());
                item.setType(NodeTypeEnums.LEAF_NODE.getType());
                item.setSort(apiGroup.getGroupSort());
                item.setData(apiGroup);
                item.setSlotsValue();
                allApiGroupTreeWrapperList.add(item);
            }
        }

        // 查询资源
        List<ApiResource> apiResourceList = this.apiResourceService.dataList(apiGroupRequest);
        if (ObjectUtil.isNotEmpty(apiResourceList)) {
            for (ApiResource apiResource : apiResourceList) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiResource.getApiResourceId());
                item.setPid(apiResource.getGroupId());
                item.setName(apiResource.getApiAlias());
                item.setType(NodeTypeEnums.DATA_NODE.getType());
                item.setSort(apiResource.getResourceSort());
                item.setData(apiResource);
                item.setSlotsValue();
                SysResource sysResource = stringSysResourceMap.get(apiResource.getResourceCode());
                if (ObjectUtil.isNotEmpty(sysResource)) {
                    item.setUrl(sysResource.getUrl());
                }
                allApiGroupTreeWrapperList.add(item);
            }
        }
        return allApiGroupTreeWrapperList;
    }

    @Override
    public List<ApiGroupTreeWrapper> groupTree(ApiGroupRequest apiGroupRequest) {
        // 结果
        List<ApiGroupTreeWrapper> apiGroupTreeWrapperList = new ArrayList<>();

        // 查询所有分组
        LambdaQueryWrapper<ApiGroup> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(apiGroupRequest.getGroupId())) {
            wrapper.notLike(ApiGroup::getGroupPids, SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroupRequest.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            wrapper.ne(ApiGroup::getGroupId, apiGroupRequest.getGroupId());
        }
        List<ApiGroup> apiGroups = this.list(wrapper);

        if (ObjectUtil.isNotEmpty(apiGroups)) {
            for (ApiGroup apiGroup : apiGroups) {
                ApiGroupTreeWrapper item = new ApiGroupTreeWrapper();
                item.setId(apiGroup.getGroupId());
                item.setPid(apiGroup.getGroupPid());
                item.setName(apiGroup.getGroupName());
                item.setType(NodeTypeEnums.LEAF_NODE.getType());
                item.setSort(apiGroup.getGroupSort());
                item.setData(apiGroup);
                item.setSlotsValue();
                apiGroupTreeWrapperList.add(item);
            }
        }

        return this.createTree(apiGroupTreeWrapperList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTreeSort(List<TreeSortRequest> treeSortRequestList) {

        // 所有叶子节点
        List<ApiGroup> apiGroupList = new ArrayList<>();

        // 所有数据节点
        List<ApiResource> apiResourceList = new ArrayList<>();

        // 处理数据
        for (TreeSortRequest treeSortRequest : treeSortRequestList) {
            if (NodeTypeEnums.LEAF_NODE.getType().equals(treeSortRequest.getNodeType())) {
                ApiGroup item = new ApiGroup();
                item.setGroupId(treeSortRequest.getNodeId());
                item.setGroupPid(treeSortRequest.getNodePid());
                item.setGroupSort(treeSortRequest.getNodeSort());
                apiGroupList.add(item);
            } else {
                ApiResource item = new ApiResource();
                item.setApiResourceId(treeSortRequest.getNodeId());
                item.setGroupId(treeSortRequest.getNodePid());
                item.setResourceSort(treeSortRequest.getNodeSort());
                apiResourceList.add(item);
            }
        }

        // 处理所有叶子节点
        if (ObjectUtil.isNotEmpty(apiGroupList)) {
            for (ApiGroup apiGroup : apiGroupList) {
                ApiGroup oldApiGroup = this.getById(apiGroup.getGroupId());
                this.setPids(apiGroup);
                this.updatePids(apiGroup, oldApiGroup);
            }
            this.updateBatchById(apiGroupList);
        }

        // 处理所有数据节点
        if (ObjectUtil.isNotEmpty(apiResourceList)) {
            this.apiResourceService.updateBatchById(apiResourceList);
        }
    }

    @Override
    public List<ApiGroup> findList(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> wrapper = this.createWrapper(apiGroupRequest);
        List<ApiGroup> apiGroupList = this.list(wrapper);
        return apiGroupList;
    }

    /**
     * 获取信息
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private ApiGroup queryApiGroup(ApiGroupRequest apiGroupRequest) {
        ApiGroup apiGroup = this.getById(apiGroupRequest.getGroupId());
        if (ObjectUtil.isEmpty(apiGroup)) {
            throw new SystemModularException(ApiGroupExceptionEnum.APIGROUP_NOT_EXISTED);
        }
        return apiGroup;
    }

    /**
     * 创建查询wrapper
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    private LambdaQueryWrapper<ApiGroup> createWrapper(ApiGroupRequest apiGroupRequest) {
        LambdaQueryWrapper<ApiGroup> queryWrapper = new LambdaQueryWrapper<>();

        Long groupId = apiGroupRequest.getGroupId();
        String groupName = apiGroupRequest.getGroupName();
        Long groupPid = apiGroupRequest.getGroupPid();
        String groupPids = apiGroupRequest.getGroupPids();

        queryWrapper.eq(ObjectUtil.isNotNull(groupId), ApiGroup::getGroupId, groupId);
        queryWrapper.like(ObjectUtil.isNotEmpty(groupName), ApiGroup::getGroupName, groupName);
        queryWrapper.eq(ObjectUtil.isNotNull(groupPid), ApiGroup::getGroupPid, groupPid);
        queryWrapper.like(ObjectUtil.isNotEmpty(groupPids), ApiGroup::getGroupPids, groupPids);

        return queryWrapper;
    }

    /**
     * 更新父ID集合
     *
     * @author majianguo
     * @date 2021/5/22 上午9:59
     **/
    private void setPids(ApiGroup apiGroup) {
        // 更新本节点的pids
        if (RuleConstants.TREE_ROOT_ID.equals(apiGroup.getGroupPid())) {
            // 顶级节点
            apiGroup.setGroupPids(SymbolConstant.LEFT_SQUARE_BRACKETS + RuleConstants.TREE_ROOT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        } else {
            ApiGroup pApiGroup = this.getById(apiGroup.getGroupPid());
            apiGroup.setGroupPids(pApiGroup.getGroupPids() + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + pApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
        }
    }

    /**
     * 修改pids
     *
     * @author majianguo
     * @date 2021/5/22 上午9:59
     **/
    private void updatePids(ApiGroup apiGroup, ApiGroup oldApiGroup) {
        String oldPids = oldApiGroup.getGroupPids();
        oldPids = oldPids + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + oldApiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS;
        ParentIdsUpdateRequest parentIdsUpdateRequest = createParenIdsUpdateRequest(apiGroup.getGroupPids() + SymbolConstant.COMMA + SymbolConstant.LEFT_SQUARE_BRACKETS + apiGroup.getGroupId() + SymbolConstant.RIGHT_SQUARE_BRACKETS, oldPids);
        this.baseMapper.updateSubPids(parentIdsUpdateRequest);
    }

    /**
     * 批量修改pids的请求
     *
     * @author fengshuonan
     * @date 2020/12/26 12:19
     */
    private static ParentIdsUpdateRequest createParenIdsUpdateRequest(String newParentIds, String oldParentIds) {
        ParentIdsUpdateRequest parentIdsUpdateRequest = new ParentIdsUpdateRequest();
        parentIdsUpdateRequest.setNewParentIds(newParentIds);
        parentIdsUpdateRequest.setOldParentIds(oldParentIds);
        parentIdsUpdateRequest.setUpdateTime(new Date());
        parentIdsUpdateRequest.setUpdateUser(LoginContext.me().getLoginUser().getUserId());
        return parentIdsUpdateRequest;
    }

    /**
     * 创建组装树结构
     *
     * @author majianguo
     * @date 2021/5/22 上午11:08
     **/
    private List<ApiGroupTreeWrapper> createTree(List<ApiGroupTreeWrapper> orgTrees) {
        // 根节点
        ApiGroupTreeWrapper root = null;

        // 组装MAP
        Map<String, ApiGroupTreeWrapper> dataMap = new HashMap<>(orgTrees.size());
        for (ApiGroupTreeWrapper orgTree : orgTrees) {
            if (orgTree.getNodeParentId().equals(RuleConstants.TREE_ROOT_ID.toString())) {
                root = orgTree;
            }
            dataMap.put(orgTree.getNodeId(), orgTree);
        }

        // 组装树形结构
        orgTrees.parallelStream().forEach(item -> {
            // 是root节点就跳过
            if (!RuleConstants.TREE_ROOT_ID.toString().equals(item.getNodeParentId())) {
                // 排序字段为空则填写一个默认值
                if (ObjectUtil.isEmpty(item.getSort())) {
                    item.setSort(BigDecimal.valueOf(9999));
                }
                ApiGroupTreeWrapper orgTreeWrapper = dataMap.get(item.getNodeParentId());
                // 找到父节点再操作，找不到父节点，则丢掉该节点信息
                if (ObjectUtil.isNotEmpty(orgTreeWrapper)) {
                    dataMap.get(item.getNodeParentId()).getChildren().add(item);
                }
            }
        });

        // 对数据进行排序
        root.sortChildren();
        return Collections.singletonList(root);
    }
}