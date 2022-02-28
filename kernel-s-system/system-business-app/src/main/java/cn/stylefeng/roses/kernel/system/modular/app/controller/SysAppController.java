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
package cn.stylefeng.roses.kernel.system.modular.app.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.app.SysAppRequest;
import cn.stylefeng.roses.kernel.system.modular.app.entity.SysApp;
import cn.stylefeng.roses.kernel.system.modular.app.service.SysAppService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统应用控制器
 *
 * @author fengshuonan
 * @date 2020/3/20 21:25
 */
@RestController
@ApiResource(name = "系统应用")
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 添加系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:44
     */
    @PostResource(name = "添加系统应用", path = "/sysApp/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysAppRequest.add.class) SysAppRequest sysAppParam) {
        sysAppService.add(sysAppParam);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "删除系统应用", path = "/sysApp/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysAppRequest.delete.class) SysAppRequest sysAppParam) {
        sysAppService.del(sysAppParam);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "编辑系统应用", path = "/sysApp/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysAppRequest.edit.class) SysAppRequest sysAppParam) {
        sysAppService.edit(sysAppParam);
        return new SuccessResponseData<>();
    }

    /**
     * 修改应用状态
     *
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "修改应用状态", path = "/sysApp/updateStatus")
    @BusinessLog
    public ResponseData<?> updateStatus(@RequestBody @Validated(SysAppRequest.updateStatus.class) SysAppRequest sysAppParam) {
        sysAppService.editStatus(sysAppParam);
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统应用
     *
     * @author fengshuonan
     * @date 2020/3/26 9:49
     */
    @GetResource(name = "查看系统应用", path = "/sysApp/detail")
    public ResponseData<SysApp> detail(@Validated(SysAppRequest.detail.class) SysAppRequest sysAppParam) {
        return new SuccessResponseData<>(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @author fengshuonan
     * @date 2020/4/19 14:55
     */
    @GetResource(name = "系统应用列表", path = "/sysApp/list")
    public ResponseData<List<SysApp>> list(SysAppRequest sysAppParam) {
        return new SuccessResponseData<>(sysAppService.findList(sysAppParam));
    }

    /**
     * 查询系统应用
     *
     * @author fengshuonan
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "查询系统应用", path = "/sysApp/page")
    public ResponseData<PageResult<SysApp>> page(SysAppRequest sysAppParam) {
        return new SuccessResponseData<>(sysAppService.findPage(sysAppParam));
    }

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "设为默认应用", path = "/sysApp/updateActiveFlag")
    @BusinessLog
    public ResponseData<?> setAsDefault(@RequestBody @Validated(SysAppRequest.updateActiveFlag.class) SysAppRequest sysAppParam) {
        sysAppService.updateActiveFlag(sysAppParam);
        return new SuccessResponseData<>();
    }

    /**
     * 获取应用列表，用于顶部应用列表
     *
     * @author fengshuonan
     * @date 2021/4/21 15:31
     */
    @GetResource(name = "获取应用列表，用于顶部应用列表", path = "/sysMenu/getTopAppList", requiredPermission = false)
    public ResponseData<List<SysApp>> getTopAppList() {
        List<SysApp> userTopAppList = sysAppService.getUserTopAppList();
        return new SuccessResponseData<>(userTopAppList);
    }

}
