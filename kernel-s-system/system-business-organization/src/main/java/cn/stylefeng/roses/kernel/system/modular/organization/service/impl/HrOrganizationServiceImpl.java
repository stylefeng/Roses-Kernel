package cn.stylefeng.roses.kernel.system.modular.organization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.db.api.context.DbOperatorContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.rule.tree.factory.node.DefaultTreeNode;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.api.RoleDataScopeServiceApi;
import cn.stylefeng.roses.kernel.system.api.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.api.UserOrgServiceApi;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.OrganizationExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.layui.LayuiOrganizationTreeNode;
import cn.stylefeng.roses.kernel.system.api.util.DataScopeUtil;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.modular.organization.factory.AntdvOrganizationFactory;
import cn.stylefeng.roses.kernel.system.modular.organization.factory.LayuiOrganizationFactory;
import cn.stylefeng.roses.kernel.system.modular.organization.mapper.HrOrganizationMapper;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 组织架构管理
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Service
public class HrOrganizationServiceImpl extends ServiceImpl<HrOrganizationMapper, HrOrganization> implements HrOrganizationService {

    @Resource
    private UserOrgServiceApi userOrgServiceApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private RoleDataScopeServiceApi roleDataScopeServiceApi;

    @Override
    public void add(HrOrganizationRequest hrOrganizationRequest) {

        // 获取父id
        Long pid = hrOrganizationRequest.getOrgParentId();

        // 校验数据范围
        DataScopeUtil.quickValidateDataScope(pid);

        HrOrganization hrOrganization = new HrOrganization();
        BeanUtil.copyProperties(hrOrganizationRequest, hrOrganization);

        // 填充parentIds
        this.fillParentIds(hrOrganization);

        // 设置状态为启用，未删除状态
        hrOrganization.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(hrOrganization);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(HrOrganizationRequest hrOrganizationRequest) {

        HrOrganization hrOrganization = this.queryOrganization(hrOrganizationRequest);
        Long organizationId = hrOrganization.getOrgId();

        // 校验数据范围
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 该机构下有员工，则不能删
        Boolean userOrgFlag = userOrgServiceApi.getUserOrgFlag(organizationId, null);
        if (userOrgFlag) {
            throw new SystemModularException(OrganizationExceptionEnum.DELETE_ORGANIZATION_ERROR);
        }

        // 级联删除子节点，逻辑删除
        Set<Long> childIdList = DbOperatorContext.me().findSubListByParentId("hr_organization", "org_pids", "org_id", organizationId);
        childIdList.add(organizationId);

        LambdaUpdateWrapper<HrOrganization> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(HrOrganization::getOrgId, childIdList).set(HrOrganization::getDelFlag, YesOrNotEnum.Y.getCode());
        this.update(updateWrapper);

        // 删除角色对应的组织架构数据范围
        roleDataScopeServiceApi.delByOrgIds(childIdList);

        // 删除用户对应的组织架构数据范围
        userServiceApi.deleteUserDataScopeListByOrgIdList(childIdList);
    }

    @Override
    public void edit(HrOrganizationRequest hrOrganizationRequest) {

        HrOrganization hrOrganization = this.queryOrganization(hrOrganizationRequest);
        Long id = hrOrganization.getOrgId();

        // 校验数据范围
        DataScopeUtil.quickValidateDataScope(id);

        BeanUtil.copyProperties(hrOrganizationRequest, hrOrganization);

        // 填充parentIds
        this.fillParentIds(hrOrganization);

        // 不能修改状态，用修改状态接口修改状态
        hrOrganization.setStatusFlag(null);

        // 更新这条记录
        this.updateById(hrOrganization);
    }

    @Override
    public void updateStatus(HrOrganizationRequest hrOrganizationRequest) {
        HrOrganization hrOrganization = this.queryOrganization(hrOrganizationRequest);
        hrOrganization.setStatusFlag(hrOrganizationRequest.getStatusFlag());
        this.updateById(hrOrganization);
    }

    @Override
    public HrOrganization detail(HrOrganizationRequest hrOrganizationRequest) {
        return this.getOne(this.createWrapper(hrOrganizationRequest), false);
    }

    @Override
    public PageResult<HrOrganization> findPage(HrOrganizationRequest hrOrganizationRequest) {
        LambdaQueryWrapper<HrOrganization> wrapper = this.createWrapper(hrOrganizationRequest);
        Page<HrOrganization> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<HrOrganization> findList(HrOrganizationRequest hrOrganizationRequest) {
        LambdaQueryWrapper<HrOrganization> wrapper = this.createWrapper(hrOrganizationRequest);
        return this.list(wrapper);
    }

    @Override
    public List<DefaultTreeNode> treeAntdv(HrOrganizationRequest hrOrganizationRequest) {
        // 定义返回结果
        List<DefaultTreeNode> treeNodeList = CollectionUtil.newArrayList();

        // 组装节点
        List<HrOrganization> list = this.findListByDataScope(hrOrganizationRequest);
        for (HrOrganization hrOrganization : list) {
            DefaultTreeNode defaultTreeNode = AntdvOrganizationFactory.parseTreeNode(hrOrganization);
            treeNodeList.add(defaultTreeNode);
        }

        // 构建树并返回
        return new DefaultTreeBuildFactory<DefaultTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public List<LayuiOrganizationTreeNode> treeLayui(HrOrganizationRequest hrOrganizationRequest) {
        // 定义返回结果
        List<LayuiOrganizationTreeNode> treeNodeList = CollectionUtil.newArrayList();

        // 组装节点
        List<HrOrganization> hrOrganizationList = this.findListByDataScope(hrOrganizationRequest);
        for (HrOrganization hrOrganization : hrOrganizationList) {
            LayuiOrganizationTreeNode treeNode = LayuiOrganizationFactory.parseOrganizationTreeNode(hrOrganization);
            treeNodeList.add(treeNode);
        }

        // 构建树并返回
        return new DefaultTreeBuildFactory<LayuiOrganizationTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public List<ZTreeNode> orgZTree(HrOrganizationRequest hrOrganizationRequest, boolean buildTree) {

        // 获取角色id
        Long roleId = hrOrganizationRequest.getRoleId();

        // 获取所有组织机构列表
        LambdaQueryWrapper<HrOrganization> wrapper = createWrapper(hrOrganizationRequest);
        List<HrOrganization> list = this.list(wrapper);
        List<ZTreeNode> zTreeNodes = LayuiOrganizationFactory.parseZTree(list);

        // 获取角色目前绑定的组织机构范围
        List<Long> roleDataScopes = roleServiceApi.getRoleDataScopes(ListUtil.toList(roleId));

        // 设置绑定的组织机构范围为已选则状态
        for (ZTreeNode zTreeNode : zTreeNodes) {
            if (roleDataScopes.contains(zTreeNode.getId())) {
                zTreeNode.setChecked(true);
            }
        }

        if (buildTree) {
            return new DefaultTreeBuildFactory<ZTreeNode>().doTreeBuild(zTreeNodes);
        } else {
            return zTreeNodes;
        }
    }

    @Override
    public Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds) {

        // 定义返回结果
        Set<Long> allLevelParentIds = new HashSet<>(organizationIds);

        // 查询出这些节点的pids字段
        LambdaQueryWrapper<HrOrganization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(HrOrganization::getOrgId, organizationIds);
        queryWrapper.select(HrOrganization::getOrgPids);

        List<HrOrganization> organizationList = this.list(queryWrapper);
        if (organizationList == null || organizationList.isEmpty()) {
            return allLevelParentIds;
        }

        // 把所有的pids分割，并放入到set中
        for (HrOrganization hrOrganization : organizationList) {

            // 获取pids值
            String pids = hrOrganization.getOrgPids();

            // 去掉所有的左中括号
            pids = StrUtil.removeAll(pids, SymbolConstant.LEFT_SQUARE_BRACKETS);

            // 去掉所有的右中括号
            pids = StrUtil.removeAll(pids, SymbolConstant.RIGHT_SQUARE_BRACKETS);

            // 按逗号分割这个字符串，得到pid的数组
            String[] finalPidArray = pids.split(StrUtil.COMMA);

            // 遍历这些值，放入到最终的set
            for (String pid : finalPidArray) {
                allLevelParentIds.add(Convert.toLong(pid));
            }
        }
        return allLevelParentIds;
    }

    @Override
    public List<HrOrganizationDTO> orgList() {

        LambdaQueryWrapper<HrOrganization> queryWrapper = new LambdaQueryWrapper<>();

        // 如果是超级管理员 或 数据范围是所有，则不过滤数据范围
        boolean needToDataScope = true;
        if (LoginContext.me().getSuperAdminFlag()) {
            Set<DataScopeTypeEnum> dataScopeTypes = LoginContext.me().getLoginUser().getDataScopeTypeEnums();
            if (dataScopeTypes != null && dataScopeTypes.contains(DataScopeTypeEnum.ALL)) {
                needToDataScope = false;
            }
        }

        // 如果需要数据范围过滤，则获取用户的数据范围，拼接查询条件
        if (needToDataScope) {
            Set<Long> dataScope = LoginContext.me().getLoginUser().getDataScopeOrganizationIds();

            // 数据范围没有，直接返回空
            if (ObjectUtil.isEmpty(dataScope)) {
                return new ArrayList<>();
            }

            // 根据组织机构数据范围的上级组织，用于展示完整的树形结构
            Set<Long> allLevelParentIdsByOrganizations = this.findAllLevelParentIdsByOrganizations(dataScope);

            // 拼接查询条件
            queryWrapper.in(HrOrganization::getOrgId, allLevelParentIdsByOrganizations);
        }

        // 只查询未删除的
        queryWrapper.eq(HrOrganization::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(HrOrganization::getOrgSort);

        // 先实例化出Function接口
        Function<Object, HrOrganizationDTO> mapper = e -> {
            HrOrganizationDTO org = new HrOrganizationDTO();
            HrOrganization hrOrg = (HrOrganization) e;
            BeanUtil.copyProperties(hrOrg, org);
            return org;
        };

        // 返回数据
        List<HrOrganization> list = this.list(queryWrapper);
        return list.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    /**
     * 创建组织架构的通用条件查询wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<HrOrganization> createWrapper(HrOrganizationRequest hrOrganizationRequest) {
        LambdaQueryWrapper<HrOrganization> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除状态的
        queryWrapper.eq(HrOrganization::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(HrOrganization::getOrgSort);

        if (ObjectUtil.isEmpty(hrOrganizationRequest)) {
            return queryWrapper;
        }

        String orgName = hrOrganizationRequest.getOrgName();
        String orgCode = hrOrganizationRequest.getOrgCode();
        Long orgParentId = hrOrganizationRequest.getOrgParentId();
        Long orgId = hrOrganizationRequest.getOrgId();

        // 拼接组织机构名称条件
        queryWrapper.like(ObjectUtil.isNotEmpty(orgName), HrOrganization::getOrgName, orgName);

        // 拼接组织机构编码条件
        queryWrapper.eq(ObjectUtil.isNotEmpty(orgCode), HrOrganization::getOrgCode, orgCode);

        // 拼接父机构id查询条件
        if (ObjectUtil.isNotEmpty(orgParentId)) {
            queryWrapper.and(qw -> {
                qw.eq(HrOrganization::getOrgId, orgParentId).or().like(HrOrganization::getOrgPids, orgParentId);
            });
        }

        // 拼接机构id查询条件
        queryWrapper.eq(ObjectUtil.isNotEmpty(orgId), HrOrganization::getOrgId, orgId);

        return queryWrapper;
    }


    /**
     * 获取系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    private HrOrganization queryOrganization(HrOrganizationRequest hrOrganizationRequest) {
        HrOrganization hrOrganization = this.getById(hrOrganizationRequest.getOrgId());
        if (ObjectUtil.isEmpty(hrOrganization)) {
            throw new SystemModularException(OrganizationExceptionEnum.CANT_FIND_ORG, hrOrganizationRequest.getOrgId());
        }
        return hrOrganization;
    }

    /**
     * 填充该节点的pIds
     * <p>
     * 如果pid是顶级节点，pids就是 [-1],
     * <p>
     * 如果pid不是顶级节点，pids就是父节点的pids + [pid] + ,
     *
     * @author fengshuonan
     * @date 2020/11/5 13:45
     */
    private void fillParentIds(HrOrganization hrOrganization) {
        if (TreeConstants.DEFAULT_PARENT_ID.equals(hrOrganization.getOrgParentId())) {
            hrOrganization.setOrgPids(SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
        } else {
            // 获取父组织机构
            HrOrganizationRequest hrOrganizationRequest = new HrOrganizationRequest();
            hrOrganizationRequest.setOrgId(hrOrganization.getOrgParentId());
            HrOrganization parentOrganization = this.queryOrganization(hrOrganizationRequest);

            // 设置本节点的父ids为 (上一个节点的pids + (上级节点的id) )
            hrOrganization.setOrgPids(
                    parentOrganization.getOrgPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + parentOrganization.getOrgId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
        }
    }

    /**
     * 根据数据范围获取组织机构列表
     *
     * @author fengshuonan
     * @date 2021/2/8 20:22
     */
    private List<HrOrganization> findListByDataScope(HrOrganizationRequest hrOrganizationRequest) {

        LambdaQueryWrapper<HrOrganization> queryWrapper = this.createWrapper(hrOrganizationRequest);

        // 数据范围过滤
        // 如果是超级管理员，或者数据范围权限是所有，则不过滤数据范围
        boolean needToDataScope = true;
        Set<DataScopeTypeEnum> dataScopeTypes = LoginContext.me().getLoginUser().getDataScopeTypeEnums();
        if (LoginContext.me().getSuperAdminFlag() || (dataScopeTypes != null && dataScopeTypes.contains(DataScopeTypeEnum.ALL))) {
            needToDataScope = false;
        }

        // 过滤数据范围的SQL拼接
        if (needToDataScope) {
            // 获取用户数据范围信息
            Set<Long> dataScope = LoginContext.me().getLoginUser().getDataScopeOrganizationIds();
            // 根据组织机构数据范围的上级组织，用于展示完整的树形结构
            Set<Long> allLevelParentIdsByOrganizations = this.findAllLevelParentIdsByOrganizations(dataScope);
            // 拼接查询条件
            queryWrapper.in(HrOrganization::getOrgId, allLevelParentIdsByOrganizations);
        }

        return this.list(queryWrapper);
    }

}
