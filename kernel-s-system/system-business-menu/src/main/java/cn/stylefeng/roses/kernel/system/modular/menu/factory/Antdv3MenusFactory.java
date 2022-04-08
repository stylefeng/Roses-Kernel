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
import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexMenuInfo;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 针对于antd vue版本的前端菜单的组装
 *
 * @author fengshuonan
 * @date 2020/12/30 20:11
 */
public class Antdv3MenusFactory {

    /**
     * 组装antdv用的获取所有菜单列表详情
     *
     * @param appSortedMenus 按应用排序过的菜单集合
     * @param appNames       排序过的应用名称
     * @author fengshuonan
     * @date 2021/1/7 18:17
     */
    public static List<IndexMenuInfo> createTotalMenus(Map<String, List<SysMenu>> appSortedMenus, List<String> appNames) {

        // 创建应用级别的菜单集合
        List<IndexMenuInfo> appSortedAntdMenus = new ArrayList<>();

        // 当前系统没有应用，则直接返回
        if (ObjectUtil.isEmpty(appNames)) {
            return appSortedAntdMenus;
        }

        // 创建其他应用的菜单
        for (Map.Entry<String, List<SysMenu>> entry : appSortedMenus.entrySet()) {
            // 创建顶层应用菜单
            IndexMenuInfo rootAppMenu = createRootAppMenu(entry.getKey());
            List<SysMenu> treeStructMenu = new DefaultTreeBuildFactory<SysMenu>(TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(entry.getValue());
            List<IndexMenuInfo> antdSysMenuDTOS = doModelReBuild(treeStructMenu);

            // 如果顶层应用下有菜单，则设为显示
            if (ObjectUtil.isNotEmpty(antdSysMenuDTOS)) {
                rootAppMenu.setHide(false);
                rootAppMenu.setChildren(antdSysMenuDTOS);
            }
            appSortedAntdMenus.add(rootAppMenu);
        }

        // 根据应用的顺序，将应用排序
        appSortedAntdMenus.sort((antdSysMenuDTO, antdSysMenuDTO2) -> {
            int one = appNames.indexOf(antdSysMenuDTO.getTitle());
            int two = appNames.indexOf(antdSysMenuDTO2.getTitle());
            return Integer.compare(one, two);
        });

        return appSortedAntdMenus;
    }

    /**
     * SysMenu转化为需要的IndexMenuInfo
     *
     * @author fengshuonan
     * @date 2022/4/8 16:14
     */
    private static List<IndexMenuInfo> doModelReBuild(List<SysMenu> sysMenuList) {
        if (ObjectUtil.isEmpty(sysMenuList)) {
            return null;
        } else {
            ArrayList<IndexMenuInfo> resultMenus = new ArrayList<>();

            for (SysMenu sysMenu : sysMenuList) {
                IndexMenuInfo antdvMenuItem = new IndexMenuInfo();
                antdvMenuItem.setMenuId(sysMenu.getMenuId());
                antdvMenuItem.setParentId(sysMenu.getMenuParentId());
                antdvMenuItem.setTitle(sysMenu.getMenuName());
                antdvMenuItem.setPath(sysMenu.getAntdvRouter());
                antdvMenuItem.setIcon(sysMenu.getAntdvIcon());
                antdvMenuItem.setComponent(sysMenu.getAntdvComponent());
                antdvMenuItem.setHide(YesOrNotEnum.N.getCode().equals(sysMenu.getAntdvVisible()));
                antdvMenuItem.setActive(sysMenu.getAntdvActiveUrl());
                antdvMenuItem.setSortNumber(sysMenu.getMenuSort());
                if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                    antdvMenuItem.setChildren(doModelReBuild(sysMenu.getChildren()));
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
     * @date 2022/4/8 16:14
     */
    private static IndexMenuInfo createRootAppMenu(String appCode) {

        IndexMenuInfo indexMenuInfo = new IndexMenuInfo();

        // 获取应用的详细信息
        AppServiceApi appServiceApi = SpringUtil.getBean(AppServiceApi.class);
        SysAppResult appInfoByAppCode = appServiceApi.getAppInfoByAppCode(appCode);

        // 菜单名称
        indexMenuInfo.setTitle(appInfoByAppCode.getAppName());

        // 菜单图标
        indexMenuInfo.setIcon(appInfoByAppCode.getAppIcon());

        // 菜单路径
        indexMenuInfo.setPath("/" + appCode);
        indexMenuInfo.setComponent(null);

        // 默认隐藏，如果应用下有children则开启显示，防止应用下没菜单还显示应用
        indexMenuInfo.setHide(true);
        indexMenuInfo.setActive(null);
        indexMenuInfo.setAuthority(null);

        return indexMenuInfo;
    }

}
