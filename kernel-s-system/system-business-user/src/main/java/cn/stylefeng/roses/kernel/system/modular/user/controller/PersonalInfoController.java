package cn.stylefeng.roses.kernel.system.modular.user.controller;

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
    public ResponseData updateInfo(@RequestBody @Validated(SysUserRequest.updateInfo.class) SysUserRequest sysUserRequest) {
        sysUserService.editInfo(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改密码
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "个人信息_修改密码", path = "/sysUser/updatePassword", requiredPermission = false)
    public ResponseData updatePwd(@RequestBody @Validated(SysUserRequest.updatePwd.class) SysUserRequest sysUserRequest) {
        sysUserService.editPassword(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改头像
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "个人信息_修改头像", path = "/sysUser/updateAvatar", requiredPermission = false)
    public ResponseData updateAvatar(@RequestBody @Validated(SysUserRequest.updateAvatar.class) SysUserRequest sysUserRequest) {
        sysUserService.editAvatar(sysUserRequest);
        return new SuccessResponseData();
    }

}
