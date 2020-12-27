package cn.stylefeng.roses.kernel.menu.modular.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.enums.LinkOpenTypeEnum;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.AntdIndexMenuTreeNode;
import cn.stylefeng.roses.kernel.system.pojo.menu.tree.MenuSelectTreeNode;

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
    public static MenuSelectTreeNode parseMenuBaseTreeNode(SysMenu sysMenu) {
        MenuSelectTreeNode menuTreeNode = new MenuSelectTreeNode();
        menuTreeNode.setId(sysMenu.getMenuId());
        menuTreeNode.setParentId(sysMenu.getMenuParentId());
        menuTreeNode.setValue(String.valueOf(sysMenu.getMenuId()));
        menuTreeNode.setTitle(sysMenu.getMenuName());
        menuTreeNode.setWeight(sysMenu.getMenuSort());
        return menuTreeNode;
    }

    /**
     * 将SysMenu格式菜单转换为LoginMenuTreeNode菜单
     *
     * @author fengshuonan
     * @date 2020/4/17 17:53
     */
    public static List<AntdIndexMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList) {
        List<AntdIndexMenuTreeNode> antDesignMenuTreeNodeList = CollectionUtil.newArrayList();
        sysMenuList.forEach(sysMenu -> {
            AntdIndexMenuTreeNode antdIndexMenuTreeNode = new AntdIndexMenuTreeNode();
            antdIndexMenuTreeNode.setComponent(sysMenu.getComponent());
            antdIndexMenuTreeNode.setId(sysMenu.getMenuId());
            antdIndexMenuTreeNode.setName(sysMenu.getMenuCode());
            antdIndexMenuTreeNode.setPath(sysMenu.getRouter());
            antdIndexMenuTreeNode.setPid(sysMenu.getMenuParentId());
            AntdIndexMenuTreeNode.Meta mateItem = new AntdIndexMenuTreeNode().new Meta();
            mateItem.setIcon(sysMenu.getIcon());
            mateItem.setTitle(sysMenu.getMenuName());
            mateItem.setLink(sysMenu.getLinkUrl());

            // 是否可见
            mateItem.setShow(YesOrNotEnum.Y.getCode().equals(sysMenu.getVisible()));

            // 是否是外链
            if (LinkOpenTypeEnum.INNER.getCode().equals(sysMenu.getLinkOpenType())) {

                // 打开外链
                mateItem.setTarget("_blank");
                antdIndexMenuTreeNode.setPath(sysMenu.getLinkUrl());
                antdIndexMenuTreeNode.setRedirect(sysMenu.getLinkUrl());

            }
            antdIndexMenuTreeNode.setMeta(mateItem);
            antDesignMenuTreeNodeList.add(antdIndexMenuTreeNode);
        });
        return antDesignMenuTreeNodeList;
    }

}
