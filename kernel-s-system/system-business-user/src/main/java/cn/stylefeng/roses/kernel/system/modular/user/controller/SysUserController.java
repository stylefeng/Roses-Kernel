package cn.stylefeng.roses.kernel.system.modular.user.controller;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户管理控制器
 *
 * @author luojie
 * @date 2020/11/6 09:47
 */
@RestController
@ApiResource(name = "用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 增加用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_增加", path = "/sysUser/add")
    public ResponseData add(@RequestBody @Validated(BaseRequest.add.class) SysUserRequest sysUserRequest) {
        sysUserService.add(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_编辑", path = "/sysUser/edit")
    public ResponseData edit(@RequestBody @Validated(SysUserRequest.edit.class) SysUserRequest sysUserRequest) {
        sysUserService.edit(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 更新用户个人信息
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_更新个人信息", path = "/sysUser/updateInfo")
    public ResponseData updateInfo(@RequestBody @Validated(SysUserRequest.updateInfo.class) SysUserRequest sysUserRequest) {
        sysUserService.updateInfo(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_修改状态", path = "/sysUser/changeStatus")
    public ResponseData changeStatus(@RequestBody @Validated(SysUserRequest.changeStatus.class) SysUserRequest sysUserRequest) {
        sysUserService.changeStatus(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改密码
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_修改密码", path = "/sysUser/updatePassword")
    public ResponseData updatePwd(@RequestBody @Validated(SysUserRequest.updatePwd.class) SysUserRequest sysUserRequest) {
        sysUserService.updatePassword(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 重置密码
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "系统用户_重置密码", path = "/sysUser/resetPwd")
    public ResponseData resetPwd(@RequestBody @Validated(SysUserRequest.resetPwd.class) SysUserRequest sysUserRequest) {
        sysUserService.resetPassword(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改头像
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "系统用户_修改头像", path = "/sysUser/updateAvatar")
    public ResponseData updateAvatar(@RequestBody @Validated(SysUserRequest.updateAvatar.class) SysUserRequest sysUserRequest) {
        sysUserService.updateAvatar(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权角色
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权角色", path = "/sysUser/grantRole")
    public ResponseData grantRole(@RequestBody @Validated(SysUserRequest.grantRole.class) SysUserRequest sysUserRequest) {
        sysUserService.grantRole(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权数据", path = "/sysUser/grantData")
    public ResponseData grantData(@RequestBody @Validated(SysUserRequest.grantData.class) SysUserRequest sysUserRequest) {
        sysUserService.grantData(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_删除", path = "/sysUser/delete")
    public ResponseData delete(@RequestBody @Validated(SysUserRequest.delete.class) SysUserRequest sysUserRequest) {
        sysUserService.delete(sysUserRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_查看", path = "/sysUser/detail")
    public ResponseData detail(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.detail(sysUserRequest));
    }

    /**
     * 查询系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "系统用户_查询", path = "/sysUser/page")
    public ResponseData page(SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.page(sysUserRequest));
    }

    /**
     * 获取用户的角色列表
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_获取用户的角色列表", path = "/sysUser/getUserRoles")
    public ResponseData ownRole(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        Long userId = sysUserRequest.getUserId();
        return new SuccessResponseData(sysUserRoleService.getUserRoles(userId));
    }

    /**
     * 获取用户数据范围列表
     *
     * @author luojie
     * @date 2020/11/6 13:51
     */
    @GetResource(name = "系统用户_获取用户数据范围列表", path = "/sysUser/getUserDataScope")
    public ResponseData ownData(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        List<Long> userBindDataScope = sysUserService.getUserBindDataScope(sysUserRequest.getUserId());
        return new SuccessResponseData(userBindDataScope);
    }

    /**
     * 用户下拉列表，可以根据姓名搜索
     *
     * @param sysUserRequest 请求参数：name 姓名(可选)
     * @return 返回除超级管理员外的用户列表
     * @author luojie
     * @date 2020/11/6 09:49
     */
    @GetResource(name = "系统用户_选择器", path = "/sysUser/selector")
    public ResponseData selector(SysUserRequest sysUserRequest) {
        return new SuccessResponseData(sysUserService.selector(sysUserRequest));
    }

    /**
     * 导出用户
     *
     * @author luojie
     * @date 2020/11/6 13:57
     */
    @GetResource(name = "系统用户_导出", path = "/sysUser/export")
    public void export(HttpServletResponse response) {
        sysUserService.export(response);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @author fengshuonan
     * @date 2021/1/1 19:01
     */
    @GetResource(name = "获取当前登录用户的信息", path = "/sysUser/currentUserInfo", requiredPermission = false)
    public ResponseData currentUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setUserId(loginUser.getUserId());
        return new SuccessResponseData(sysUserService.detail(sysUserRequest));
    }

}
