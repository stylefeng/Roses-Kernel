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
package cn.stylefeng.roses.kernel.menu.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.menu.modular.factory.AntdMenusFactory;
import cn.stylefeng.roses.kernel.menu.modular.factory.LayuiMenusFactory;
import cn.stylefeng.roses.kernel.menu.modular.factory.common.CommonMenusFactory;
import cn.stylefeng.roses.kernel.menu.modular.mapper.SysMenuMapper;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.AppServiceApi;
import cn.stylefeng.roses.kernel.system.MenuServiceApi;
import cn.stylefeng.roses.kernel.system.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysMenuExceptionEnum;
import cn.stylefeng.roses.kernel.system.exception.enums.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdIndexMenuTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.layui.LayuiAppIndexMenus;
import cn.stylefeng.roses.kernel.system.pojo.menu.other.MenuSelectTreeNode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统菜单service接口实现类
 *
 * @author fengshuonan
 * @date 2020/3/13 16:05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService, MenuServiceApi {

    @Resource
    private DbOperatorApi dbOperatorApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private AppServiceApi appServiceApi;

    @Override
    public void add(SysMenuRequest sysMenuRequest) {

        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuRequest, sysMenu);

        // 组装pids
        String newPids = createPids(sysMenuRequest.getMenuParentId());
        sysMenu.setMenuPids(newPids);

        // 设置启用状态
        sysMenu.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysMenuRequest sysMenuRequest) {

        // 获取库中的菜单信息
        SysMenu oldMenu = this.querySysMenu(sysMenuRequest);

        String newPids = updateChildrenAppAndLevel(sysMenuRequest, oldMenu);

        // 拷贝参数到实体中
        BeanUtil.copyProperties(sysMenuRequest, oldMenu);

        // 设置新的pids
        oldMenu.setMenuPids(newPids);

        // 不能修改状态，用修改状态接口修改状态
        oldMenu.setStatusFlag(null);

        this.updateById(oldMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysMenuRequest sysMenuRequest) {

        Long id = sysMenuRequest.getMenuId();

        // 获取所有子级的节点id
        Set<Long> childIdList = this.dbOperatorApi.findSubListByParentId(
                "sys_menu", "menu_pids", "menu_id", id);
        childIdList.add(id);

        // 逻辑删除，设置删除标识
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(SysMenu::getMenuId, childIdList)
                .set(SysMenu::getDelFlag, YesOrNotEnum.Y.getCode());
        this.update(updateWrapper);
    }

    @Override
    public SysMenu detail(SysMenuRequest sysMenuRequest) {
        return this.querySysMenu(sysMenuRequest);
    }

    @Override
    public List<SysMenu> layuiList(SysMenuRequest sysMenuRequest) {

        LambdaQueryWrapper<SysMenu> wrapper = createWrapper(sysMenuRequest);

        List<SysMenu> list = this.list(wrapper);

        // 应用编码转化为应用名称
        HashMap<String, String> appCodeName = new HashMap<>();
        Set<String> appCodeSet = list.stream().map(SysMenu::getAppCode).collect(Collectors.toSet());
        for (String appCode : appCodeSet) {
            String appName = appServiceApi.getAppNameByAppCode(appCode);
            appCodeName.put(appCode, appName);
        }

        // 查询对应菜单的应用名称
        for (SysMenu sysMenu : list) {
            sysMenu.setAppName(appCodeName.get(sysMenu.getAppCode()));
        }

        return list;
    }

    @Override
    public List<SysMenu> list(SysMenuRequest sysMenuRequest) {

        LambdaQueryWrapper<SysMenu> wrapper = createWrapper(sysMenuRequest);

        List<SysMenu> sysMenuList = this.list(wrapper);

        // 将结果集处理成树
        return new DefaultTreeBuildFactory<SysMenu>().doTreeBuild(sysMenuList);
    }

    @Override
    public List<SysMenu> getCurrentUserMenus() {
        return getCurrentUserMenus(null);
    }

    @Override
    public List<SysMenu> getCurrentUserMenus(String appCode) {

        // 获取当前用户所有的菜单id
        List<Long> menuIdList = getCurrentUserMenuIds();

        // 当前用户没有菜单
        if (menuIdList.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_HAVE_MENUS);
        }

        // 获取菜单列表
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getMenuId, menuIdList)
                .eq(SysMenu::getStatusFlag, StatusEnum.ENABLE.getCode())
                .eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode())
                .orderByAsc(SysMenu::getMenuSort);

        // 如果应用编码不为空，则拼接应用编码
        if (StrUtil.isNotBlank(appCode)) {
            queryWrapper.eq(SysMenu::getAppCode, appCode);
        }

        return this.list(queryWrapper);
    }

    @Override
    public List<LayuiAppIndexMenus> getLayuiIndexMenus() {

        // 获取当前用户所有菜单
        List<SysMenu> currentUserMenus = this.getCurrentUserMenus();

        // 组装每个应用的菜单树
        return LayuiMenusFactory.createLayuiAppIndexMenus(currentUserMenus);
    }

    @Override
    public List<AntdIndexMenuTreeNode> getAntDVueIndexMenus(String appCode) {

        // 获取当前用户的所有菜单
        List<SysMenu> currentUserMenus = this.getCurrentUserMenus(appCode);

        // 转换成登录菜单格式
        List<AntdIndexMenuTreeNode> antdIndexMenuTreeNodes = AntdMenusFactory.convertSysMenuToLoginMenu(currentUserMenus);

        // 转化成树结构
        return new DefaultTreeBuildFactory<AntdIndexMenuTreeNode>(SystemConstants.VIRTUAL_ROOT_PARENT_ID.toString()).doTreeBuild(antdIndexMenuTreeNodes);
    }

    @Override
    public List<MenuSelectTreeNode> tree(SysMenuRequest sysMenuRequest) {
        List<MenuSelectTreeNode> menuTreeNodeList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysMenu> wrapper = createWrapper(sysMenuRequest);
        this.list(wrapper).forEach(sysMenu -> {
            MenuSelectTreeNode menuTreeNode = CommonMenusFactory.parseMenuBaseTreeNode(sysMenu);
            menuTreeNodeList.add(menuTreeNode);
        });

        return new DefaultTreeBuildFactory<MenuSelectTreeNode>().doTreeBuild(menuTreeNodeList);
    }

    @Override
    public List<MenuSelectTreeNode> treeForGrant(SysMenuRequest sysMenuRequest) {
        List<MenuSelectTreeNode> menuTreeNodeList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysMenu> wrapper = createWrapper(sysMenuRequest);
        wrapper.eq(SysMenu::getStatusFlag, StatusEnum.ENABLE.getCode());

        // 非超级管理员则获取自己拥有的菜单，分配给人员，防止越级授权
        if (!LoginContext.me().getSuperAdminFlag()) {
            List<Long> menuIdList = getCurrentUserMenuIds();
            if (!menuIdList.isEmpty()) {
                wrapper.in(SysMenu::getMenuId, menuIdList);
            }
        }

        this.list(wrapper).forEach(sysMenu -> {
            MenuSelectTreeNode menuTreeNode = CommonMenusFactory.parseMenuBaseTreeNode(sysMenu);
            menuTreeNodeList.add(menuTreeNode);
        });

        return new DefaultTreeBuildFactory<MenuSelectTreeNode>().doTreeBuild(menuTreeNodeList);
    }

    @Override
    public boolean hasMenu(String appCode) {
        SysMenuRequest sysMenuRequest = new SysMenuRequest();
        sysMenuRequest.setAppCode(appCode);
        LambdaQueryWrapper<SysMenu> wrapper = this.createWrapper(sysMenuRequest);

        List<SysMenu> list = this.list(wrapper);
        return !list.isEmpty();
    }

    /**
     * 获取系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 9:13
     */
    private SysMenu querySysMenu(SysMenuRequest sysMenuRequest) {
        SysMenu sysMenu = this.getById(sysMenuRequest.getMenuId());
        if (ObjectUtil.isNull(sysMenu)) {
            throw new ServiceException(SysMenuExceptionEnum.MENU_NOT_EXIST);
        }
        return sysMenu;
    }

    /**
     * 创建pids的值
     * <p>
     * 如果pid是0顶级节点，pids就是 [0],
     * <p>
     * 如果pid不是顶级节点，pids就是父菜单的pids + [pid] + ,
     *
     * @author fengshuonan, stylefeng
     * @date 2020/3/26 11:28
     */
    private String createPids(Long pid) {
        if (pid.equals(SystemConstants.DEFAULT_PARENT_ID)) {
            return SymbolConstant.LEFT_SQUARE_BRACKETS + SystemConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS
                    + SymbolConstant.COMMA;
        } else {
            //获取父菜单
            SysMenu parentMenu = this.getById(pid);
            return parentMenu.getMenuPids()
                    + SymbolConstant.LEFT_SQUARE_BRACKETS + pid + SymbolConstant.RIGHT_SQUARE_BRACKETS
                    + SymbolConstant.COMMA;
        }
    }

    /**
     * 创建查询条件wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysMenu> createWrapper(SysMenuRequest sysMenuRequest) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysMenuRequest)) {

            // 根据所属应用查询
            if (ObjectUtil.isNotEmpty(sysMenuRequest.getAppCode())) {
                queryWrapper.eq(SysMenu::getAppCode, sysMenuRequest.getAppCode());
            }

            // 根据菜单名称模糊查询
            if (ObjectUtil.isNotEmpty(sysMenuRequest.getMenuName())) {
                queryWrapper.like(SysMenu::getMenuName, sysMenuRequest.getMenuName());
            }

            // 根据菜单编码模糊查询
            if (ObjectUtil.isNotEmpty(sysMenuRequest.getMenuCode())) {
                queryWrapper.like(SysMenu::getMenuCode, sysMenuRequest.getMenuCode());
            }
        }

        // 查询未删除状态的
        queryWrapper.eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysMenu::getAppCode);
        queryWrapper.orderByAsc(SysMenu::getMenuSort);

        return queryWrapper;
    }

    /**
     * 获取当前用户的菜单id集合
     *
     * @author fengshuonan
     * @date 2020/11/22 23:15
     */
    private List<Long> getCurrentUserMenuIds() {

        // 获取当前用户的角色id集合
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<Long> roleIdList = loginUser.getSimpleRoleInfoList().stream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toList());

        // 当前用户角色为空，则没菜单
        if (ObjectUtil.isEmpty(roleIdList)) {
            return CollectionUtil.newArrayList();
        }

        // 获取角色拥有的菜单id集合
        List<Long> menuIdList = roleServiceApi.getMenuIdsByRoleIds(roleIdList);
        if (ObjectUtil.isEmpty(menuIdList)) {
            return CollectionUtil.newArrayList();
        }

        return menuIdList;
    }

    /**
     * 更新子节点以及子节点的子节点的appCode和层级关系（pids）
     *
     * @author fengshuonan
     * @date 2020/11/23 22:05
     */
    private String updateChildrenAppAndLevel(SysMenuRequest sysMenuRequest, SysMenu oldMenu) {

        // 本菜单旧的pids
        Long oldPid = oldMenu.getMenuParentId();
        String oldPids = oldMenu.getMenuPids();

        // 生成新的pid和pids
        Long newPid = sysMenuRequest.getMenuParentId();
        String newPids = this.createPids(sysMenuRequest.getMenuParentId());

        // 是否更新子应用的标识
        boolean updateSubAppsFlag = false;

        // 是否更新子节点的pids的标识
        boolean updateSubPidsFlag = false;

        // 如果应用有变化，不能把非一级菜单转移应用
        if (!sysMenuRequest.getAppCode().equals(oldMenu.getAppCode())) {
            if (!oldPid.equals(SystemConstants.DEFAULT_PARENT_ID)) {
                throw new ServiceException(SysMenuExceptionEnum.CANT_MOVE_APP);
            }
            updateSubAppsFlag = true;
        }

        // 父节点有变化
        if (!newPid.equals(oldPid)) {
            updateSubPidsFlag = true;
        }

        // 开始更新所有子节点的配置
        if (updateSubAppsFlag || updateSubPidsFlag) {

            // 查找所有叶子节点，包含子节点的子节点
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(SysMenu::getMenuPids, oldMenu.getMenuId());
            List<SysMenu> list = this.list(queryWrapper);

            // 更新所有子节点的应用为当前菜单的应用
            if (ObjectUtil.isNotEmpty(list)) {

                // 更新所有子节点的application
                if (updateSubAppsFlag) {
                    list.forEach(child -> child.setAppCode(sysMenuRequest.getAppCode()));
                }

                // 更新所有子节点的pids
                if (updateSubPidsFlag) {
                    list.forEach(child -> {
                        // 子节点pids组成 = 当前菜单新pids + 当前菜单id + 子节点自己的pids后缀
                        String oldParentCodesPrefix = oldPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId()
                                + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                        String oldParentCodesSuffix = child.getMenuPids().substring(oldParentCodesPrefix.length());
                        String menuParentCodes = newPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId()
                                + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA + oldParentCodesSuffix;
                        child.setMenuPids(menuParentCodes);
                    });
                }

                this.updateBatchById(list);
            }
        }
        return newPids;
    }

}
