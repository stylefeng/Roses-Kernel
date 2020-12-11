package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.stylefeng.roses.kernel.system.enums.UserStatusEnum;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.DataScopeExceptionEnum;
import cn.stylefeng.roses.kernel.system.exception.enums.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserMapper;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.response.SysUserResponse;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeRequest;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeResponse;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.system.util.DataScopeUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.office.api.OfficeExcelApi;
import cn.stylefeng.roses.kernel.office.api.pojo.report.ExcelExportParam;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.DataScopeApi;
import cn.stylefeng.roses.kernel.system.SysEmployeeApi;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.modular.user.factory.LoginUserFactory;
import cn.stylefeng.roses.kernel.system.modular.user.factory.SysUserFactory;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/21 15:04
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, UserServiceApi {

    @Resource
    private SysEmployeeApi sysEmployeeApi;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private OfficeExcelApi officeExcelApi;

    @Resource
    private DataScopeApi dataScopeApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getUserMainEmployee().getOrganizationId();

        // 获取用户有无该企业的数据权限
        if (DataScopeUtil.validateDataScopeByOrganizationId(organizationId)) {
            String userTip = StrUtil.format(DataScopeExceptionEnum.DATA_SCOPE_ERROR.getUserTip(), DataScopeUtil.getDataScopeTip());
            throw new SystemModularException(DataScopeExceptionEnum.DATA_SCOPE_ERROR, userTip);
        }

        // 请求bean转为实体，填充一些基本属性
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);
        SysUserFactory.fillAddSysUser(sysUser);

        // 保存用户
        this.save(sysUser);

        Long sysUserId = sysUser.getId();

        // 增加员工信息
        List<SysEmployeeRequest> sysEmployeeRequest = sysUserRequest.getSysEmployeeRequest();
        for (SysEmployeeRequest employeeRequest : sysEmployeeRequest) {
            employeeRequest.setUserId(sysUserId);
        }

        // 更新用户员工信息
        sysEmployeeApi.updateEmployee(sysUserId, ListUtil.toList(sysEmployeeRequest));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getUserMainEmployee().getOrganizationId();

        // 获取用户有无该企业的数据权限
        if (DataScopeUtil.validateDataScopeByOrganizationId(organizationId)) {
            String userTip = StrUtil.format(DataScopeExceptionEnum.DATA_SCOPE_ERROR.getUserTip(), DataScopeUtil.getDataScopeTip());
            throw new SystemModularException(DataScopeExceptionEnum.DATA_SCOPE_ERROR, userTip);
        }

        // 转化为实体
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUserRequest, sysUser);

        // 填充基础参数
        SysUserFactory.fillEditSysUser(sysUser);
        this.updateById(sysUser);

        Long sysUserId = sysUser.getId();

        // 编辑员工信息
        List<SysEmployeeRequest> sysEmployeeRequest = sysUserRequest.getSysEmployeeRequest();
        for (SysEmployeeRequest employeeRequest : sysEmployeeRequest) {
            employeeRequest.setUserId(sysUserId);
        }

        // 更新用户员工信息
        sysEmployeeApi.updateEmployee(sysUserId, ListUtil.toList(sysEmployeeRequest));
    }

    @Override
    public void updateInfo(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 填充更新用户的信息
        SysUserFactory.fillUpdateInfo(sysUserRequest, sysUser);

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

        Long id = sysUser.getId();

        // 更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, id)
                .and(i -> i.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode()))
                .set(SysUser::getStatusFlag, statusFlag);

        boolean update = this.update(updateWrapper);
        if (!update) {
            log.error(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR.getUserTip());
            throw new SystemModularException(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR);
        }
    }

    @Override
    public void updatePwd(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 新密码与原密码相同
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // 原密码错误
        if (!BCrypt.checkpw(sysUserRequest.getPassword(), sysUser.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        sysUser.setPassword(BCrypt.hashpw(sysUserRequest.getNewPassword(), BCrypt.gensalt()));
        this.updateById(sysUser);
    }

    @Override
    public void resetPwd(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取系统配置的默认密码
        String password = AuthConfigExpander.getDefaultPassWord();
        sysUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        this.updateById(sysUser);
    }

    @Override
    public void updateAvatar(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);
        sysUser.setAvatar(sysUserRequest.getAvatar());
        this.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取要授权角色的用户的所属机构
        SysEmployeeResponse sysEmployeeResponse = sysEmployeeApi.getUserMainEmployee(sysUser.getId());
        Long organizationId = sysEmployeeResponse.getOrganizationId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.validateDataScopeByOrganizationId(organizationId);

        // 给用户授权角色
        sysUserRoleService.grantRole(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantData(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取被授权用户的所属机构
        SysEmployeeResponse sysEmployeeResponse = sysEmployeeApi.getUserMainEmployee(sysUser.getId());
        Long organizationId = sysEmployeeResponse.getOrganizationId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.validateDataScopeByOrganizationId(organizationId);

        sysUserDataScopeService.grantData(sysUserRequest);
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
        SysEmployeeResponse sysEmployeeResponse = sysEmployeeApi.getUserMainEmployee(sysUser.getId());
        Long organizationId = sysEmployeeResponse.getOrganizationId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.validateDataScopeByOrganizationId(organizationId);

        // 逻辑删除，设置标识位Y
        sysUser.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysUser);

        Long userId = sysUser.getId();

        // 删除该用户对应的员工表信息
        sysEmployeeApi.deleteEmployeeByUserId(userId);

        // 删除该用户对应的用户-角色表关联信息
        sysUserRoleService.deleteUserRoleListByUserId(userId);

        // 删除该用户对应的用户-数据范围表关联信息
        sysUserDataScopeService.deleteUserDataScopeListByUserId(userId);
    }

    @Override
    public SysUserResponse detail(SysUserRequest sysUserRequest) {
        SysUserResponse sysUserResponse = new SysUserResponse();

        // 获取用户基本信息
        SysUser sysUser = this.querySysUser(sysUserRequest);
        BeanUtil.copyProperties(sysUser, sysUserRequest);

        // 获取对应员工信息
        List<SysEmployeeResponse> sysEmployeeResponse = sysEmployeeApi.getUserAllEmployee(sysUser.getId());
        sysUserResponse.setSysEmployeeResponse(sysEmployeeResponse);

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserResponse> page(SysUserRequest sysUserRequest) {

        Page<SysUserResponse> userPage =
                this.baseMapper.findUserPage(PageFactory.defaultPage(), sysUserRequest);

        return PageResultFactory.createPageResult(userPage);
    }

    @Override
    public List<Long> getUserRoles(SysUserRequest sysUserRequest) {
        return this.getUserRoleIdList(sysUserRequest.getId());
    }

    @Override
    public List<SimpleDict> selector(SysUserRequest sysUserRequest) {

        LambdaQueryWrapper<SysUser> wrapper = createWrapper(sysUserRequest);

        // 排除超级管理员
        wrapper.ne(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());

        // 只查询id和name
        wrapper.select(SysUser::getName, SysUser::getId);
        List<SysUser> list = this.list(wrapper);

        ArrayList<SimpleDict> results = new ArrayList<>();
        for (SysUser sysUser : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysUser.getId());
            simpleDict.setName(sysUser.getName());
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
    public SysUser getUserByCount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.ne(SysUser::getDelFlag, YesOrNotEnum.Y.getCode());
        return this.getOne(queryWrapper);
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {
        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();

        // 根据账号获取系统用户表中的信息
        SysUser sysUser = getUserByCount(account);
        LoginUser loginUser = LoginUserFactory.createLoginUser(sysUser);

        // 获取用户加密的密码，用于登录校验
        userLoginInfoDTO.setUserPasswordHexed(sysUser.getPassword());

        // 填充用户状态
        userLoginInfoDTO.setUserStatus(sysUser.getStatusFlag());

        // 设置用户登录详情信息
        userLoginInfoDTO.setLoginUser(loginUser);

        return userLoginInfoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserLoginInfo(Long userId, Date date, String ip) {

        // 根据用户id获取用户信息实体
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getId, userId).eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());
        SysUser sysUser = this.getOne(sysUserLambdaQueryWrapper);

        if (sysUser != null) {
            // 更新用户登录信息
            SysUser newSysUser = new SysUser();
            newSysUser.setId(sysUser.getId());
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
            queryWrapper.in(SysUserDataScope::getOrganizationId, organizationIds);
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
        return sysUserDataScopeService.getUserDataScopeIdList(userId);
    }

    /**
     * 获取系统用户
     *
     * @author fengshuonan
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.getById(sysUserRequest.getId());
        if (ObjectUtil.isNull(sysUser)) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
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
        if (ObjectUtil.isNotNull(sysUserRequest)) {

            // 组装账号的查询条件
            if (ObjectUtil.isNotEmpty(sysUserRequest.getAccount())) {
                queryWrapper.like(SysUser::getAccount, sysUserRequest.getAccount());
            }

            // 组装用户姓名的查询条件
            if (ObjectUtil.isNotEmpty(sysUserRequest.getName())) {
                queryWrapper.eq(SysUser::getName, sysUserRequest.getName());
            }
        }

        // 查询未删除状态的
        queryWrapper.eq(SysUser::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
