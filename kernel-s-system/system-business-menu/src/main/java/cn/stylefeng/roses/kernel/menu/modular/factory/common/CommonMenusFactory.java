package cn.stylefeng.roses.kernel.menu.modular.factory.common;

import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.pojo.menu.other.MenuSelectTreeNode;

/**
 * 针对于antd前端的菜单的组装
 *
 * @author fengshuonan
 * @date 2020/11/23 21:58
 */
public class CommonMenusFactory {

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

}
