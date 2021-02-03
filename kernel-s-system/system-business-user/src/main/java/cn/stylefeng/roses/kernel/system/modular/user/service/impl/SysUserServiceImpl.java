package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.FileInfoApi;
import cn.stylefeng.roses.kernel.office.api.OfficeExcelApi;
import cn.stylefeng.roses.kernel.office.api.pojo.report.ExcelExportParam;
import cn.stylefeng.roses.kernel.rule.enums.TreeNodeEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.tree.UserSelectTreeNode;
import cn.stylefeng.roses.kernel.system.DataScopeApi;
import cn.stylefeng.roses.kernel.system.OrganizationServiceApi;
import cn.stylefeng.roses.kernel.system.ResourceServiceApi;
import cn.stylefeng.roses.kernel.system.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.expander.SystemConfigExpander;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.factory.OnlineUserCreateFactory;
import cn.stylefeng.roses.kernel.system.modular.user.factory.SysUserCreateFactory;
import cn.stylefeng.roses.kernel.system.modular.user.factory.UserLoginInfoFactory;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserMapper;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.response.SysUserResponse;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserOrgService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.system.pojo.organization.DataScopeResponse;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrOrganizationResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleResponse;
import cn.stylefeng.roses.kernel.system.pojo.user.OnlineUserResponse;
import cn.stylefeng.roses.kernel.system.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.pojo.user.SysUserOrgResponse;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.system.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.util.DataScopeUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/21 15:04
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserOrgService sysUserOrgService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private OfficeExcelApi officeExcelApi;

    @Resource
    private DataScopeApi dataScopeApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private FileInfoApi fileInfoApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private OrganizationServiceApi organizationServiceApi;

    @Override
    public void register(SysUserRequest sysUserRequest) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);
        sysUser.setRealName(IdUtil.simpleUUID());
        SysUserCreateFactory.fillAddSysUser(sysUser);
        // 保存用户
        this.save(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getOrgId();

        // 获取用户有无该企业的数据权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 请求bean转为实体，填充一些基本属性
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);
        SysUserCreateFactory.fillAddSysUser(sysUser);

        // 保存用户
        this.save(sysUser);

        // 更新用户员工信息
        sysUserOrgService.add(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getOrgId();

        // 获取用户有无该企业的数据权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 转化为实体
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUserRequest, sysUser);

        // 填充基础参数
        SysUserCreateFactory.fillEditSysUser(sysUser);
        this.updateById(sysUser);

        Long sysUserId = sysUser.getUserId();

        // 更新用户员工信息
        sysUserOrgService.edit(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
    }

    @Override
    public void updateInfo(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 填充更新用户的信息
        SysUserCreateFactory.fillUpdateInfo(sysUserRequest, sysUser);

        this.updateById(sysUser);
    }

    @Override
    public void changeStatus(SysUserRequest sysUserRequest) {

        // 校验状态在不在枚举值里
        Integer statusFlag = sysUserRequest.getStatusFlag();
        UserStatusEnum.validateUserStatus(statusFlag);

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 不能修改超级管理员状态
        if (YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getUserId();

        // 更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, id).and(i -> i.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode())).set(SysUser::getStatusFlag, statusFlag);

        boolean update = this.update(updateWrapper);
        if (!update) {
            log.error(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR.getUserTip());
            throw new SystemModularException(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR);
        }
    }

    @Override
    public void updatePassword(SysUserRequest sysUserRequest) {

        // 获取当前用户的userId
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 新密码与原密码相同
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // 原密码错误
        if (!passwordStoredEncryptApi.checkPassword(sysUserRequest.getPassword(), sysUser.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        sysUser.setPassword(passwordStoredEncryptApi.encrypt(sysUserRequest.getNewPassword()));
        this.updateById(sysUser);
    }

    @Override
    public void resetPassword(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取系统配置的默认密码
        String password = SystemConfigExpander.getDefaultPassWord();
        sysUser.setPassword(passwordStoredEncryptApi.encrypt(password));

        this.updateById(sysUser);
    }

    @Override
    public void updateAvatar(SysUserRequest sysUserRequest) {

        // 新头像文件id
        Long fileId = sysUserRequest.getAvatar();

        // 从当前用户获取用户id
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        // 更新用户头像
        SysUser sysUser = this.querySysUser(sysUserRequest);
        sysUser.setAvatar(fileId);
        this.updateById(sysUser);

        // 更新当前用户的session信息
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();
        simpleUserInfo.setAvatar(fileId);
        sessionManagerApi.updateSession(LoginContext.me().getToken(), loginUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取要授权角色的用户的所属机构
        SysUserOrgResponse userOrgInfo = sysUserOrgService.getUserOrgInfo(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 给用户授权角色
        sysUserRoleService.assignRoles(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantData(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取被授权用户的所属机构
        SysUserOrgResponse userOrgInfo = sysUserOrgService.getUserOrgInfo(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        sysUserDataScopeService.assignData(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 不能删除超级管理员
        if (YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // 获取被授权用户的所属机构
        SysUserOrgResponse userOrgInfo = sysUserOrgService.getUserOrgInfo(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 逻辑删除，设置标识位Y
        sysUser.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysUser);

        Long userId = sysUser.getUserId();

        // 删除该用户对应的员工表信息
        sysUserOrgService.delByUserId(userId);

        // 删除该用户对应的用户-角色表关联信息
        sysUserRoleService.delByUserId(userId);

        // 删除该用户对应的用户-数据范围表关联信息
        sysUserDataScopeService.delByUserId(userId);
    }

    @Override
    public SysUserResponse detail(SysUserRequest sysUserRequest) {
        SysUserResponse sysUserResponse = new SysUserResponse();

        // 获取用户基本信息
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUser, sysUserResponse);

        // 获取用户组织绑定信息
        SysUserOrgResponse userOrgInfo = sysUserOrgService.getUserOrgInfo(sysUser.getUserId());
        sysUserResponse.setOrgId(userOrgInfo.getOrgId());
        sysUserResponse.setPositionId(userOrgInfo.getPositionId());

        // 获取用户角色信息
        sysUserResponse.setGrantRoleIdList(sysUserRoleService.findRoleIdsByUserId(sysUser.getUserId()));

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserResponse> page(SysUserRequest sysUserRequest) {

        Page<SysUserResponse> userPage = this.baseMapper.findUserPage(PageFactory.defaultPage(), sysUserRequest);

        return PageResultFactory.createPageResult(userPage);
    }

    @Override
    public List<SimpleDict> selector(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // 排除超级管理员
        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // 只查询id和name
        wrapper.select(SysUser::getRealName, SysUser::getUserId);
        List<SysUser> list = this.list(wrapper);

        ArrayList<SimpleDict> results = new ArrayList<>();
        for (SysUser sysUser : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysUser.getUserId());
            simpleDict.setName(sysUser.getRealName());
            results.add(simpleDict);
        }

        return results;
    }

    @Override
    public void export(HttpServletResponse response) {
        ExcelExportParam excelExportParam = new ExcelExportParam();
        List<SysUser> sysUserList = this.list();

        excelExportParam.setClazz(SysUser.class);
        excelExportParam.setDataList(sysUserList);
        excelExportParam.setExcelTypeEnum(ExcelTypeEnum.XLS);
        excelExportParam.setFileName("系统用户导出");
        excelExportParam.setResponse(response);

        officeExcelApi.easyExportDownload(excelExportParam);
    }

    @Override
    public SysUser getUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysUser> list = this.list(queryWrapper);

        // 用户不存在
        if (list.isEmpty()) {
            String userTip = StrUtil.format(SysUserExceptionEnum.USER_NOT_EXIST.getUserTip(), account);
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, userTip);
        }

        // 账号存在多个
        if (list.size() > 1) {
            String userTip = StrUtil.format(SysUserExceptionEnum.ACCOUNT_HAVE_MANY.getUserTip(), account);
            throw new SystemModularException(SysUserExceptionEnum.ACCOUNT_HAVE_MANY, userTip);
        }

        return list.get(0);
    }

    @Override
    public String getUserAvatarUrl(Long fileId) {

        // 获取头像的访问地址
        return fileInfoApi.getFileAuthUrl(fileId);
    }

    @Override
    public String getUserAvatarUrl(Long fileId, String token) {

        // 获取头像的访问地址
        return fileInfoApi.getFileAuthUrl(fileId, token);
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {

        // 1. 获取用户和账号信息
        SysUser sysUser = this.getUserByAccount(account);
        Long userId = sysUser.getUserId();

        // 2. 获取用户角色信息
        List<Long> roleIds = sysUserRoleService.findRoleIdsByUserId(userId);
        List<SysRoleResponse> roleResponseList = roleServiceApi.getRolesByIds(roleIds);

        // 3. 获取用户的数据范围
        DataScopeResponse dataScopeResponse = dataScopeApi.getDataScope(userId, roleResponseList);

        // 4. 获取用户的组织机构和职位信息
        SysUserOrgResponse userOrgInfo = sysUserOrgService.getUserOrgInfo(userId);

        // 5. 获取用户的所有资源url
        List<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
        Set<String> resourceUrlsListByCodes = resourceServiceApi.getResourceUrlsListByCodes(resourceCodeList);

        // 6. 获取用户的所有按钮code集合
        Set<String> roleButtonCodes = roleServiceApi.getRoleButtonCodes(roleIds);

        // 7. 组装响应结果
        return UserLoginInfoFactory.userLoginInfoDTO(sysUser, roleResponseList, dataScopeResponse, userOrgInfo, resourceUrlsListByCodes, roleButtonCodes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserLoginInfo(Long userId, Date date, String ip) {

        // 根据用户id获取用户信息实体
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserId, userId).eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());
        SysUser sysUser = this.getOne(sysUserLambdaQueryWrapper);

        if (sysUser != null) {
            // 更新用户登录信息
            SysUser newSysUser = new SysUser();
            newSysUser.setUserId(sysUser.getUserId());
            newSysUser.setLastLoginIp(ip);
            newSysUser.setLastLoginTime(date);
            this.updateById(newSysUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds) {
        if (organizationIds != null && organizationIds.size() > 0) {
            LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysUserDataScope::getOrgId, organizationIds);
            sysUserDataScopeService.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getUserRoleIdList(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        queryWrapper.select(SysUserRole::getRoleId);

        List<SysUserRole> list = sysUserRoleService.list(queryWrapper);
        return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoleListByRoleId(Long roleId) {
        if (roleId != null) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getRoleId, roleId);
            sysUserRoleService.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getUserBindDataScope(Long userId) {
        return sysUserDataScopeService.findOrgIdsByUserId(userId);
    }

    @Override
    public List<OnlineUserResponse> onlineUserList(OnlineUserRequest onlineUserRequest) {
        List<LoginUser> loginUsers = sessionManagerApi.onlineUserList();

        // 对象转化
        List<OnlineUserResponse> result = loginUsers.stream().map(OnlineUserCreateFactory::createOnlineUser).collect(Collectors.toList());

        // 如果带了条件则根据account筛选结果
        if (StrUtil.isNotBlank(onlineUserRequest.getAccount())) {
            return result.stream().filter(i -> i.getAccount().equals(onlineUserRequest.getAccount())).collect(Collectors.toList());
        } else {
            return result;
        }
    }

    @Override
    public cn.stylefeng.roses.kernel.system.pojo.user.SysUserResponse getUserInfoByUserId(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNotEmpty(sysUser)) {
            return BeanUtil.copyProperties(sysUser, cn.stylefeng.roses.kernel.system.pojo.user.SysUserResponse.class);
        }
        return null;
    }


    @Override
    public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // 排除超级管理员
//        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // 只查询id
        wrapper.select(SysUser::getUserId);
        // 查询全部用户ID
        Function<Object, Long> mapper = id -> Long.valueOf(id.toString());
        List<Long> userIds = this.listObjs(wrapper, mapper);

        return userIds;
    }

    /**
     * 获取系统用户信息
     *
     * @author liuhanqing
     * @date 2021/1/4 22:54
     */
    @Override
    public SysUserDTO getUserInfo(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, userId);
        }
        SysUserDTO userDTO = new SysUserDTO();
        BeanUtil.copyProperties(sysUser, userDTO);
        return userDTO;
    }

    @Override
    public Boolean userExist(Long userId) {
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setUserId(userId);
        LambdaQueryWrapper<SysUser> wrapper = createWrapper(userRequest);

        // 只查询id
        wrapper.select(SysUser::getUserId);
        // 查询用户
        SysUser sysUser = this.getOne(wrapper);
        if (sysUser == null || sysUser.getUserId() == null) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 获取系统用户
     *
     * @author fengshuonan
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.getById(sysUserRequest.getUserId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, sysUserRequest.getUserId());
        }
        return sysUser;
    }

    @Override
    public List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest) {
        // 定义返回结果
        List<UserSelectTreeNode> treeNodeList = CollectionUtil.newArrayList();
        List<HrOrganizationResponse> orgList = organizationServiceApi.orgList();
        UserSelectTreeNode orgTreeNode;
        for (HrOrganizationResponse hrOrganization : orgList) {
            orgTreeNode = new UserSelectTreeNode();
            orgTreeNode.setId(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setPId(String.valueOf(hrOrganization.getOrgParentId()));
            orgTreeNode.setName(hrOrganization.getOrgName());
            orgTreeNode.setNodeType(TreeNodeEnum.ORG.getCode());
            orgTreeNode.setValue(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setSort(hrOrganization.getOrgSort());
            treeNodeList.add(orgTreeNode);
            List<UserSelectTreeNode> userNodeList = this.getUserTreeNodeList(hrOrganization.getOrgId());
            if (userNodeList.size() > 0) {
                treeNodeList.addAll(userNodeList);
            }
        }
        // 构建树并返回
        return new DefaultTreeBuildFactory<UserSelectTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public List<UserSelectTreeNode> getUserTreeNodeList(Long orgId) {
        // 定义返回结果
        List<UserSelectTreeNode> treeNodeList = CollectionUtil.newArrayList();
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setOrgId(orgId);
        List<SysUserResponse> userList = this.baseMapper.findUserList(userRequest);
        UserSelectTreeNode userTreeNode;
        for (SysUserResponse user : userList) {
            userTreeNode = new UserSelectTreeNode();
            userTreeNode.setId(String.valueOf(user.getUserId()));
            userTreeNode.setPId(String.valueOf(user.getOrgId()));
            userTreeNode.setName(user.getRealName());
            userTreeNode.setNodeType(TreeNodeEnum.USER.getCode());
            userTreeNode.setValue(String.valueOf(user.getUserId()));
//            userTreeNode.setSort(hrOrganization.getOrgSort());
            treeNodeList.add(userTreeNode);
        }
        return treeNodeList;
    }

    /**
     * 创建查询用户的wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysUser> createWrapper(SysUserRequest sysUserRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysUserRequest.getUserId()), SysUser::getUserId, sysUserRequest.getUserId());
        queryWrapper.like(ObjectUtil.isNotEmpty(sysUserRequest.getAccount()), SysUser::getAccount, sysUserRequest.getAccount());
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysUserRequest.getRealName()), SysUser::getRealName, sysUserRequest.getRealName());

        // 查询未删除状态的
        queryWrapper.eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
