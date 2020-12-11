package cn.stylefeng.roses.kernel.menu.modular.controller;

import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
     * 获取某个应用的菜单，用于系统顶部切换菜单
     *
     * @author fengshuonan
     * @date 2020/4/19 15:50
     */
    @GetResource(name = "获取某个应用的菜单", path = "/sysMenu/getAppMenus")
    public ResponseData getAppMenus(@RequestBody @Validated(SysMenuRequest.getAppMenus.class) SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData(sysMenuService.getAppMenusAntDesign(sysMenuRequest.getAppCode()));
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

}
