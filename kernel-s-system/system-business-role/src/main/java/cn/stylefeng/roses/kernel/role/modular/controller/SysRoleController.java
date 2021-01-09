package cn.stylefeng.roses.kernel.role.modular.controller;

import cn.hutool.core.collection.ListUtil;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.role.modular.service.SysRoleResourceService;
import cn.stylefeng.roses.kernel.role.modular.service.SysRoleService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 *
 * @author majianguo
 * @date 2020/11/5 上午10:19
 */
@RestController
@ApiResource(name = "系统角色管理")
public class SysRoleController {

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    /**
     * 添加系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:38
     */
    @PostResource(name = "添加系统", path = "/sysRole/add")
    public ResponseData add(@RequestBody @Validated(SysRoleRequest.add.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.add(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:49
     */
    @PostResource(name = "角色编辑", path = "/sysRole/edit")
    public ResponseData edit(@RequestBody @Validated(SysRoleRequest.edit.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.edit(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:48
     */
    @PostResource(name = "角色删除", path = "/sysRole/delete")
    public ResponseData delete(@RequestBody @Validated(SysRoleRequest.delete.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.delete(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权角色对应的资源
     *
     * @author fengshuonan
     * @date 2020/11/22 19:51
     */
    @PostResource(name = "授权资源", path = "/sysRole/grantResource")
    public ResponseData grantResource(@RequestBody @Validated(SysRoleRequest.grantResource.class) SysRoleRequest sysRoleParam) {
        sysRoleResourceService.grantResource(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 授权角色授权菜单和按钮
     *
     * @author majianguo
     * @date 2021/1/9 18:04
     */
    @PostResource(name = "授权资源", path = "/sysRole/grantMenuAndButton")
    public ResponseData grantMenuAndButton(@RequestBody @Validated(SysRoleRequest.grantMenuButton.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.grantMenuAndButton(sysRoleRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author fengshuonan
     * @date 2020/3/28 16:05
     */
    @PostResource(name = "授权数据", path = "/sysRole/grantData")
    public ResponseData grantData(@RequestBody @Validated(SysRoleRequest.grantData.class) SysRoleRequest sysRoleParam) {
        sysRoleService.grantData(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:50
     */
    @GetResource(name = "角色查看", path = "/sysRole/detail")
    public ResponseData detail(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.detail(sysRoleRequest));
    }

    /**
     * 查询系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:19
     */
    @GetResource(name = "查询角色", path = "/sysRole/page")
    public ResponseData page(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.page(sysRoleRequest));
    }

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @author majianguo
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "角色下拉", path = "/sysRole/dropDown")
    public ResponseData dropDown() {
        return new SuccessResponseData(sysRoleService.dropDown());
    }

    /**
     * 拥有菜单
     *
     * @author majianguo
     * @date 2020/11/5 上午10:58
     */
    @GetResource(name = "角色拥有菜单", path = "/sysRole/getRoleMenus")
    public ResponseData getRoleMenus(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        Long roleId = sysRoleRequest.getRoleId();
        return new SuccessResponseData(roleServiceApi.getMenuIdsByRoleIds(ListUtil.toList(roleId)));
    }

    /**
     * 拥有数据
     *
     * @author majianguo
     * @date 2020/11/5 上午10:59
     */
    @GetResource(name = "角色拥有数据", path = "/sysRole/getRoleDataScope")
    public ResponseData getRoleDataScope(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData(sysRoleService.getRoleDataScope(sysRoleRequest));
    }

}
