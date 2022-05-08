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
package cn.stylefeng.roses.kernel.system.modular.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.api.AppServiceApi;
import cn.stylefeng.roses.kernel.system.api.MenuServiceApi;
import cn.stylefeng.roses.kernel.system.api.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.api.enums.MenuFrontTypeEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.menu.SysMenuExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.app.SysAppResult;
import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexMenuInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.MenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiAppIndexMenusVO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiMenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuButtonDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuResource;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.AntdMenusFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.Antdv3MenusFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.LayuiMenusFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.MenuTypeFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.mapper.SysMenuMapper;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuButtonService;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuResourceService;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private SysMenuButtonService sysMenuButtonService;

    @Resource
    private SysMenuResourceService sysMenuResourceService;

    @Override
    public void add(SysMenuRequest sysMenuRequest) {

        // 如果父节点为空，则填充为默认的父节点id
        if (sysMenuRequest.getMenuParentId() == null) {
            sysMenuRequest.setMenuParentId(TreeConstants.DEFAULT_PARENT_ID);
        }

        // 如果父节点不为空，并且不是-1，则判断父节点存不存在，防止脏数据
        else {
            if (!TreeConstants.DEFAULT_PARENT_ID.equals(sysMenuRequest.getMenuParentId())) {
                SysMenuRequest tempParam = new SysMenuRequest();
                tempParam.setMenuId(sysMenuRequest.getMenuParentId());
                this.querySysMenu(tempParam);
            }
        }

        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuRequest, sysMenu);

        // 组装pids
        String newPids = createPids(sysMenuRequest.getMenuParentId());
        sysMenu.setMenuPids(newPids);

        // 设置启用状态
        sysMenu.setStatusFlag(StatusEnum.ENABLE.getCode());
        sysMenu.setDelFlag(YesOrNotEnum.N.getCode());

        // 设置添加的菜单的类型
        MenuTypeFactory.processMenuType(sysMenu, sysMenuRequest.getVisible());

        // 设置如果菜单前后台类型如果为空，则默认为都显示
        if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvFrontType())) {
            sysMenu.setAntdvFrontType(MenuFrontTypeEnum.TOTAL.getCode());
        }

        this.save(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(SysMenuRequest sysMenuRequest) {

        Long id = sysMenuRequest.getMenuId();

        // 获取所有子级的节点id
        Set<Long> childIdList = this.dbOperatorApi.findSubListByParentId("sys_menu", "menu_pids", "menu_id", id);
        childIdList.add(id);

        // 逻辑删除，设置删除标识
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SysMenu::getMenuId, childIdList).set(SysMenu::getDelFlag, YesOrNotEnum.Y.getCode());
        this.update(updateWrapper);

        // 删除该菜单下的按钮
        sysMenuButtonService.deleteMenuButtonByMenuId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysMenuRequest sysMenuRequest) {

        // 获取库中的菜单信息
        SysMenu oldMenu = this.querySysMenu(sysMenuRequest);

        // 更新子节点以及子节点的子节点的appCode和层级关系（pids）
        String newPids = updateChildrenAppAndLevel(sysMenuRequest, oldMenu);

        // 拷贝参数到实体中
        BeanUtil.copyProperties(sysMenuRequest, oldMenu);

        // 设置新的pids
        oldMenu.setMenuPids(newPids);

        // 不能修改状态，用修改状态接口修改状态
        oldMenu.setStatusFlag(null);

        // 设置添加的菜单的类型
        MenuTypeFactory.processMenuType(oldMenu, sysMenuRequest.getVisible());

        this.updateById(oldMenu);
    }

    @Override
    public SysMenu detail(SysMenuRequest sysMenuRequest) {
        SysMenu sysMenu = this.querySysMenu(sysMenuRequest);

        // 设置父级菜单名称
        if (sysMenu != null) {
            Long menuParentId = sysMenu.getMenuParentId();
            if (TreeConstants.DEFAULT_PARENT_ID.equals(menuParentId)) {
                sysMenu.setMenuParentName("顶级");
            } else {
                Long parentId = sysMenu.getMenuParentId();
                SysMenu parentMenu = this.getById(parentId);
                if (parentMenu == null) {
                    sysMenu.setMenuParentName("无");
                } else {
                    sysMenu.setMenuParentName(parentMenu.getMenuName());
                }
            }
        }

        return sysMenu;
    }

    @Override
    public List<SysMenu> findList(SysMenuRequest sysMenuRequest) {

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
    public List<SysMenu> findListWithTreeStructure(SysMenuRequest sysMenuRequest) {

        List<SysMenu> sysMenuList = this.findList(sysMenuRequest);

        // 遍历菜单，设置是否是叶子节点属性
        AntdMenusFactory.fillLeafFlag(sysMenuList);

        // 将结果集处理成树
        return new DefaultTreeBuildFactory<SysMenu>().doTreeBuild(sysMenuList);
    }

    @Override
    public List<ZTreeNode> layuiSelectParentMenuTreeList() {

        ArrayList<ZTreeNode> zTreeNodes = new ArrayList<>();

        List<SysMenu> allMenus = this.list();
        for (SysMenu sysMenu : allMenus) {
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(sysMenu.getMenuId());
            zTreeNode.setpId(sysMenu.getMenuParentId());
            zTreeNode.setName(sysMenu.getMenuName());
            zTreeNode.setOpen(false);
            zTreeNodes.add(zTreeNode);
        }

        // 创建顶级节点
        zTreeNodes.add(ZTreeNode.createParent());

        return zTreeNodes;
    }

    @Override
    public List<AntdMenuSelectTreeNode> tree(SysMenuRequest sysMenuRequest) {
        List<AntdMenuSelectTreeNode> menuTreeNodeList = CollectionUtil.newArrayList();

        // 添加根节点
        AntdMenuSelectTreeNode rootNode = AntdMenusFactory.createRootNode();
        menuTreeNodeList.add(rootNode);

        LambdaQueryWrapper<SysMenu> wrapper = createWrapper(sysMenuRequest);
        this.list(wrapper).forEach(sysMenu -> {
            AntdMenuSelectTreeNode menuTreeNode = AntdMenusFactory.parseMenuBaseTreeNode(sysMenu);
            menuTreeNodeList.add(menuTreeNode);
        });

        // -2是根节点的上级
        return new DefaultTreeBuildFactory<AntdMenuSelectTreeNode>("-2").doTreeBuild(menuTreeNodeList);
    }

    @Override
    public List<LayuiAppIndexMenusVO> getLayuiIndexMenus() {

        // 获取当前用户所有菜单
        List<SysMenu> currentUserMenus = this.getCurrentUserMenus(null, true, null);

        // 组装每个应用的菜单树
        List<LayuiAppIndexMenusVO> layuiAppIndexMenuVOS = LayuiMenusFactory.createLayuiAppIndexMenus(currentUserMenus);

        // 给应用排序，激活的应用放在前边
        String activeAppCode = appServiceApi.getActiveAppCode();
        if (activeAppCode != null) {
            List<LayuiAppIndexMenusVO> layuiAppIndexMenusVOArrayList = layuiAppIndexMenuVOS.stream().filter(i -> activeAppCode.equals(i.getAppCode())).collect(Collectors.toList());
            layuiAppIndexMenusVOArrayList.addAll(layuiAppIndexMenuVOS.stream().filter(i -> !activeAppCode.equals(i.getAppCode())).collect(Collectors.toList()));
            return layuiAppIndexMenusVOArrayList;
        }

        return layuiAppIndexMenuVOS;
    }

    @Override
    public List<AntdSysMenuDTO> getLeftMenusAntdv(SysMenuRequest sysMenuRequest) {

        // 获取前台或者后台类型
        Integer antdvFrontType = sysMenuRequest.getAntdvFrontType();
        if (antdvFrontType == null) {
            antdvFrontType = MenuFrontTypeEnum.FRONT.getCode();
        }

        // 获取当前已经启用的应用，并且按排序字段排序的
        List<SysAppResult> appNameSorted = appServiceApi.getSortedApps();
        if (ObjectUtil.isEmpty(appNameSorted)) {
            throw new ServiceException(SysMenuExceptionEnum.CANT_FIND_APPS);
        }

        // 查询菜单
        List<String> appCodes = appNameSorted.stream().map(SysAppResult::getAppCode).collect(Collectors.toList());
        List<SysMenu> currentUserMenus = this.getCurrentUserMenus(appCodes, false, antdvFrontType);

        // 将菜单按应用编码分类
        Map<String, List<SysMenu>> sortedUserMenus = AntdMenusFactory.sortUserMenusByAppCode(currentUserMenus);

        // 获取应用名称集合，带顺序
        List<String> appNames = appNameSorted.stream().map(SysAppResult::getAppName).collect(Collectors.toList());
        return AntdMenusFactory.createTotalMenus(sortedUserMenus, appNames);
    }

    @Override
    public List<LayuiMenuAndButtonTreeResponse> getMenuAndButtonTree(SysRoleRequest sysRoleRequest, Boolean lateralFlag) {
        List<LayuiMenuAndButtonTreeResponse> menuTreeNodeList = CollectionUtil.newArrayList();

        // 查询未删除的启用的菜单列表
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.eq(SysMenu::getStatusFlag, StatusEnum.ENABLE.getCode());
        menuWrapper.eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode());

        // 非超级管理员则获取自己拥有的菜单
        if (!LoginContext.me().getSuperAdminFlag()) {
            List<Long> menuIdList = getCurrentUserMenuIds();
            if (!menuIdList.isEmpty()) {
                menuWrapper.in(SysMenu::getMenuId, menuIdList);
            }
        }
        List<SysMenu> sysMenuList = this.list(menuWrapper);

        // 获取菜单的id集合
        List<Long> menuList = sysMenuList.parallelStream().map(SysMenu::getMenuId).collect(Collectors.toList());

        // 查询这些菜单对应的所有按钮
        LambdaQueryWrapper<SysMenuButton> buttonWrapper = new LambdaQueryWrapper<>();
        buttonWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        buttonWrapper.in(SysMenuButton::getMenuId, menuList);
        List<SysMenuButton> sysMenuButtons = sysMenuButtonService.list(buttonWrapper);

        // 把按钮按照菜单id存起来，方便后续操作
        Map<Long, List<SysMenuButton>> buttons = new HashMap<>(menuList.size());
        for (SysMenuButton menuButton : sysMenuButtons) {
            List<SysMenuButton> buttonList = buttons.get(menuButton.getMenuId());
            if (ObjectUtil.isEmpty(buttonList)) {
                buttonList = new ArrayList<>();
                buttons.put(menuButton.getMenuId(), buttonList);
            }
            buttonList.add(menuButton);
        }

        // 查询所有已有的权限
        // 所有已有的菜单权限
        List<SysRoleMenuDTO> roleMenuList = roleServiceApi.getRoleMenuList(Collections.singletonList(sysRoleRequest.getRoleId()));

        // 转换成map方便后续处理
        Map<Long, SysRoleMenuDTO> roleMenuMap = new HashMap<>();
        for (SysRoleMenuDTO sysRoleMenuResponse : roleMenuList) {
            roleMenuMap.put(sysRoleMenuResponse.getMenuId(), sysRoleMenuResponse);
        }

        // 所有的按钮权限
        List<SysRoleMenuButtonDTO> roleMenuButtonList = roleServiceApi.getRoleMenuButtonList(Collections.singletonList(sysRoleRequest.getRoleId()));

        // 转换成map方便后续处理
        Map<Long, SysRoleMenuButtonDTO> roleMenuButtonMap = new HashMap<>();
        for (SysRoleMenuButtonDTO buttonResponse : roleMenuButtonList) {
            roleMenuButtonMap.put(buttonResponse.getButtonId(), buttonResponse);
        }

        // 组装树结果
        for (SysMenu sysMenu : sysMenuList) {
            LayuiMenuAndButtonTreeResponse menuTree = new LayuiMenuAndButtonTreeResponse();
            menuTree.setId(sysMenu.getMenuId());
            menuTree.setMenuFlag(true);
            menuTree.setName(sysMenu.getMenuName());
            menuTree.setPid(sysMenu.getMenuParentId());
            // 判断是否已经有了
            SysRoleMenuDTO roleMenuResponse = roleMenuMap.get(sysMenu.getMenuId());
            menuTree.setChecked(!ObjectUtil.isEmpty(roleMenuResponse));

            // 处理该菜单的按钮
            List<SysMenuButton> menuButtons = buttons.get(sysMenu.getMenuId());

            // 不为空就去处理
            if (ObjectUtil.isNotEmpty(menuButtons)) {
                for (SysMenuButton menuButton : menuButtons) {
                    LayuiMenuAndButtonTreeResponse buttonTree = new LayuiMenuAndButtonTreeResponse();
                    buttonTree.setName(menuButton.getButtonName());
                    buttonTree.setId(menuButton.getButtonId());
                    buttonTree.setPid(menuButton.getMenuId());
                    buttonTree.setButtonCode(menuButton.getButtonCode());
                    buttonTree.setMenuFlag(false);
                    // 判断是否已经拥有
                    SysRoleMenuButtonDTO buttonResponse = roleMenuButtonMap.get(menuButton.getButtonId());
                    if (ObjectUtil.isNotEmpty(buttonResponse)) {
                        buttonTree.setChecked(true);
                        menuTree.setChecked(true);
                    } else {
                        buttonTree.setChecked(false);
                    }
                    // 记录按钮节点
                    menuTreeNodeList.add(buttonTree);
                }
            }
            // 记录菜单节点
            menuTreeNodeList.add(menuTree);
        }

        // 返回结果
        if (lateralFlag) {
            return menuTreeNodeList;
        } else {
            return new DefaultTreeBuildFactory<LayuiMenuAndButtonTreeResponse>().doTreeBuild(menuTreeNodeList);
        }
    }

    @Override
    public List<MenuAndButtonTreeResponse> getRoleMenuAndButtons(SysRoleRequest sysRoleRequest) {
        List<MenuAndButtonTreeResponse> menuTreeNodeList = CollectionUtil.newArrayList();

        // 查询未删除的启用的菜单列表
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode());
        menuWrapper.eq(SysMenu::getStatusFlag, StatusEnum.ENABLE.getCode());

        // 非超级管理员则获取自己拥有的菜单
        if (!LoginContext.me().getSuperAdminFlag()) {
            List<Long> menuIdList = getCurrentUserMenuIds();
            if (!menuIdList.isEmpty()) {
                menuWrapper.in(SysMenu::getMenuId, menuIdList);
            }
        }

        // 查询所有菜单列表
        List<SysMenu> sysMenuList = this.list(menuWrapper);

        // 获取菜单的id集合
        List<Long> menuIdList = sysMenuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());

        // 获取角色绑定的菜单的id
        List<SysRoleMenuDTO> roleMenuList = roleServiceApi.getRoleMenuList(Collections.singletonList(sysRoleRequest.getRoleId()));

        // 转化 --->>> 菜单列表自转化为响应的节点类型
        List<MenuAndButtonTreeResponse> menuAndButtonTreeResponses = AntdMenusFactory.parseMenuAndButtonTreeResponse(sysMenuList, roleMenuList);

        // 查询这些菜单对应的所有按钮
        LambdaQueryWrapper<SysMenuButton> buttonWrapper = new LambdaQueryWrapper<>();
        buttonWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        buttonWrapper.in(SysMenuButton::getMenuId, menuIdList);
        List<SysMenuButton> buttonList = sysMenuButtonService.list(buttonWrapper);

        // 获取角色绑定的按钮的id
        List<SysRoleMenuButtonDTO> roleMenuButtonList = roleServiceApi.getRoleMenuButtonList(Collections.singletonList(sysRoleRequest.getRoleId()));

        // 转化 --->>> 将这些按钮，分别绑定在各自的菜单上
        AntdMenusFactory.fillButtons(menuAndButtonTreeResponses, buttonList, roleMenuButtonList);

        // 菜单列表转化为一棵树
        return new DefaultTreeBuildFactory<MenuAndButtonTreeResponse>().doTreeBuild(menuAndButtonTreeResponses);
    }

    @Override
    public List<SysMenu> getCurrentUserMenus(List<String> appCodeList, Boolean layuiVisibleFlag, Integer antdvFrontType) {

        // 菜单查询条件
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getStatusFlag, StatusEnum.ENABLE.getCode()).eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode()).orderByAsc(SysMenu::getMenuSort);

        // 如果应用编码不为空，则拼接应用编码
        if (ObjectUtil.isNotEmpty(appCodeList)) {
            queryWrapper.in(SysMenu::getAppCode, appCodeList);
        }

        // 判断前台还是后台菜单
        if (ObjectUtil.isNotEmpty(antdvFrontType)) {
            queryWrapper.in(SysMenu::getAntdvFrontType, ListUtil.list(true, antdvFrontType, MenuFrontTypeEnum.TOTAL.getCode()));
        }

        // 如果是不分离版本，则筛选一下不需要显示的菜单
        if (layuiVisibleFlag != null && layuiVisibleFlag) {
            queryWrapper.eq(SysMenu::getLayuiVisible, YesOrNotEnum.Y.getCode());
        }

        // 如果是超级管理员，则获取所有的菜单
        if (LoginContext.me().getSuperAdminFlag()) {
            return this.list(queryWrapper);
        }

        // 非超级管理员，获取当前用户所有的菜单id
        List<Long> menuIdList = getCurrentUserMenuIds();
        if (menuIdList.isEmpty()) {
            return new ArrayList<>();
        }
        queryWrapper.in(SysMenu::getMenuId, menuIdList);

        return this.list(queryWrapper);
    }

    @Override
    public boolean hasMenu(String appCode) {
        SysMenuRequest sysMenuRequest = new SysMenuRequest();
        sysMenuRequest.setAppCode(appCode);
        LambdaQueryWrapper<SysMenu> wrapper = this.createWrapper(sysMenuRequest);

        List<SysMenu> list = this.list(wrapper);
        return !list.isEmpty();
    }

    @Override
    public List<String> getUserAppCodeList() {

        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysMenu::getAppCode);
        queryWrapper.groupBy(SysMenu::getAppCode);

        // 非超级管理员获取自己的菜单
        if (!LoginContext.me().getSuperAdminFlag()) {
            List<Long> currentUserMenuIds = this.getCurrentUserMenuIds();
            queryWrapper.in(SysMenu::getMenuId, currentUserMenuIds);
        }

        List<SysMenu> list = this.list(queryWrapper);
        return list.stream().map(SysMenu::getAppCode).collect(Collectors.toList());
    }

    @Override
    public Set<Long> getMenuAllParentMenuId(Set<Long> menuIds) {
        Set<Long> parentMenuIds = new HashSet<>();

        // 查询所有菜单信息
        List<SysMenu> sysMenus = this.listByIds(menuIds);
        if (ObjectUtil.isEmpty(sysMenus)) {
            return parentMenuIds;
        }

        // 获取所有父菜单ID
        for (SysMenu sysMenu : sysMenus) {
            String menuPids = sysMenu.getMenuPids().replaceAll("\\[", "").replaceAll("\\]", "");
            String[] ids = menuPids.split(SymbolConstant.COMMA);
            for (String id : ids) {
                parentMenuIds.add(Long.parseLong(id));
            }
        }

        return parentMenuIds;
    }

    @Override
    public String getMenuButtonCodeByButtonId(Long buttonId) {
        SysMenuButton sysMenuButton = this.sysMenuButtonService.getById(buttonId);
        if (sysMenuButton != null) {
            return sysMenuButton.getButtonCode();
        } else {
            return "";
        }
    }

    @Override
    public List<String> getResourceCodesByBusinessId(List<Long> businessIds) {
        if (ObjectUtil.isEmpty(businessIds)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<SysMenuResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysMenuResource::getBusinessId, businessIds);
        wrapper.select(SysMenuResource::getResourceCode);
        List<SysMenuResource> list = sysMenuResourceService.list(wrapper);

        return list.stream().map(SysMenuResource::getResourceCode).collect(Collectors.toList());
    }

    @Override
    public List<IndexMenuInfo> buildAuthorities(Integer menuFrontType) {

        // 不分离应用查询菜单
        List<SysAppResult> sortedApps = appServiceApi.getSortedApps();
        List<String> appCodes = sortedApps.stream().map(SysAppResult::getAppCode).collect(Collectors.toList());
        List<SysMenu> currentUserMenus = this.getCurrentUserMenus(appCodes, false, menuFrontType);

        // 获取当前激活的应用
        List<String> appNames = sortedApps.stream().map(SysAppResult::getAppName).collect(Collectors.toList());

        // 将菜单按应用编码分类，激活的应用放在最前边
        Map<String, List<SysMenu>> sortedUserMenus = AntdMenusFactory.sortUserMenusByAppCode(currentUserMenus);

        return Antdv3MenusFactory.createTotalMenus(sortedUserMenus, appNames);
    }

    /**
     * 获取系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 9:13
     */
    private SysMenu querySysMenu(SysMenuRequest sysMenuRequest) {
        SysMenu sysMenu = this.getById(sysMenuRequest.getMenuId());
        if (ObjectUtil.isNull(sysMenu) || YesOrNotEnum.Y.getCode().equals(sysMenu.getDelFlag())) {
            throw new SystemModularException(SysMenuExceptionEnum.MENU_NOT_EXIST, sysMenuRequest.getMenuId());
        }
        return sysMenu;
    }

    /**
     * 创建pids的值
     * <p>
     * 如果pid是0顶级节点，pids就是 [-1],
     * <p>
     * 如果pid不是顶级节点，pids就是父菜单的pids + [pid] + ,
     *
     * @author fengshuonan, stylefeng
     * @date 2020/3/26 11:28
     */
    private String createPids(Long pid) {
        if (pid.equals(TreeConstants.DEFAULT_PARENT_ID)) {
            return SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
        } else {
            //获取父菜单
            SysMenuRequest sysMenuRequest = new SysMenuRequest();
            sysMenuRequest.setMenuId(pid);
            SysMenu parentMenu = this.querySysMenu(sysMenuRequest);

            // 组装pids
            return parentMenu.getMenuPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + pid + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
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

        // 查询未删除状态的
        queryWrapper.eq(SysMenu::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysMenu::getAppCode);
        queryWrapper.orderByAsc(SysMenu::getMenuSort);

        if (ObjectUtil.isEmpty(sysMenuRequest)) {
            return queryWrapper;
        }

        if (ObjectUtil.isNotEmpty(sysMenuRequest.getAppCode()) || ObjectUtil.isNotEmpty(sysMenuRequest.getMenuName()) || ObjectUtil.isNotEmpty(sysMenuRequest.getMenuCode())) {
            queryWrapper.nested(
                    // 根据所属应用查询
                    i -> i.like(ObjectUtil.isNotEmpty(sysMenuRequest.getAppCode()), SysMenu::getAppCode, sysMenuRequest.getAppCode()).or()
                            // 根据菜单名称模糊查询
                            .like(ObjectUtil.isNotEmpty(sysMenuRequest.getMenuName()), SysMenu::getMenuName, sysMenuRequest.getMenuName()).or()
                            // 根据菜单编码模糊查询
                            .like(ObjectUtil.isNotEmpty(sysMenuRequest.getMenuCode()), SysMenu::getMenuCode, sysMenuRequest.getMenuCode()));
        }

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
            if (!oldPid.equals(TreeConstants.DEFAULT_PARENT_ID)) {
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
                        String oldParentCodesPrefix = oldPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                        String oldParentCodesSuffix = child.getMenuPids().substring(oldParentCodesPrefix.length());
                        String menuParentCodes = newPids + SymbolConstant.LEFT_SQUARE_BRACKETS + oldMenu.getMenuId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA + oldParentCodesSuffix;
                        child.setMenuPids(menuParentCodes);
                    });
                }

                this.updateBatchById(list);
            }
        }
        return newPids;
    }

}
