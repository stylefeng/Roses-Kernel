package cn.stylefeng.roses.kernel.system.modular.menu.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdvMenuItem;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiMenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.AntdMenusFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统菜单控制器
 *
 * @author fengshuonan
 * @date 2020/3/20 18:54
 */
@RestController
@ApiResource(name = "菜单管理")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 添加系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:57
     */
    @PostResource(name = "添加系统菜单", path = "/sysMenu/add")
    public ResponseData add(@RequestBody @Validated(SysMenuRequest.add.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.add(sysMenuRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:58
     */
    @PostResource(name = "删除系统菜单", path = "/sysMenu/delete")
    public ResponseData delete(@RequestBody @Validated(SysMenuRequest.delete.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.del(sysMenuRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:59
     */
    @PostResource(name = "编辑系统菜单", path = "/sysMenu/edit")
    public ResponseData edit(@RequestBody @Validated(SysMenuRequest.edit.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.edit(sysMenuRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 9:01
     */
    @GetResource(name = "查看系统菜单", path = "/sysMenu/detail")
    public ResponseData detail(@Validated(SysMenuRequest.detail.class) SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.detail(sysMenuRequest));
    }

    /**
     * Layui版本--菜单列表，不带树形结构（菜单管理界面的列表用）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:09
     */
    @GetResource(name = "获取菜单列表（layui版本）", path = "/sysMenu/layuiList")
    public ResponseData layuiList(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.findList(sysMenuRequest));
    }

    /**
     * Layui版本--获取系统菜单和按钮的树，用于角色分配菜单按钮
     *
     * @author majianguo
     * @date 2021/1/9 17:10
     */
    @GetResource(name = "获取系统菜单和按钮的树，用于角色分配菜单按钮（layui版本使用）", path = "/sysMenu/menuAndButtonTree")
    public List<LayuiMenuAndButtonTreeResponse> menuAndButtonTree(SysRoleRequest sysRoleRequest) {
        return sysMenuService.getMenuAndButtonTree(sysRoleRequest, true);
    }

    /**
     * Layui版本--获取系统菜单树，用于新增，编辑时选择上级节点（用在新增和编辑菜单选择上级菜单）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:09
     */
    @GetResource(name = "获取菜单的树形列表（用于选择上级菜单）（layui版本）", path = "/sysMenu/layuiSelectParentMenuTreeList")
    public List<ZTreeNode> layuiSelectParentMenuTreeList() {
        return sysMenuService.layuiSelectParentMenuTreeList();
    }

    /**
     * AntdVue版本--获取系统左侧菜单（适用于登录后获取左侧菜单）
     *
     * @author majianguo
     * @date 2021/1/7 15:17
     */
    @GetResource(name = "获取系统所有菜单（适用于登录后获取左侧菜单）（适配antd vue版本）", path = "/sysMenu/getLeftMenusAntdv", requiredPermission = false)
    public ResponseData getLeftMenusAntdv() {
        List<AntdSysMenuDTO> sysMenuResponses = sysMenuService.getLeftMenusAntdv();
        List<AntdvMenuItem> totalMenus = AntdMenusFactory.createTotalMenus(sysMenuResponses);
        return new SuccessResponseData(totalMenus);
    }

    /**
     * AntdVue版本--菜单列表，带树形结构（菜单管理界面的列表用）
     *
     * @author fengshuonan
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "系统菜单列表（树）", path = "/sysMenu/list")
    public ResponseData list(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.findListWithTreeStructure(sysMenuRequest));
    }

    /**
     * AntdVue版本--获取系统菜单树，用于新增，编辑时选择上级节点（用在新增和编辑菜单选择上级菜单）
     *
     * @author fengshuonan
     * @date 2020/3/27 15:55
     */
    @GetResource(name = "获取系统菜单树，用于新增，编辑时选择上级节点", path = "/sysMenu/tree")
    public ResponseData tree(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.tree(sysMenuRequest));
    }

    /**
     * AntdVue版本--获取系统菜单和按钮的树，用于角色分配菜单按钮
     *
     * @author majianguo
     * @date 2021/1/9 17:10
     */
    @GetResource(name = "获取系统菜单和按钮的树，用于角色分配菜单按钮（antd vue版本使用）", path = "/sysMenu/menuAndButtonTreeChildren")
    public ResponseData menuAndButtonTreeChildren(SysRoleRequest sysRoleRequest) {
        List<LayuiMenuAndButtonTreeResponse> treeResponseList = sysMenuService.getMenuAndButtonTree(sysRoleRequest, false);
        return new SuccessResponseData(treeResponseList);
    }

}
