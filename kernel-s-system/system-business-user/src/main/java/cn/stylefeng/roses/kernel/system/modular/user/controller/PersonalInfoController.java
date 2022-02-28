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
package cn.stylefeng.roses.kernel.system.modular.user.controller;

import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 个人信息控制器
 *
 * @author fengshuonan
 * @date 2021/3/17 22:05
 */
@RestController
@ApiResource(name = "个人信息")
public class PersonalInfoController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 更新用户个人信息
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "个人信息_更新个人信息", path = "/sysUser/updateInfo", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updateInfo(@RequestBody @Validated(SysUserRequest.updateInfo.class) SysUserRequest sysUserRequest) {
        sysUserService.editInfo(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改密码
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "个人信息_修改密码", path = "/sysUser/updatePassword", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updatePwd(@RequestBody @Validated(SysUserRequest.updatePwd.class) SysUserRequest sysUserRequest) {
        sysUserService.editPassword(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改头像
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "个人信息_修改头像", path = "/sysUser/updateAvatar", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updateAvatar(@RequestBody @Validated(SysUserRequest.updateAvatar.class) SysUserRequest sysUserRequest) {
        sysUserService.editAvatar(sysUserRequest);
        return new SuccessResponseData<>();
    }

}
