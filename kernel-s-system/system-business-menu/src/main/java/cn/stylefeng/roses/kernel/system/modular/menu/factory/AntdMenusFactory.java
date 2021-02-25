package cn.stylefeng.roses.kernel.system.modular.menu.factory;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdvMenuAuthorityItem;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdvMenuItem;

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
    public static List<AntdvMenuItem> createTotalMenus(List<AntdSysMenuDTO> sysMenuRespons) {

        ArrayList<AntdvMenuItem> antdvMenuItems = new ArrayList<>(sysMenuRespons.size());

        // 实体转化
        for (AntdSysMenuDTO antdSysMenuDTO : sysMenuRespons) {
            AntdvMenuItem antdvMenuItem = new AntdvMenuItem();

            // 填充id pid name用于构建树
            antdvMenuItem.setMenuId(antdSysMenuDTO.getMenuId());
            antdvMenuItem.setMenuParentId(antdSysMenuDTO.getMenuParentId());
            antdvMenuItem.setName(antdSysMenuDTO.getMenuName());

            // 填充路由等信息
            antdvMenuItem.setRouter(antdSysMenuDTO.getAntdvRouter());
            antdvMenuItem.setIcon(antdSysMenuDTO.getAntdvIcon());

            // 填充是否隐藏
            antdvMenuItem.setInvisible(YesOrNotEnum.N.getCode().equals(antdSysMenuDTO.getVisible()));

            // 填充哪个角色绑定了这个菜单
            List<SimpleRoleInfo> roles = antdSysMenuDTO.getRoles();
            AntdvMenuAuthorityItem antdvAuthorityItem = new AntdvMenuAuthorityItem();
            if (roles != null && roles.size() > 0) {
                ArrayList<String> auths = new ArrayList<>();
                for (SimpleRoleInfo role : roles) {
                    auths.add(role.getRoleCode());
                }
                antdvAuthorityItem.setPermission(auths);
                antdvAuthorityItem.setRole(auths);
                antdvMenuItem.setAuthority(antdvAuthorityItem);
            }

            antdvMenuItems.add(antdvMenuItem);
        }

        // 加入根节点
        antdvMenuItems.add(createAntdVMenuRoot());

        // 构造菜单树
        return new DefaultTreeBuildFactory<AntdvMenuItem>(TreeConstants.VIRTUAL_ROOT_PARENT_ID.toString()).doTreeBuild(antdvMenuItems);
    }

    /**
     * 创建虚拟根节点
     *
     * @author fengshuonan
     * @date 2020/12/30 20:38
     */
    private static AntdvMenuItem createAntdVMenuRoot() {
        AntdvMenuItem antdvMenuItem = new AntdvMenuItem();
        antdvMenuItem.setRouter("root");
        antdvMenuItem.setName("根节点");
        antdvMenuItem.setMenuId(TreeConstants.DEFAULT_PARENT_ID);
        antdvMenuItem.setMenuParentId(TreeConstants.VIRTUAL_ROOT_PARENT_ID);
        antdvMenuItem.setInvisible(false);

        antdvMenuItem.setAuthority(null);

        return antdvMenuItem;
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

}
