/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.system.modular.menu.controller;

import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.MenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdMenuSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.antd.AntdSysMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.layui.LayuiMenuAndButtonTreeResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
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
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysMenuRequest.add.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.add(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:58
     */
    @PostResource(name = "删除系统菜单", path = "/sysMenu/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysMenuRequest.delete.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.del(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 8:59
     */
    @PostResource(name = "编辑系统菜单", path = "/sysMenu/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysMenuRequest.edit.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.edit(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统菜单
     *
     * @author fengshuonan
     * @date 2020/3/27 9:01
     */
    @GetResource(name = "查看系统菜单", path = "/sysMenu/detail")
    public ResponseData<SysMenu> detail(@Validated(SysMenuRequest.detail.class) SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData<>(sysMenuService.detail(sysMenuRequest));
    }

    /**
     * Layui版本--菜单列表，不带树形结构（菜单管理界面的列表用）
     *
     * @author fengshuonan
     * @date 2021/1/6 17:09
     */
    @GetResource(name = "获取菜单列表（layui版本）", path = "/sysMenu/layuiList")
    public ResponseData<List<SysMenu>> layuiList(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData<>(sysMenuService.findList(sysMenuRequest));
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
    public ResponseData<List<AntdSysMenuDTO>> getLeftMenusAntdv(SysMenuRequest sysMenuRequest) {
        List<AntdSysMenuDTO> sysMenuResponses = sysMenuService.getLeftMenusAntdv(sysMenuRequest);
        return new SuccessResponseData<>(sysMenuResponses);
    }

    /**
     * AntdVue版本--菜单列表，带树形结构（菜单管理界面的列表用）
     *
     * @author fengshuonan
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "系统菜单列表（树）", path = "/sysMenu/list")
    public ResponseData<List<SysMenu>> list(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData<>(sysMenuService.findListWithTreeStructure(sysMenuRequest));
    }

    /**
     * AntdVue版本--获取系统菜单树，用于新增，编辑时选择上级节点（用在新增和编辑菜单选择上级菜单）
     *
     * @author fengshuonan
     * @date 2020/3/27 15:55
     */
    @GetResource(name = "获取系统菜单树，用于新增，编辑时选择上级节点", path = "/sysMenu/tree")
    public ResponseData<List<AntdMenuSelectTreeNode>> tree(SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData<>(sysMenuService.tree(sysMenuRequest));
    }

    /**
     * AntdVue版本--获取系统菜单和按钮的树，用于角色分配菜单按钮
     *
     * @author majianguo
     * @date 2021/1/9 17:10
     */
    @GetResource(name = "获取系统菜单和按钮的树，用于角色分配菜单按钮（antd vue版本使用）", path = "/sysMenu/menuAndButtonTreeChildren")
    public ResponseData<List<LayuiMenuAndButtonTreeResponse>> menuAndButtonTreeChildren(SysRoleRequest sysRoleRequest) {
        List<LayuiMenuAndButtonTreeResponse> treeResponseList = sysMenuService.getMenuAndButtonTree(sysRoleRequest, false);
        return new SuccessResponseData<>(treeResponseList);
    }

    /**
     * 新版角色分配菜单和按钮界面使用的接口
     *
     * @author fengshuonan
     * @date 2021/8/10 22:21
     */
    @GetResource(name = "新版角色分配菜单和按钮界面使用的接口（v2）", path = "/sysMenu/menuAndButtonTreeChildrenV2")
    public ResponseData<List<MenuAndButtonTreeResponse>> menuAndButtonTreeChildrenV2(SysRoleRequest sysRoleRequest) {
        List<MenuAndButtonTreeResponse> treeResponseList = sysMenuService.getRoleMenuAndButtons(sysRoleRequest);
        return new SuccessResponseData<>(treeResponseList);
    }

}
