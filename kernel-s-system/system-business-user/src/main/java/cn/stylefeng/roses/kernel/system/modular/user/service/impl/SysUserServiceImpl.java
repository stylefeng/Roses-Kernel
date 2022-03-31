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
package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.api.ExpandApi;
import cn.stylefeng.roses.kernel.file.api.FileInfoApi;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.jwt.api.context.JwtContext;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import cn.stylefeng.roses.kernel.message.api.expander.WebSocketConfigExpander;
import cn.stylefeng.roses.kernel.office.api.OfficeExcelApi;
import cn.stylefeng.roses.kernel.office.api.pojo.report.ExcelExportParam;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.TreeNodeEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.api.*;
import cn.stylefeng.roses.kernel.system.api.enums.DevopsCheckStatusEnum;
import cn.stylefeng.roses.kernel.system.api.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.expander.SystemConfigExpander;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.DataScopeDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrPositionDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.*;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.api.util.DataScopeUtil;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.factory.OnlineUserCreateFactory;
import cn.stylefeng.roses.kernel.system.modular.user.factory.SysUserCreateFactory;
import cn.stylefeng.roses.kernel.system.modular.user.factory.UserLoginInfoFactory;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserMapper;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserOrgService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
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

    @Resource
    private CacheOperatorApi<SysUserDTO> sysUserCacheOperatorApi;

    @Resource
    private PositionServiceApi positionServiceApi;

    @Resource
    private ExpandApi expandApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getOrgId();

        // 获取用户有无该企业的数据权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 请求bean转为实体，填充一些基本属性
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser, CopyOptions.create().ignoreError());
        SysUserCreateFactory.fillAddSysUser(sysUser);

        // 设置用户默认头像
        sysUser.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_ID);

        // 保存用户
        this.save(sysUser);

        // 更新用户员工信息
        if (null == sysUserRequest.getPositionId()) {
            sysUserOrgService.add(sysUser.getUserId(), sysUserRequest.getOrgId());
        } else {
            sysUserOrgService.add(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
        }

        // 处理动态表单数据
        if (sysUserRequest.getExpandDataInfo() != null) {
            sysUserRequest.getExpandDataInfo().setPrimaryFieldValue(sysUser.getUserId());
            expandApi.saveOrUpdateExpandData(sysUserRequest.getExpandDataInfo());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 不能删除超级管理员
        if (YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // 获取被授权用户的所属机构
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
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

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(userId));
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
        BeanUtil.copyProperties(sysUserRequest, sysUser, CopyOptions.create().ignoreError());

        // 填充基础参数
        SysUserCreateFactory.fillEditSysUser(sysUser);
        this.updateById(sysUser);

        Long sysUserId = sysUser.getUserId();

        // 更新用户员工信息
        if (null == sysUserRequest.getPositionId()) {
            sysUserOrgService.edit(sysUser.getUserId(), sysUserRequest.getOrgId());
        } else {
            sysUserOrgService.edit(sysUser.getUserId(), sysUserRequest.getOrgId(), sysUserRequest.getPositionId());
        }

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUserId));

        // 处理动态表单数据
        if (sysUserRequest.getExpandDataInfo() != null) {
            sysUserRequest.getExpandDataInfo().setPrimaryFieldValue(sysUser.getUserId());
            expandApi.saveOrUpdateExpandData(sysUserRequest.getExpandDataInfo());
        }
    }

    @Override
    public void editInfo(SysUserRequest sysUserRequest) {

        // 获取当前登录用户的id
        sysUserRequest.setUserId(LoginContext.me().getLoginUser().getUserId());
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 填充更新用户的信息
        SysUserCreateFactory.fillUpdateInfo(sysUserRequest, sysUser);

        this.updateById(sysUser);

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    public void editStatus(SysUserRequest sysUserRequest) {

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

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    public void editPassword(SysUserRequest sysUserRequest) {

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

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
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
    public void editAvatar(SysUserRequest sysUserRequest) {

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

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取要授权角色的用户的所属机构
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
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
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        Long organizationId = userOrgInfo.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        sysUserDataScopeService.assignData(sysUserRequest);
    }

    @Override
    public SysUserDTO detail(SysUserRequest sysUserRequest) {
        SysUserDTO sysUserResponse = new SysUserDTO();

        // 获取用户基本信息
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUser, sysUserResponse, CopyOptions.create().ignoreError());

        // 获取用户组织绑定信息
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(sysUser.getUserId());
        if (ObjectUtil.isNotNull(userOrgInfo.getOrgId())) {
            sysUserResponse.setOrgId(userOrgInfo.getOrgId());
            HrOrganizationDTO orgDetail = organizationServiceApi.getOrgDetail(userOrgInfo.getOrgId());
            sysUserResponse.setOrgName(orgDetail.getOrgName());
        }
        if (ObjectUtil.isNotNull(userOrgInfo.getPositionId())) {
            sysUserResponse.setPositionId(userOrgInfo.getPositionId());
            HrPositionDTO positionDetail = positionServiceApi.getPositionDetail(userOrgInfo.getPositionId());
            sysUserResponse.setPositionName(positionDetail.getPositionName());
        }

        // 获取用户角色信息
        sysUserResponse.setGrantRoleIdList(sysUserRoleService.findRoleIdsByUserId(sysUser.getUserId()));

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserDTO> findPage(SysUserRequest sysUserRequest) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 获取当前用户数据范围的枚举
        Set<DataScopeTypeEnum> dataScopeTypeEnums = loginUser.getDataScopeTypeEnums();

        // 获取当前用户数绑定的组织机构范围
        Set<Long> dataScopeOrganizationIds = loginUser.getDataScopeOrganizationIds();

        // 获取当前用户绑定的用户数据范围
        Set<Long> dataScopeUserIds = loginUser.getDataScopeUserIds();

        // 如果包含了全部数据
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.ALL)) {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(null);
        }
        // 如果是按部门数据划分
        else if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT_WITH_CHILD)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEFINE)) {
            sysUserRequest.setScopeOrgIds(dataScopeOrganizationIds);
            sysUserRequest.setUserScopeIds(null);
        }
        // 如果包含了仅有自己的数据
        else if (dataScopeTypeEnums.contains(DataScopeTypeEnum.SELF)) {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(dataScopeUserIds);
        }
        // 其他情况，没有设置数据范围，则查所有
        else {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(null);
        }

        Page<SysUserDTO> userPage = this.baseMapper.findUserPage(PageFactory.defaultPage(), sysUserRequest);

        return PageResultFactory.createPageResult(userPage);
    }

    @Override
    public List<SysUserDTO> getUserList(SysUserRequest sysUserRequest) {
        return this.baseMapper.findUserList(sysUserRequest);
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
    public List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest) {
        // 定义返回结果
        List<UserSelectTreeNode> treeNodeList = CollectionUtil.newArrayList();
        List<HrOrganizationDTO> orgList = organizationServiceApi.orgList();
        UserSelectTreeNode orgTreeNode;
        for (HrOrganizationDTO hrOrganization : orgList) {
            orgTreeNode = new UserSelectTreeNode();
            orgTreeNode.setId(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setPId(String.valueOf(hrOrganization.getOrgParentId()));
            orgTreeNode.setName(hrOrganization.getOrgName());
            orgTreeNode.setNodeType(TreeNodeEnum.ORG.getCode());
            orgTreeNode.setValue(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setSort(hrOrganization.getOrgSort());
            treeNodeList.add(orgTreeNode);
            List<UserSelectTreeNode> userNodeList = this.getUserTreeNodeList(hrOrganization.getOrgId(), treeNodeList);
            if (userNodeList.size() > 0) {
                treeNodeList.addAll(userNodeList);
            }
        }
        // 构建树并返回
        return new DefaultTreeBuildFactory<UserSelectTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public SysUser getUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysUser> list = this.list(queryWrapper);

        // 用户不存在
        if (list.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, account);
        }

        // 账号存在多个
        if (list.size() > 1) {
            throw new SystemModularException(SysUserExceptionEnum.ACCOUNT_HAVE_MANY, account);
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
    public List<UserSelectTreeNode> getUserTreeNodeList(Long orgId, List<UserSelectTreeNode> treeNodeList) {
        // 定义返回结果
        List<UserSelectTreeNode> newTreeNodeList = CollectionUtil.newArrayList();
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setOrgId(orgId);
        List<SysUserDTO> userList = this.baseMapper.findUserList(userRequest);
        UserSelectTreeNode userTreeNode;
        for (SysUserDTO user : userList) {
            userTreeNode = new UserSelectTreeNode();
            userTreeNode.setId(String.valueOf(user.getUserId()));
            userTreeNode.setPId(String.valueOf(user.getOrgId()));
            userTreeNode.setName(user.getRealName());
            userTreeNode.setNodeType(TreeNodeEnum.USER.getCode());
            userTreeNode.setValue(String.valueOf(user.getUserId()));

            // 判断参数treeNodeList是否包含这个用户，如果包含了就不用返回了
            boolean fillThisUser = true;
            for (UserSelectTreeNode userSelectTreeNode : treeNodeList) {
                if (userSelectTreeNode.getNodeId().equals(userTreeNode.getId())) {
                    fillThisUser = false;
                    break;
                }
            }
            if (fillThisUser) {
                newTreeNodeList.add(userTreeNode);
            }
        }
        return newTreeNodeList;
    }

    @Override
    public List<SimpleDict> selector(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // 排除超级管理员
        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // 只查询id和name
        wrapper.select(SysUser::getRealName, SysUser::getUserId, SysUser::getAccount);
        List<SysUser> list = this.list(wrapper);

        ArrayList<SimpleDict> results = new ArrayList<>();
        for (SysUser sysUser : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysUser.getUserId());
            simpleDict.setName(sysUser.getRealName());
            simpleDict.setCode(sysUser.getAccount());
            results.add(simpleDict);
        }

        return results;
    }

    @Override
    public void batchDelete(SysUserRequest sysUserRequest) {
        List<Long> userIds = sysUserRequest.getUserIds();
        for (Long userId : userIds) {
            SysUserRequest tempRequest = new SysUserRequest();
            tempRequest.setUserId(userId);
            this.del(tempRequest);
        }
    }

    @Override
    public List<Long> getAllUserIds() {
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.select(SysUser::getUserId);
        userLambdaQueryWrapper.eq(SysUser::getStatusFlag, StatusEnum.ENABLE.getCode());
        userLambdaQueryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysUser> list = this.list(userLambdaQueryWrapper);
        return list.stream().map(SysUser::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<SysUserRequest> getAllUserIdList() {
        if (!SystemConfigExpander.getDevSwitchStatus()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.select(SysUser::getUserId, SysUser::getAccount);
        userLambdaQueryWrapper.eq(SysUser::getStatusFlag, StatusEnum.ENABLE.getCode());
        userLambdaQueryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());
        List<SysUser> list = this.list(userLambdaQueryWrapper);
        return list.stream().map(item -> BeanUtil.toBean(item, SysUserRequest.class)).collect(Collectors.toList());
    }

    @Override
    public String getTokenByUserId(Long userId) {
        if (!SystemConfigExpander.getDevSwitchStatus() || !LoginContext.me().getSuperAdminFlag()) {
            return null;
        }
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysUser::getUserId, userId);
        SysUser sysUser = this.getOne(userLambdaQueryWrapper);

        // 获取用户密码的加密值和用户的状态
        UserLoginInfoDTO userValidateInfo = this.getUserLoginInfo(sysUser.getAccount());

        // 获取LoginUser，用于用户的缓存
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(), false, null);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        synchronized (this) {

            // 获取ws-url 保存到用户信息中
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser, false);

            // 如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        return jwtToken;
    }

    @Override
    public Integer devopsApiCheck(SysUserRequest sysUserRequest) {
        String account = sysUserRequest.getAccount();
        String password = sysUserRequest.getPassword();
        SysUser sysUser = this.getUserByAccount(account);
        if (ObjectUtil.isEmpty(sysUser)) {
            return DevopsCheckStatusEnum.USER_NOT_EXIST.getCode();
        } else if (!passwordStoredEncryptApi.checkPassword(password, sysUser.getPassword())) {
            return DevopsCheckStatusEnum.ACCOUNT_PASSWORD_ERROR.getCode();
        } else if (!SystemConfigExpander.getDevSwitchStatus()) {
            return DevopsCheckStatusEnum.REQUESTER_NOT_OPEN_SWITCH.getCode();
        }
        return DevopsCheckStatusEnum.SUCCESSFUL.getCode();
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {

        // 1. 获取用户和账号信息
        SysUser sysUser = this.getUserByAccount(account);
        Long userId = sysUser.getUserId();

        // 2. 获取用户角色信息
        List<Long> roleIds = sysUserRoleService.findRoleIdsByUserId(userId);
        if (ObjectUtil.isEmpty(roleIds)) {
            throw new SystemModularException(AuthExceptionEnum.ROLE_IS_EMPTY);
        }
        List<SysRoleDTO> roleResponseList = roleServiceApi.getRolesByIds(roleIds);

        // 3. 获取用户的数据范围
        DataScopeDTO dataScopeResponse = dataScopeApi.getDataScope(userId, roleResponseList);

        // 4. 获取用户的组织机构和职位信息
        SysUserOrgDTO userOrgInfo = sysUserOrgService.getUserOrgByUserId(userId);

        // 5. 获取用户的所有资源url
        Set<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
        Set<String> resourceUrlsListByCodes = resourceServiceApi.getResourceUrlsListByCodes(resourceCodeList);

        // 6. 获取用户的所有按钮code集合
        Set<String> roleButtonCodes = roleServiceApi.getRoleButtonCodes(roleIds);

        // 7. 组装响应结果
        return UserLoginInfoFactory.userLoginInfoDTO(sysUser, roleResponseList, dataScopeResponse, userOrgInfo, resourceUrlsListByCodes, roleButtonCodes);
    }

    @Override
    public LoginUser getEffectiveLoginUser(LoginUser loginUser) {

        // 如果是C端用户，直接返回缓存中的登录用户
        if (loginUser.getCustomerFlag()) {
            return loginUser;
        }

        UserLoginInfoDTO userLoginInfoDTO = this.getUserLoginInfo(loginUser.getAccount());
        LoginUser newLoginUser = userLoginInfoDTO.getLoginUser();

        // 设置登录用户原有的一些信息
        newLoginUser.setToken(loginUser.getToken());
        newLoginUser.setTenantCode(loginUser.getTenantCode());
        newLoginUser.setWsUrl(loginUser.getWsUrl());
        newLoginUser.setOtherInfos(loginUser.getOtherInfos());

        return newLoginUser;
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
    public List<OnlineUserDTO> onlineUserList(OnlineUserRequest onlineUserRequest) {
        List<LoginUser> loginUsers = sessionManagerApi.onlineUserList();

        // 对象转化
        List<OnlineUserDTO> result = loginUsers.stream().map(OnlineUserCreateFactory::createOnlineUser).collect(Collectors.toList());

        // 如果带了条件则根据account筛选结果
        if (StrUtil.isNotBlank(onlineUserRequest.getAccount())) {
            return result.stream().filter(i -> i.getAccount().equals(onlineUserRequest.getAccount())).collect(Collectors.toList());
        } else {
            return result;
        }
    }

    @Override
    public SysUserDTO getUserInfoByUserId(Long userId) {

        // 从缓存查询用户
        SysUserDTO sysUserDTO = sysUserCacheOperatorApi.get(String.valueOf(userId));
        if (sysUserDTO != null) {
            return sysUserDTO;
        }

        SysUser sysUser = this.getById(userId);
        if (ObjectUtil.isNotEmpty(sysUser)) {
            SysUserDTO result = BeanUtil.copyProperties(sysUser, SysUserDTO.class);
            sysUserCacheOperatorApi.put(String.valueOf(userId), result);
            return result;
        }
        return null;
    }

    @Override
    public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // 只查询id
        wrapper.select(SysUser::getUserId);

        // 查询全部用户ID
        Function<Object, Long> mapper = id -> Long.valueOf(id.toString());

        return this.listObjs(wrapper, mapper);
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

    @Override
    public String getUserAvatarUrlByUserId(Long userId) {

        // 获取用户头像文件id
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId).select(SysUser::getAvatar);
        SysUser sysUser = this.getOne(wrapper, false);

        if (sysUser == null) {
            return "";
        }

        return this.getUserAvatarUrl(sysUser.getAvatar());
    }

    /**
     * 获取系统用户
     *
     * @author fengshuonan
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {

        // 先从缓存中获取用户信息
        String userIdKey = String.valueOf(sysUserRequest.getUserId());
        SysUserDTO sysUserDTO = sysUserCacheOperatorApi.get(userIdKey);
        if (sysUserDTO != null) {
            SysUser tempUser = new SysUser();
            BeanUtil.copyProperties(sysUserDTO, tempUser, CopyOptions.create().ignoreError());
            return tempUser;
        }

        SysUser sysUser = this.getById(sysUserRequest.getUserId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, sysUserRequest.getUserId());
        }

        // 放入缓存
        SysUserDTO sysUserDTOCache = new SysUserDTO();
        BeanUtil.copyProperties(sysUser, sysUserDTOCache, CopyOptions.create().ignoreError());
        sysUserCacheOperatorApi.put(userIdKey, sysUserDTOCache);

        return sysUser;
    }

    /**
     * 创建查询用户的wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysUser> createWrapper(SysUserRequest sysUserRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除状态的
        queryWrapper.eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysUserRequest)) {
            return queryWrapper;
        }

        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysUserRequest.getUserId()), SysUser::getUserId, sysUserRequest.getUserId());
        queryWrapper.like(ObjectUtil.isNotEmpty(sysUserRequest.getAccount()), SysUser::getAccount, sysUserRequest.getAccount());
        queryWrapper.like(ObjectUtil.isNotEmpty(sysUserRequest.getRealName()), SysUser::getRealName, sysUserRequest.getRealName());

        return queryWrapper;
    }

}
