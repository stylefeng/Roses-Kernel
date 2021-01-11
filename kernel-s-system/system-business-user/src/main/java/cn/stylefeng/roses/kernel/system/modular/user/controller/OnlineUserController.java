package cn.stylefeng.roses.kernel.system.modular.user.controller;

import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.system.pojo.user.request.OnlineUserRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 在线用户管理
 *
 * @author fengshuonan
 * @date 2021/1/11 22:52
 */
@RestController
@ApiResource(name = "在线用户管理")
public class OnlineUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 当前在线用户列表
     *
     * @author fengshuonan
     * @date 2021/1/11 22:53
     */
    @GetResource(name = "当前在线用户列表", path = "/sysUser/onlineUserList")
    public ResponseData onlineUserList(OnlineUserRequest onlineUserRequest) {
        return new SuccessResponseData(sysUserService.onlineUserList(onlineUserRequest));
    }

    /**
     * 踢掉在线用户
     *
     * @author fengshuonan
     * @date 2021/1/11 22:53
     */
    @PostResource(name = "踢掉在线用户", path = "/sysUser/removeSession")
    public ResponseData removeSession(@Valid @RequestBody OnlineUserRequest onlineUserRequest) {
        sessionManagerApi.removeSession(onlineUserRequest.getToken());
        return new SuccessResponseData();
    }

}
