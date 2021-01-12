package cn.stylefeng.roses.kernel.menu.modular.factory;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.menu.modular.pojo.AntdvMenuItem;
import cn.stylefeng.roses.kernel.menu.modular.pojo.AuthorityItem;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdIndexMenuTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdSysMenuResponse;

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
    public static List<AntdvMenuItem> createTotalMenus(List<AntdSysMenuResponse> antdSysMenuResponses) {

        ArrayList<AntdvMenuItem> antdvMenuItems = new ArrayList<>(antdSysMenuResponses.size());

        // 实体转化
        for (AntdSysMenuResponse antdSysMenuResponse : antdSysMenuResponses) {
            AntdvMenuItem antdvMenuItem = new AntdvMenuItem();

            // 填充id pid name用于构建树
            antdvMenuItem.setMenuId(antdSysMenuResponse.getMenuId());
            antdvMenuItem.setMenuParentId(antdSysMenuResponse.getMenuParentId());
            antdvMenuItem.setName(antdSysMenuResponse.getMenuName());

            // 填充路由等信息
            antdvMenuItem.setRouter(antdSysMenuResponse.getAntdvRouter());
            antdvMenuItem.setIcon(antdSysMenuResponse.getAntdvIcon());

            // 填充哪个角色绑定了这个菜单
            List<SimpleRoleInfo> roles = antdSysMenuResponse.getRoles();
            AuthorityItem authorityItem = new AuthorityItem();
            if (roles != null && roles.size() > 0) {
                ArrayList<String> auths = new ArrayList<>();
                for (SimpleRoleInfo role : roles) {
                    auths.add(role.getRoleCode());
                }
                authorityItem.setPermission(auths);
                authorityItem.setRole(auths);
                antdvMenuItem.setAuthority(authorityItem);
            }

            antdvMenuItems.add(antdvMenuItem);
        }

        // 加入根节点
        antdvMenuItems.add(createAntdVMenuRoot());

        // 构造菜单树
        return new DefaultTreeBuildFactory<AntdvMenuItem>(SystemConstants.VIRTUAL_ROOT_PARENT_ID.toString()).doTreeBuild(antdvMenuItems);
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
        antdvMenuItem.setMenuId(SystemConstants.DEFAULT_PARENT_ID);
        antdvMenuItem.setMenuParentId(SystemConstants.VIRTUAL_ROOT_PARENT_ID);

        antdvMenuItem.setAuthority(null);

        return antdvMenuItem;
    }


    /**
     * 将数据库表的菜单，转化为vue antd admin识别的菜单路由格式
     *
     * @author fengshuonan
     * @date 2020/12/30 20:28
     */
    public static List<AntdIndexMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList) {
        List<AntdIndexMenuTreeNode> antDesignMenuTreeNodeList = new ArrayList<>(sysMenuList.size());

        // 添加根节点
        antDesignMenuTreeNodeList.add(createRootMenuNode());

        for (SysMenu sysMenu : sysMenuList) {
            AntdIndexMenuTreeNode antdIndexMenuTreeNode = new AntdIndexMenuTreeNode();

            // 设置菜单id
            antdIndexMenuTreeNode.setId(sysMenu.getMenuId());

            // 设置父级id
            antdIndexMenuTreeNode.setPid(sysMenu.getMenuParentId());

            // 菜单名称
            antdIndexMenuTreeNode.setName(sysMenu.getMenuName());

            // 菜单路由地址
            antdIndexMenuTreeNode.setPath(sysMenu.getAntdvRouter());
            antdIndexMenuTreeNode.setRouter(sysMenu.getAntdvRouter());

            AntdIndexMenuTreeNode.Meta mateItem = new AntdIndexMenuTreeNode.Meta();

            // 菜单图标
            mateItem.setIcon(sysMenu.getAntdvIcon());

            // 设置是否隐藏，true就是隐藏
            mateItem.setInvisible(YesOrNotEnum.N.getCode().equals(sysMenu.getVisible()));
            antdIndexMenuTreeNode.setMeta(mateItem);

            antDesignMenuTreeNodeList.add(antdIndexMenuTreeNode);
        }

        return antDesignMenuTreeNodeList;
    }

    /**
     * 创建虚拟的根节点信息
     *
     * @author fengshuonan
     * @date 2020/12/30 20:38
     */
    private static AntdIndexMenuTreeNode createRootMenuNode() {
        AntdIndexMenuTreeNode antdIndexMenuTreeNode = new AntdIndexMenuTreeNode();
        antdIndexMenuTreeNode.setId(SystemConstants.DEFAULT_PARENT_ID);
        antdIndexMenuTreeNode.setPid(SystemConstants.VIRTUAL_ROOT_PARENT_ID);
        antdIndexMenuTreeNode.setName("根虚拟节点");
        antdIndexMenuTreeNode.setPath("/");
        return antdIndexMenuTreeNode;
    }

}
