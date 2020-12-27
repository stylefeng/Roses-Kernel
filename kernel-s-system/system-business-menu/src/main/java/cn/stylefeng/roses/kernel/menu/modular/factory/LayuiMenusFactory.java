package cn.stylefeng.roses.kernel.menu.modular.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.system.pojo.menu.layui.LayuiAppIndexMenus;
import cn.stylefeng.roses.kernel.system.pojo.menu.layui.LayuiIndexMenuTreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对于layui前端的菜单的组装
 *
 * @author fengshuonan
 * @date 2020/12/27 18:53
 */
public class LayuiMenusFactory {

    /**
     * 创建layui前端首页需要的菜单列表
     *
     * @author fengshuonan
     * @date 2020/12/27 19:07
     */
    public static List<LayuiAppIndexMenus> createLayuiAppIndexMenus(List<SysMenu> sysMenuList) {

        ArrayList<LayuiAppIndexMenus> resultList = new ArrayList<>();

        // 找出用户有多少个应用的菜单
        Set<String> appCodes = new HashSet<>();
        for (SysMenu currentUserMenu : sysMenuList) {
            String appCode = currentUserMenu.getAppCode();
            appCodes.add(appCode);
        }

        // 找出每个应用下的所有菜单
        for (String appCode : appCodes) {


            // 找出这个应用下的菜单
            List<SysMenu> appMenus = sysMenuList.stream()
                    .filter(i -> i.getAppCode().equals(appCode))
                    .collect(Collectors.toList());

            // 菜单实体 转化为 layui节点
            ArrayList<LayuiIndexMenuTreeNode> layuiIndexMenuTreeNodes = new ArrayList<>();
            for (SysMenu appMenu : appMenus) {
                LayuiIndexMenuTreeNode layuiIndexMenuTreeNode = new LayuiIndexMenuTreeNode();
                BeanUtil.copyProperties(appMenu, layuiIndexMenuTreeNode);
                layuiIndexMenuTreeNodes.add(layuiIndexMenuTreeNode);
            }

            // 将这些菜单组合成树
            List<LayuiIndexMenuTreeNode> layuiIndexMenuTreeNodeList = new DefaultTreeBuildFactory<LayuiIndexMenuTreeNode>().doTreeBuild(layuiIndexMenuTreeNodes);

            // 将appCode和对应的树包装为实体
            LayuiAppIndexMenus layuiAppIndexMenus = new LayuiAppIndexMenus();
            layuiAppIndexMenus.setAppCode(appCode);
            layuiAppIndexMenus.setLayuiIndexMenuTreeNodes(layuiIndexMenuTreeNodeList);
            resultList.add(layuiAppIndexMenus);
        }

        return resultList;
    }

}
