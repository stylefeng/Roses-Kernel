package cn.stylefeng.roses.kernel.menu.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuButtonService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuButtonResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统菜单按钮控制器
 *
 * @author luojie
 * @date 2021/1/9 16:08
 */
@RestController
@ApiResource(name = "菜单按钮管理")
public class SysMenuButtonController {

    @Resource
    private SysMenuButtonService sysMenuButtonService;

    /**
     * 添加系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author luojie
     * @date 2021/1/9 11:28
     */
    @PostResource(name = "添加系统菜单按钮", path = "/sysMenuButton/add")
    public ResponseData add(@RequestBody @Validated(SysMenuButtonRequest.add.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.add(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取菜单按钮详情
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @author luojie
     * @date 2021/1/9 11:53
     */
    @GetResource(name = "获取菜单按钮详情", path = "/sysMenuButton/detail")
    public ResponseData detail(@Validated(SysMenuButtonRequest.detail.class) SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButtonResponse detail = sysMenuButtonService.detail(sysMenuButtonRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 编辑系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author luojie
     * @date 2021/1/9 12:00
     */
    @PostResource(name = "编辑系统菜单按钮", path = "/sysMenuButton/edit")
    public ResponseData edit(@RequestBody @Validated(SysMenuButtonRequest.edit.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.edit(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除单个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @author luojie
     * @date 2021/1/9 12:14
     */
    @PostResource(name = "删除单个系统菜单按钮", path = "/sysMenuButton/delete")
    public ResponseData delete(@RequestBody @Validated(SysMenuButtonRequest.delete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.delete(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 批量删除多个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id集合
     * @author luojie
     * @date 2021/1/9 12:27
     */
    @PostResource(name = "批量删除多个系统菜单按钮", path = "/sysMenuButton/batchDelete")
    public ResponseData batchDelete(@RequestBody @Validated(SysMenuButtonRequest.batchDelete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.batchDelete(sysMenuButtonRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取菜单按钮列表
     *
     * @param sysMenuButtonRequest 菜单id
     * @author luojie
     * @date 2021/1/9 12:33
     */
    @GetResource(name = "获取菜单按钮列表", path = "/sysMenuButton/pageList")
    public ResponseData pageList(@Validated(SysMenuButtonRequest.list.class) SysMenuButtonRequest sysMenuButtonRequest) {
        PageResult<SysMenuButtonResponse> pageResult = sysMenuButtonService.pageList(sysMenuButtonRequest);
        return new SuccessResponseData(pageResult);
    }

}
