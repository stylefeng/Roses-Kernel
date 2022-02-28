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
package cn.stylefeng.roses.kernel.monitor.system.controller;

import cn.stylefeng.roses.kernel.monitor.system.SystemHardwareCalculator;
import cn.stylefeng.roses.kernel.monitor.system.holder.SystemHardwareInfoHolder;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统监控控制器
 *
 * @author fengshuonan
 * @date 2021/4/14 18:44
 */
@RestController
@ApiResource(name = "监控的控制器")
public class MonitorStatusController {

    @Resource
    private SystemHardwareInfoHolder systemHardwareInfoHolder;

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    @GetResource(name = "获取系统信息", path = "/getSystemInfo")
    public ResponseData<SystemHardwareCalculator> getSystemInfo() {
        SystemHardwareCalculator systemHardwareInfo = systemHardwareInfoHolder.getSystemHardwareInfo();
        return new SuccessResponseData<>(systemHardwareInfo);
    }

}
