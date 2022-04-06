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
package cn.stylefeng.roses.kernel.system.modular.menu.factory;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.api.AppServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.app.SysAppResult;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.MenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuButtonDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuDTO;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对于antd vue版本的前端菜单的组装
 *
 * @author fengshuonan
 * @date 2020/12/30 20:11
 */
public class AntdMenusFactory {

    /**
     * 组装antdv用的获取所有菜单列表详情
     *
     * @param appSortedMenus 按应用排序过的菜单集合
     * @param appNames       排序过的应用名称
     * @author fengshuonan
     * @date 2021/1/7 18:17
     */
    public static List<AntdSysMenuDTO> createTotalMenus(Map<String, List<SysMenu>> appSortedMenus, List<String> appNames) {

        // 创建应用级别的菜单集合
        ArrayList<AntdSysMenuDTO> appSortedAntdMenus = new ArrayList<>();

        // 创建其他应用的菜单
        for (Map.Entry<String, List<SysMenu>> entry : appSortedMenus.entrySet()) {
            // 创建顶层应用菜单
            AntdSysMenuDTO rootAppMenu = createRootAppMenu(entry.getKey());
            List<SysMenu> treeStructMenu = new DefaultTreeBuildFactory<SysMenu>(TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(entry.getValue());
            List<AntdSysMenuDTO> antdSysMenuDTOS = doModelTransfer(treeStructMenu);

            // 更新顶层应用级别的菜单
            rootAppMenu.setChildren(antdSysMenuDTOS);
            appSortedAntdMenus.add(rootAppMenu);
        }

        // 更新排序
        if (ObjectUtil.isEmpty(appNames)) {
            return appSortedAntdMenus;
        }
        appSortedAntdMenus.sort((antdSysMenuDTO, antdSysMenuDTO2) -> {
            int one = appNames.indexOf(antdSysMenuDTO.getTitle());
            int two = appNames.indexOf(antdSysMenuDTO2.getTitle());
            return Integer.compare(one, two);
        });

        return appSortedAntdMenus;
    }

    /**
     * menu实体转化为菜单树节点
     *
     * @author fengshuonan
     * @date 2020/11/23 21:54
     */
    public static AntdMenuSelectTreeNode parseMenuBaseTreeNode(SysMenu sysMenu) {
        AntdMenuSelectTreeNode menuTreeNode = new AntdMenuSelectTreeNode();
        menuTreeNode.setId(sysMenu.getMenuId());
        menuTreeNode.setParentId(sysMenu.getMenuParentId());
        menuTreeNode.setValue(String.valueOf(sysMenu.getMenuId()));
        menuTreeNode.setTitle(sysMenu.getMenuName());
        menuTreeNode.setWeight(sysMenu.getMenuSort());
        return menuTreeNode;
    }

    /**
     * 添加根节点
     *
     * @author fengshuonan
     * @date 2021/4/16 15:52
     */
    public static AntdMenuSelectTreeNode createRootNode() {
        AntdMenuSelectTreeNode antdMenuSelectTreeNode = new AntdMenuSelectTreeNode();
        antdMenuSelectTreeNode.setId(-1L);
        antdMenuSelectTreeNode.setParentId(-2L);
        antdMenuSelectTreeNode.setTitle("根节点");
        antdMenuSelectTreeNode.setValue(String.valueOf(antdMenuSelectTreeNode.getId()));
        antdMenuSelectTreeNode.setWeight(new BigDecimal(-1));
        return antdMenuSelectTreeNode;
    }

    /**
     * 填充叶子节点的标识
     *
     * @author fengshuonan
     * @date 2021/8/8 15:22
     */
    public static void fillLeafFlag(List<SysMenu> sysMenuList) {
        for (SysMenu sysMenu : sysMenuList) {
            sysMenu.setLeafFlag(true);

            // 判断这个节点下面有没有节点
            for (SysMenu tempMenu : sysMenuList) {
                if (tempMenu.getMenuPids().contains("[" + sysMenu.getMenuId() + "]")) {
                    sysMenu.setLeafFlag(false);
                }
            }
        }
    }

    /**
     * 菜单集合转化成角色分配菜单的集合
     *
     * @author fengshuonan
     * @date 2021/8/10 22:56
     */
    public static List<MenuAndButtonTreeResponse> parseMenuAndButtonTreeResponse(List<SysMenu> sysMenuList, List<SysRoleMenuDTO> roleBindMenus) {
        ArrayList<MenuAndButtonTreeResponse> result = new ArrayList<>();

        if (ObjectUtil.isEmpty(sysMenuList)) {
            return result;
        }

        for (SysMenu sysMenu : sysMenuList) {
            MenuAndButtonTreeResponse menuAndButtonTreeResponse = new MenuAndButtonTreeResponse();
            menuAndButtonTreeResponse.setId(sysMenu.getMenuId());
            menuAndButtonTreeResponse.setName(sysMenu.getMenuName());
            menuAndButtonTreeResponse.setCode(sysMenu.getMenuCode());
            menuAndButtonTreeResponse.setPid(sysMenu.getMenuParentId());
            menuAndButtonTreeResponse.setChecked(false);

            if (ObjectUtil.isNotEmpty(roleBindMenus)) {
                for (SysRoleMenuDTO roleBindMenu : roleBindMenus) {
                    if (roleBindMenu.getMenuId().equals(sysMenu.getMenuId())) {
                        menuAndButtonTreeResponse.setChecked(true);
                    }
                }
            }

            result.add(menuAndButtonTreeResponse);
        }

        return result;
    }

    /**
     * 菜单集合转化成角色分配菜单的集合
     *
     * @author fengshuonan
     * @date 2021/8/10 22:56
     */
    public static void fillButtons(List<MenuAndButtonTreeResponse> sysMenuList, List<SysMenuButton> buttonList, List<SysRoleMenuButtonDTO> roleMenuButtonList) {
        for (MenuAndButtonTreeResponse menuAndButtonTreeResponse : sysMenuList) {
            if (ObjectUtil.isEmpty(buttonList)) {
                continue;
            }

            ArrayList<MenuAndButtonTreeResponse> menuButtonList = new ArrayList<>();

            for (SysMenuButton sysMenuButton : buttonList) {
                if (menuAndButtonTreeResponse.getId().equals(sysMenuButton.getMenuId())) {
                    MenuAndButtonTreeResponse buttonInfo = new MenuAndButtonTreeResponse();
                    buttonInfo.setId(sysMenuButton.getButtonId());
                    buttonInfo.setName(sysMenuButton.getButtonName());
                    buttonInfo.setCode(sysMenuButton.getButtonCode());
                    buttonInfo.setChecked(false);

                    if (ObjectUtil.isNotEmpty(roleMenuButtonList)) {
                        for (SysRoleMenuButtonDTO sysRoleMenuButtonDTO : roleMenuButtonList) {
                            if (sysRoleMenuButtonDTO.getButtonId().equals(sysMenuButton.getButtonId())) {
                                buttonInfo.setChecked(true);
                            }
                        }
                    }

                    menuButtonList.add(buttonInfo);
                }
            }

            menuAndButtonTreeResponse.setButtons(menuButtonList);
        }
    }

    /**
     * 获取分类过的用户菜单，返回一个menus数组，并且第一个是激活的应用
     *
     * @author fengshuonan
     * @date 2021/8/24 16:50
     */
    public static Map<String, List<SysMenu>> sortUserMenusByAppCode(List<SysMenu> currentUserMenus) {

        // 根据应用编码分类的菜单，key是应用编码，value是菜单
        HashMap<String, List<SysMenu>> appMenus = new HashMap<>();

        // 将菜单分类
        for (SysMenu currentUserMenu : currentUserMenus) {

            // 获取这个菜单的应用编码
            String appCode = currentUserMenu.getAppCode();

            // 获取该应用已有的菜单集合
            List<SysMenu> sysMenus = appMenus.get(appCode);
            if (sysMenus == null) {
                sysMenus = new ArrayList<>();
            }

            sysMenus.add(currentUserMenu);
            appMenus.put(appCode, sysMenus);
        }

        return appMenus;
    }

    /**
     * 模型转化
     *
     * @author fengshuonan
     * @date 2021/3/23 21:40
     */
    private static List<AntdSysMenuDTO> doModelTransfer(List<SysMenu> sysMenuList) {
        if (ObjectUtil.isEmpty(sysMenuList)) {
            return null;
        } else {
            ArrayList<AntdSysMenuDTO> resultMenus = new ArrayList<>();

            for (SysMenu sysMenu : sysMenuList) {
                AntdSysMenuDTO antdvMenuItem = new AntdSysMenuDTO();
                antdvMenuItem.setTitle(sysMenu.getMenuName());
                antdvMenuItem.setIcon(sysMenu.getAntdvIcon());
                antdvMenuItem.setPath(sysMenu.getAntdvRouter());
                antdvMenuItem.setComponent(sysMenu.getAntdvComponent());
                antdvMenuItem.setHide(YesOrNotEnum.N.getCode().equals(sysMenu.getAntdvVisible()));
                antdvMenuItem.setActive(sysMenu.getAntdvActiveUrl());
                if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                    antdvMenuItem.setChildren(doModelTransfer(sysMenu.getChildren()));
                }
                resultMenus.add(antdvMenuItem);
            }

            return resultMenus;
        }
    }

    /**
     * 创建顶层应用层级的菜单
     *
     * @author fengshuonan
     * @date 2021/8/24 17:23
     */
    private static AntdSysMenuDTO createRootAppMenu(String appCode) {

        AntdSysMenuDTO antdSysMenuDTO = new AntdSysMenuDTO();

        // 获取应用的详细信息
        AppServiceApi appServiceApi = SpringUtil.getBean(AppServiceApi.class);
        SysAppResult appInfoByAppCode = appServiceApi.getAppInfoByAppCode(appCode);
        antdSysMenuDTO.setTitle(appInfoByAppCode.getAppName());
        antdSysMenuDTO.setIcon(appInfoByAppCode.getAppIcon());
        antdSysMenuDTO.setPath("/" + appCode);
        antdSysMenuDTO.setComponent(null);
        antdSysMenuDTO.setHide(false);
        antdSysMenuDTO.setActive(null);

        return antdSysMenuDTO;
    }

}
