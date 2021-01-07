package cn.stylefeng.roses.kernel.menu.modular.controller;

import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.response.SysMenuResponse;
import cn.stylefeng.roses.kernel.system.pojo.ztree.ZTreeNode;
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
     * 删除系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:58
     */
    @PostResource(name = "删除系统菜单", path = "/sysMenu/delete")
    public ResponseData delete(@RequestBody @Validated(SysMenuRequest.delete.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.delete(sysMenuRequest);
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
     * 获取菜单列表（layui版本）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:09
     */
    @GetResource(name = "获取菜单列表（layui版本）", path = "/sysMenu/layuiList")
    public ResponseData layuiList(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.layuiList(sysMenuRequest));
    }

    /**
     * 获取菜单的树形列表（用于选择上级菜单）（layui版本）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:09
     */
    @GetResource(name = "获取菜单的树形列表（用于选择上级菜单）（layui版本）", path = "/sysMenu/layuiSelectParentMenuTreeList")
    public List<ZTreeNode> layuiSelectParentMenuTreeList() {
        return sysMenuService.layuiSelectParentMenuTreeList();
    }

    /**
     * 系统菜单列表，树形结构，用于菜单管理界面的列表展示
     *
     * @author fengshuonan
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "系统菜单列表（树）", path = "/sysMenu/list")
    public ResponseData list(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.list(sysMenuRequest));
    }

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @author fengshuonan
     * @date 2020/3/27 15:55
     */
    @GetResource(name = "获取系统菜单树，用于新增，编辑时选择上级节点", path = "/sysMenu/tree")
    public ResponseData tree(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.tree(sysMenuRequest));
    }

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @author fengshuonan
     * @date 2020/4/5 15:00
     */
    @GetResource(name = "获取系统菜单树，用于给角色授权时选择", path = "/sysMenu/treeForGrant")
    public ResponseData treeForGrant(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.treeForGrant(sysMenuRequest));
    }

    /**
     * 获取主页左侧菜单列表（适配Antd Vue的版本）
     *
     * @author fengshuonan
     * @date 2020/4/19 15:50
     */
    @GetResource(name = "获取主页左侧菜单列表（Antd Vue）", path = "/sysMenu/getIndexMenuAntdVue", requiredPermission = false)
    public ResponseData getAppMenus(@Validated(SysMenuRequest.getAppMenusAntdVue.class) SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.getAntDVueIndexMenus(sysMenuRequest.getAppCode()));
    }

    /**
     * 获取系统所有菜单
     *
     * @author majianguo
     * @date 2021/1/7 15:17
     */
    @GetResource(name = "获取菜单列表(包含菜单可以被那些角色访问)", path = "/sysMenu/getSystemAllMenus", requiredPermission = false)
    public ResponseData getSystemAllMenus() {
        List<SysMenuResponse> sysMenuResponses = sysMenuService.getSystemAllMenus();
        return new SuccessResponseData(sysMenuResponses);
    }

}
