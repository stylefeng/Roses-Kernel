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
package cn.stylefeng.roses.kernel.config.modular.controller;

import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitItem;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitRequest;
import cn.stylefeng.roses.kernel.config.modular.entity.SysConfig;
import cn.stylefeng.roses.kernel.config.modular.param.SysConfigParam;
import cn.stylefeng.roses.kernel.config.modular.service.SysConfigService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置控制器
 *
 * @author stylefeng
 * @date 2020/4/13 22:46
 */
@RestController
@ApiResource(name = "参数配置控制器")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 添加系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "添加系统参数配置", path = "/sysConfig/add")
    public ResponseData<?> add(@RequestBody @Validated(SysConfigParam.add.class) SysConfigParam sysConfigParam) {
        sysConfigService.add(sysConfigParam);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "删除系统参数配置", path = "/sysConfig/delete")
    public ResponseData<?> delete(@RequestBody @Validated(SysConfigParam.delete.class) SysConfigParam sysConfigParam) {
        sysConfigService.del(sysConfigParam);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "编辑系统参数配置", path = "/sysConfig/edit")
    public ResponseData<?> edit(@RequestBody @Validated(SysConfigParam.edit.class) SysConfigParam sysConfigParam) {
        sysConfigService.edit(sysConfigParam);
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:12
     */
    @GetResource(name = "查看系统参数配置", path = "/sysConfig/detail")
    public ResponseData<SysConfig> detail(@Validated(SysConfigParam.detail.class) SysConfigParam sysConfigParam) {
        return new SuccessResponseData<>(sysConfigService.detail(sysConfigParam));
    }


    /**
     * 分页查询配置列表
     *
     * @author fengshuonan
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "分页查询配置列表", path = "/sysConfig/page")
    public ResponseData<PageResult<SysConfig>> page(SysConfigParam sysConfigParam) {
        return new SuccessResponseData<>(sysConfigService.findPage(sysConfigParam));
    }

    /**
     * 系统参数配置列表
     *
     * @author fengshuonan
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "系统参数配置列表", path = "/sysConfig/list")
    public ResponseData<List<SysConfig>> list(SysConfigParam sysConfigParam) {
        return new SuccessResponseData<>(sysConfigService.findList(sysConfigParam));
    }

    /**
     * 获取系统配置是否初始化的标志
     *
     * @author fengshuonan
     * @date 2021/7/8 17:20
     */
    @GetResource(name = "获取系统配置是否初始化的标志", path = "/sysConfig/getInitConfigFlag", requiredPermission = false)
    public ResponseData<Boolean> getInitConfigFlag() {
        return new SuccessResponseData<>(sysConfigService.getInitConfigFlag());
    }

    /**
     * 初始化系统配置参数，用在系统第一次登录时
     *
     * @author fengshuonan
     * @date 2021/7/8 16:36
     */
    @PostResource(name = "初始化系统配置参数，用在系统第一次登录时", path = "/sysConfig/initConfig", requiredPermission = false)
    public ResponseData<?> initConfig(@RequestBody ConfigInitRequest configInitRequest) {
        sysConfigService.initConfig(configInitRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取需要初始化的配置列表
     *
     * @author fengshuonan
     * @date 2021/7/8 16:36
     */
    @GetResource(name = "获取需要初始化的配置列表", path = "/sysConfig/getInitConfigList")
    public ResponseData<List<ConfigInitItem>> getInitConfigList() {
        return new SuccessResponseData<>(sysConfigService.getInitConfigs());
    }

    /**
     * 获取后端服务部署的地址
     *
     * @author fengshuonan
     * @date 2021/7/8 16:36
     */
    @GetResource(name = "获取后端服务部署的地址", path = "/sysConfig/getBackendDeployUrl", requiredLogin = false, requiredPermission = false)
    public ResponseData<String> getBackendDeployUrl() {
        String serverDeployHost = sysConfigService.getServerDeployHost();
        return new SuccessResponseData<>(serverDeployHost);
    }

}


