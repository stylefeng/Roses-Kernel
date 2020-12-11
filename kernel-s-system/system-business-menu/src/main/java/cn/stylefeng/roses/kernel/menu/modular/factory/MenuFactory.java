package cn.stylefeng.roses.kernel.menu.modular.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.enums.LinkOpenTypeEnum;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.LoginMenuTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.MenuBaseTreeNode;

import java.util.List;

/**
 * 菜单pojo的组装
 *
 * @author fengshuonan
 * @date 2020/11/23 21:58
 */
public class MenuFactory {

    /**
     * menu实体转化为菜单树节点
     *
     * @author fengshuonan
     * @date 2020/11/23 21:54
     */
    public static MenuBaseTreeNode parseMenuBaseTreeNode(SysMenu sysMenu) {
        MenuBaseTreeNode menuTreeNode = new MenuBaseTreeNode();
        menuTreeNode.setId(sysMenu.getId());
        menuTreeNode.setParentId(sysMenu.getPid());
        menuTreeNode.setValue(String.valueOf(sysMenu.getId()));
        menuTreeNode.setTitle(sysMenu.getName());
        menuTreeNode.setWeight(sysMenu.getSort());
        return menuTreeNode;
    }

    /**
     * 将SysMenu格式菜单转换为LoginMenuTreeNode菜单
     *
     * @author fengshuonan
     * @date 2020/4/17 17:53
     */
    public static List<LoginMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList) {
        List<LoginMenuTreeNode> antDesignMenuTreeNodeList = CollectionUtil.newArrayList();
        sysMenuList.forEach(sysMenu -> {
            LoginMenuTreeNode loginMenuTreeNode = new LoginMenuTreeNode();
            loginMenuTreeNode.setComponent(sysMenu.getComponent());
            loginMenuTreeNode.setId(sysMenu.getId());
            loginMenuTreeNode.setName(sysMenu.getCode());
            loginMenuTreeNode.setPath(sysMenu.getRouter());
            loginMenuTreeNode.setPid(sysMenu.getPid());
            LoginMenuTreeNode.Meta mateItem = new LoginMenuTreeNode().new Meta();
            mateItem.setIcon(sysMenu.getIcon());
            mateItem.setTitle(sysMenu.getName());
            mateItem.setLink(sysMenu.getLinkUrl());

            // 是否可见
            mateItem.setShow(!YesOrNotEnum.N.getCode().equals(sysMenu.getVisible()));

            // 是否是外链
            if (LinkOpenTypeEnum.INNER.getCode().equals(sysMenu.getLinkOpenType())) {

                // 打开外链
                mateItem.setTarget("_blank");
                loginMenuTreeNode.setPath(sysMenu.getLinkUrl());
                loginMenuTreeNode.setRedirect(sysMenu.getLinkUrl());

            }
            loginMenuTreeNode.setMeta(mateItem);
            antDesignMenuTreeNodeList.add(loginMenuTreeNode);
        });
        return antDesignMenuTreeNodeList;
    }

}
