/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package cn.stylefeng.roses.kernel.role.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.role.modular.entity.*;
import cn.stylefeng.roses.kernel.role.modular.mapper.SysRoleMapper;
import cn.stylefeng.roses.kernel.role.modular.service.*;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.UserServiceApi;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysRoleExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleMenuButtonResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleMenuResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleResourceResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleResponse;
import cn.stylefeng.roses.kernel.system.util.DataScopeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 系统角色service接口实现类
 *
 * @author majianguo
 * @date 2020/11/5 上午11:33
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService, RoleServiceApi {

    @Resource
    private UserServiceApi userServiceApi;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;

    @Resource
    private SysRoleMenuService roleMenuService;

    @Resource
    private SysRoleMenuButtonService sysRoleMenuButtonService;

    @Override
    public void add(SysRoleRequest sysRoleRequest) {

        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleRequest, sysRole);

        // 默认设置为启用
        sysRole.setStatusFlag(StatusEnum.ENABLE.getCode());

        // 默认设置为普通角色
        sysRole.setRoleSystemFlag(YesOrNotEnum.N.getCode());

        //默认数据范围
        sysRole.setDataScopeType(DataScopeTypeEnum.SELF.getCode());

        this.save(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // 超级管理员不能删除
        if (YesOrNotEnum.Y.getCode().equals(sysRole.getRoleSystemFlag())) {
            throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
        }

        // 逻辑删除，设为删除标志
        sysRole.setDelFlag(YesOrNotEnum.Y.getCode());

        this.updateById(sysRole);

        Long roleId = sysRole.getRoleId();

        // 级联删除该角色对应的角色-数据范围关联信息
        sysRoleDataScopeService.delByRoleId(roleId);

        // 级联删除该角色对应的用户-角色表关联信息
        userServiceApi.deleteUserRoleListByRoleId(roleId);

        // 级联删除该角色对应的角色-菜单表关联信息
        sysRoleResourceService.deleteRoleResourceListByRoleId(roleId);
    }

    @Override
    public void edit(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        BeanUtil.copyProperties(sysRoleRequest, sysRole);

        // 不能修改状态，用修改状态接口修改状态
        sysRole.setStatusFlag(null);

        this.updateById(sysRole);
    }

    @Override
    public SysRoleResponse detail(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        SysRoleResponse roleResponse = new SysRoleResponse();
        BeanUtil.copyProperties(sysRole, roleResponse);

        // 填充数据范围类型枚举
        roleResponse.setDataScopeTypeEnum(DataScopeTypeEnum.codeToEnum(sysRole.getDataScopeType()));

        return roleResponse;
    }

    @Override
    public PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest) {
        LambdaQueryWrapper<SysRole> wrapper = createWrapper(sysRoleRequest);
        Page<SysRole> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<SimpleDict> findList(SysRoleRequest sysRoleParam) {
        List<SimpleDict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysRoleParam)) {

            // 根据角色名称或编码模糊查询
            if (ObjectUtil.isNotEmpty(sysRoleParam.getRoleName())) {
                queryWrapper.and(i -> i.like(SysRole::getRoleName, sysRoleParam.getRoleName()).or().like(SysRole::getRoleCode, sysRoleParam.getRoleName()));
            }
        }

        // 只查询正常状态
        queryWrapper.eq(SysRole::getStatusFlag, StatusEnum.ENABLE.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        this.list(queryWrapper).forEach(sysRole -> {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysRole.getRoleId());
            simpleDict.setName(sysRole.getRoleName() + SymbolConstant.LEFT_SQUARE_BRACKETS + sysRole.getRoleCode() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
            dictList.add(simpleDict);
        });
        return dictList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);

        // 获取当前用户是否是超级管理员
        boolean superAdmin = LoginContext.me().getSuperAdminFlag();

        // 获取请求参数的数据范围类型
        Integer dataScopeType = sysRoleRequest.getDataScopeType();
        DataScopeTypeEnum dataScopeTypeEnum = DataScopeTypeEnum.codeToEnum(dataScopeType);

        // 如果登录用户不是超级管理员，则进行数据权限校验
        if (!superAdmin) {

            // 只有超级管理员可以授权全部范围
            if (DataScopeTypeEnum.ALL.equals(dataScopeTypeEnum)) {
                throw new AuthException(AuthExceptionEnum.ONLY_SUPER_ERROR);
            }

            // 数据范围类型为自定义，则判断当前用户有没有该公司的权限
            if (DataScopeTypeEnum.DEFINE.getCode().equals(dataScopeType)) {
                if (ObjectUtil.isEmpty(sysRoleRequest.getGrantOrgIdList())) {
                    throw new SystemModularException(SysRoleExceptionEnum.PLEASE_FILL_DATA_SCOPE);
                }
                for (Long orgId : sysRoleRequest.getGrantOrgIdList()) {
                    DataScopeUtil.quickValidateDataScope(orgId);
                }
            }
        }

        sysRole.setDataScopeType(sysRoleRequest.getDataScopeType());
        this.updateById(sysRole);

        // 绑定角色数据范围关联
        sysRoleDataScopeService.grantDataScope(sysRoleRequest);
    }

    @Override
    public List<SimpleDict> dropDown() {
        List<SimpleDict> dictList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        // 如果当前登录用户不是超级管理员，则查询自己拥有的角色
        if (!LoginContext.me().getSuperAdminFlag()) {

            // 查询自己拥有的
            List<SimpleRoleInfo> roles = LoginContext.me().getLoginUser().getSimpleRoleInfoList();

            // 取出所有角色id
            Set<Long> loginUserRoleIds = roles.stream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toSet());
            if (ObjectUtil.isEmpty(loginUserRoleIds)) {
                return dictList;
            }
            queryWrapper.in(SysRole::getRoleId, loginUserRoleIds);
        }

        // 只查询正常状态
        queryWrapper.eq(SysRole::getStatusFlag, StatusEnum.ENABLE.getCode()).eq(SysRole::getDelFlag, YesOrNotEnum.N.getCode());

        this.list(queryWrapper).forEach(sysRole -> {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysRole.getRoleId());
            simpleDict.setCode(sysRole.getRoleCode());
            simpleDict.setName(sysRole.getRoleName());
            dictList.add(simpleDict);
        });
        return dictList;
    }

    @Override
    public List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.querySysRole(sysRoleRequest);
        return sysRoleDataScopeService.getRoleDataScopeIdList(CollectionUtil.newArrayList(sysRole.getRoleId()));
    }

    @Override
    public String getNameByRoleId(Long roleId) {
        SysRole sysRole = this.getById(roleId);
        if (ObjectUtil.isEmpty(sysRole)) {
            throw new SystemModularException(SysRoleExceptionEnum.ROLE_NOT_EXIST);
        }
        return sysRole.getRoleName();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantMenuAndButton(SysRoleRequest sysRoleMenuButtonRequest) {
        // 清除该角色之前的菜单
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLqw = new LambdaQueryWrapper<>();
        sysRoleMenuLqw.eq(SysRoleMenu::getRoleId, sysRoleMenuButtonRequest.getRoleId());
        roleMenuService.remove(sysRoleMenuLqw);

        // 清除该角色之前的按钮授权
        LambdaQueryWrapper<SysRoleMenuButton> menuButtonLqw = new LambdaQueryWrapper<>();
        menuButtonLqw.eq(SysRoleMenuButton::getRoleId, sysRoleMenuButtonRequest.getRoleId());
        sysRoleMenuButtonService.remove(menuButtonLqw);

        // 新增菜单
        List<Long> menuIdList = sysRoleMenuButtonRequest.getGrantMenuIdList();
        if (ObjectUtil.isNotEmpty(menuIdList)) {
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysRoleMenu item = new SysRoleMenu();
                item.setRoleId(sysRoleMenuButtonRequest.getRoleId());
                item.setMenuId(menuId);
                sysRoleMenus.add(item);
            }
            roleMenuService.saveBatch(sysRoleMenus);
        }

        // 新增按钮
        List<SysRoleMenuButtonRequest> menuButtonList = sysRoleMenuButtonRequest.getGrantMenuButtonIdList();
        if (ObjectUtil.isNotEmpty(menuButtonList)) {
            List<SysRoleMenuButton> sysRoleMenuButtons = new ArrayList<>();
            for (SysRoleMenuButtonRequest menuButton : menuButtonList) {
                SysRoleMenuButton item = new SysRoleMenuButton();
                item.setRoleId(sysRoleMenuButtonRequest.getRoleId());
                item.setButtonId(menuButton.getButtonId());
                item.setButtonCode(menuButton.getButtonCode());
                sysRoleMenuButtons.add(item);
            }
            sysRoleMenuButtonService.saveBatch(sysRoleMenuButtons);
        }

    }

    @Override
    public List<SysRoleResponse> getRolesByIds(List<Long> roleIds) {

        ArrayList<SysRoleResponse> sysRoleResponses = new ArrayList<>();

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRole::getRoleId, roleIds);
        List<SysRole> sysRoles = this.list(queryWrapper);

        // 角色列表不为空，角色信息转化为SysRoleResponse
        if (!sysRoles.isEmpty()) {
            for (SysRole sysRole : sysRoles) {
                SysRoleResponse sysRoleResponse = new SysRoleResponse();
                BeanUtil.copyProperties(sysRole, sysRoleResponse);
                // 填充数据范围类型枚举
                sysRoleResponse.setDataScopeTypeEnum(DataScopeTypeEnum.codeToEnum(sysRole.getDataScopeType()));
                sysRoleResponses.add(sysRoleResponse);
            }
        }

        return sysRoleResponses;
    }

    @Override
    public List<Long> getRoleDataScopes(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleDataScope::getRoleId, roleIds);

        List<SysRoleDataScope> list = this.sysRoleDataScopeService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list.stream().map(SysRoleDataScope::getOrganizationId).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取角色绑定的菜单
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenu::getRoleId, roleIds);
        queryWrapper.select(SysRoleMenu::getMenuId);

        List<SysRoleMenu> roleMenus = this.roleMenuService.list(queryWrapper);
        if (roleMenus == null || roleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleResourceCodeList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRoleResource::getResourceCode);
        queryWrapper.in(SysRoleResource::getRoleId, roleIdList);
        List<SysRoleResource> sysRoleResources = sysRoleResourceService.list(queryWrapper);
        return sysRoleResources.parallelStream().map(SysRoleResource::getResourceCode).collect(Collectors.toList());
    }

    @Override
    public List<SysRoleResourceResponse> getRoleResourceList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleResource::getRoleId, roleIdList);
        List<SysRoleResource> sysRoleResources = sysRoleResourceService.list(queryWrapper);
        return sysRoleResources.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleResourceResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Set<String> getRoleButtonCodes(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenuButton> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenuButton::getRoleId, roleIdList);
        queryWrapper.select(SysRoleMenuButton::getButtonCode);
        List<SysRoleMenuButton> list = sysRoleMenuButtonService.list(queryWrapper);
        return list.stream().map(SysRoleMenuButton::getButtonCode).collect(Collectors.toSet());
    }

    @Override
    public List<SysRoleMenuResponse> getRoleMenuList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuLambdaQueryWrapper.in(SysRoleMenu::getRoleId, roleIdList);
        List<SysRoleMenu> roleMenus = roleMenuService.list(sysRoleMenuLambdaQueryWrapper);
        List<SysRoleMenuResponse> sysRoleMenuResponses = roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuResponse.class)).collect(Collectors.toList());
        return sysRoleMenuResponses;
    }

    @Override
    public List<SysRoleMenuButtonResponse> getRoleMenuButtonList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleMenuButton> sysRoleMenuButtonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuButtonLambdaQueryWrapper.in(SysRoleMenuButton::getRoleId, roleIdList);
        List<SysRoleMenuButton> sysRoleMenuButtons = sysRoleMenuButtonService.list(sysRoleMenuButtonLambdaQueryWrapper);
        List<SysRoleMenuButtonResponse> sysRoleMenuButtonResponses = sysRoleMenuButtons.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuButtonResponse.class)).collect(Collectors.toList());
        return sysRoleMenuButtonResponses;
    }

    /**
     * 获取系统角色
     *
     * @param sysRoleRequest 请求信息
     * @author majianguo
     * @date 2020/11/5 下午4:12
     */
    private SysRole querySysRole(SysRoleRequest sysRoleRequest) {
        SysRole sysRole = this.getById(sysRoleRequest.getRoleId());
        if (ObjectUtil.isNull(sysRole)) {
            throw new SystemModularException(SysRoleExceptionEnum.ROLE_NOT_EXIST);
        }
        return sysRole;
    }

    /**
     * 创建查询wrapper
     *
     * @author fengshuonan
     * @date 2020/11/22 15:14
     */
    private LambdaQueryWrapper<SysRole> createWrapper(SysRoleRequest sysRoleRequest) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysRoleRequest)) {

            // 根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysRoleRequest.getRoleName())) {
                queryWrapper.like(SysRole::getRoleName, sysRoleRequest.getRoleName());
            }

            // 根据编码模糊查询
            if (ObjectUtil.isNotEmpty(sysRoleRequest.getRoleCode())) {
                queryWrapper.like(SysRole::getRoleCode, sysRoleRequest.getRoleCode());
            }
        }

        // 查询未删除的
        queryWrapper.eq(SysRole::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysRole::getRoleSort);

        return queryWrapper;
    }

}
