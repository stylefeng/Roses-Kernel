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
package cn.stylefeng.roses.kernel.system.modular.role.controller;

import cn.hutool.core.collection.ListUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleResourceService;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleResourceService sysRoleResourceService;

    /**
     * 添加系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:38
     */
    @PostResource(name = "添加角色", path = "/sysRole/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysRoleRequest.add.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.add(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:48
     */
    @PostResource(name = "角色删除", path = "/sysRole/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysRoleRequest.delete.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.del(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:49
     */
    @PostResource(name = "角色编辑", path = "/sysRole/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysRoleRequest.edit.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.edit(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:50
     */
    @GetResource(name = "角色查看", path = "/sysRole/detail")
    public ResponseData<SysRoleDTO> detail(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.detail(sysRoleRequest));
    }

    /**
     * 查询系统角色
     *
     * @author majianguo
     * @date 2020/11/5 上午10:19
     */
    @GetResource(name = "查询角色", path = "/sysRole/page")
    public ResponseData<PageResult<SysRole>> page(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.findPage(sysRoleRequest));
    }

    /**
     * 角色授权资源
     *
     * @author fengshuonan
     * @date 2020/11/22 19:51
     */
    @PostResource(name = "角色授权资源", path = "/sysRole/grantResource")
    public ResponseData<?> grantResource(@RequestBody @Validated(SysRoleRequest.grantResource.class) SysRoleRequest sysRoleParam) {
        sysRoleResourceService.grantResource(sysRoleParam);
        return new SuccessResponseData<>();
    }

    /**
     * 角色绑定接口数据
     *
     * @author fengshuonan
     * @date 2021/8/10 18:23
     */
    @PostResource(name = "角色绑定接口数据V2", path = "/sysRole/grantResourceV2")
    public ResponseData<?> grantResourceV2(@RequestBody @Validated(SysRoleRequest.grantResourceV2.class) SysRoleRequest sysRoleRequest) {
        sysRoleResourceService.grantResourceV2(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 角色授权菜单和按钮
     *
     * @author majianguo
     * @date 2021/1/9 18:04
     */
    @PostResource(name = "角色授权菜单和按钮", path = "/sysRole/grantMenuAndButton")
    public ResponseData<?> grantMenuAndButton(@RequestBody @Validated(SysRoleRequest.grantMenuButton.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.grantMenuAndButton(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 角色授权菜单，新版界面用
     *
     * @author fengshuonan
     * @date 2021/8/11 9:58
     */
    @PostResource(name = "角色授权菜单", path = "/sysRole/grantMenu")
    public ResponseData<?> grantMenu(@RequestBody @Validated(SysRoleRequest.grantMenu.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.grantMenu(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 角色授权按钮，新版界面用
     *
     * @author fengshuonan
     * @date 2021/8/11 9:58
     */
    @PostResource(name = "角色授权按钮", path = "/sysRole/grantButton")
    public ResponseData<?> grantButton(@RequestBody @Validated(SysRoleRequest.grantButton.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.grantButton(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 设置角色绑定的数据范围类型和数据范围
     *
     * @author fengshuonan
     * @date 2020/3/28 16:05
     */
    @PostResource(name = "设置角色绑定的数据范围类型和数据范围", path = "/sysRole/grantDataScope")
    public ResponseData<?> grantData(@RequestBody @Validated(SysRoleRequest.grantDataScope.class) SysRoleRequest sysRoleParam) {
        sysRoleService.grantDataScope(sysRoleParam);
        return new SuccessResponseData<>();
    }

    /**
     * 系统角色下拉（用于用户授权角色时选择）
     *
     * @author majianguo
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "角色下拉", path = "/sysRole/dropDown")
    public ResponseData<List<SimpleDict>> dropDown() {
        return new SuccessResponseData<>(sysRoleService.dropDown());
    }

    /**
     * 拥有菜单
     *
     * @author majianguo
     * @date 2020/11/5 上午10:58
     */
    @GetResource(name = "角色拥有菜单", path = "/sysRole/getRoleMenus")
    public ResponseData<List<Long>> getRoleMenus(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        Long roleId = sysRoleRequest.getRoleId();
        return new SuccessResponseData<>(sysRoleService.getMenuIdsByRoleIds(ListUtil.toList(roleId)));
    }

    /**
     * 拥有数据
     *
     * @author majianguo
     * @date 2020/11/5 上午10:59
     */
    @GetResource(name = "角色拥有数据", path = "/sysRole/getRoleDataScope")
    public ResponseData<List<Long>> getRoleDataScope(@Validated(SysRoleRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.getRoleDataScope(sysRoleRequest));
    }

}
