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

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuButtonService;
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
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysMenuButtonRequest.add.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.add(sysMenuButtonRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 添加系统菜单按钮-默认按钮
     *
     * @author chenjinlong
     * @date 2021/1/9 11:28
     */
    @PostResource(name = "添加系统默认菜单按钮", path = "/sysMenuButton/addSystemDefaultButton")
    @BusinessLog
    public ResponseData<?> addSystemDefaultButton(@RequestBody @Validated(SysMenuButtonRequest.def.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.addDefaultButtons(sysMenuButtonRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除单个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @author luojie
     * @date 2021/1/9 12:14
     */
    @PostResource(name = "删除单个系统菜单按钮", path = "/sysMenuButton/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysMenuButtonRequest.delete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.del(sysMenuButtonRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除多个系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮id集合
     * @author luojie
     * @date 2021/1/9 12:27
     */
    @PostResource(name = "批量删除多个系统菜单按钮", path = "/sysMenuButton/batchDelete")
    @BusinessLog
    public ResponseData<?> batchDelete(@RequestBody @Validated(SysMenuButtonRequest.batchDelete.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.delBatch(sysMenuButtonRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统菜单按钮
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author luojie
     * @date 2021/1/9 12:00
     */
    @PostResource(name = "编辑系统菜单按钮", path = "/sysMenuButton/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysMenuButtonRequest.edit.class) SysMenuButtonRequest sysMenuButtonRequest) {
        sysMenuButtonService.edit(sysMenuButtonRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取菜单按钮详情
     *
     * @param sysMenuButtonRequest 菜单按钮id
     * @author luojie
     * @date 2021/1/9 11:53
     */
    @GetResource(name = "获取菜单按钮详情", path = "/sysMenuButton/detail")
    public ResponseData<SysMenuButton> detail(@Validated(SysMenuButtonRequest.detail.class) SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton detail = sysMenuButtonService.detail(sysMenuButtonRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取菜单按钮列表
     *
     * @param sysMenuButtonRequest 菜单id
     * @author luojie
     * @date 2021/1/9 12:33
     */
    @GetResource(name = "获取菜单按钮列表", path = "/sysMenuButton/pageList")
    public ResponseData<PageResult<SysMenuButton>> pageList(@Validated(SysMenuButtonRequest.list.class) SysMenuButtonRequest sysMenuButtonRequest) {
        PageResult<SysMenuButton> pageResult = sysMenuButtonService.findPage(sysMenuButtonRequest);
        return new SuccessResponseData<>(pageResult);
    }

}
