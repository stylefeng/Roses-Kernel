package cn.stylefeng.roses.kernel.menu.modular.factory;

import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.pojo.menu.antd.AntdIndexMenuTreeNode;

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
            antdIndexMenuTreeNode.setPath(sysMenu.getRouter());
            antdIndexMenuTreeNode.setRouter(sysMenu.getRouter());

            AntdIndexMenuTreeNode.Meta mateItem = new AntdIndexMenuTreeNode.Meta();

            // 菜单图标
            mateItem.setIcon(sysMenu.getIcon());

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
