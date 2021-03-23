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
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

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
     * @author fengshuonan
     * @date 2021/1/7 18:17
     */
    public static List<AntdSysMenuDTO> createTotalMenus(List<SysMenu> sysMenuList) {

        // 构造菜单树
        List<SysMenu> treeStructMenu = new DefaultTreeBuildFactory<SysMenu>(TreeConstants.DEFAULT_PARENT_ID.toString()).doTreeBuild(sysMenuList);

        // 模型转化
        return doModelTransfer(treeStructMenu);
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
                antdvMenuItem.setHide(YesOrNotEnum.N.getCode().equals(sysMenu.getVisible()));
                antdvMenuItem.setUid(null);
                if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                    antdvMenuItem.setChildren(doModelTransfer(sysMenu.getChildren()));
                }
                resultMenus.add(antdvMenuItem);
            }

            return resultMenus;
        }
    }

}
