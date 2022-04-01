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

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.UserSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.system.modular.user.wrapper.UserExpandWrapper;
import cn.stylefeng.roses.kernel.wrapper.api.annotation.Wrapper;
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
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(BaseRequest.add.class) SysUserRequest sysUserRequest) {
        sysUserService.add(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_删除", path = "/sysUser/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysUserRequest.delete.class) SysUserRequest sysUserRequest) {
        sysUserService.del(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除系统用户
     *
     * @author fengshuonan
     * @date 2021/4/7 16:12
     */
    @PostResource(name = "系统用户_批量删除系统用户", path = "/sysUser/batchDelete")
    @BusinessLog
    public ResponseData<?> batchDelete(@RequestBody @Validated(SysUserRequest.batchDelete.class) SysUserRequest sysUserRequest) {
        sysUserService.batchDelete(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_编辑", path = "/sysUser/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysUserRequest.edit.class) SysUserRequest sysUserRequest) {
        sysUserService.edit(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改状态
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_修改状态", path = "/sysUser/changeStatus")
    @BusinessLog
    public ResponseData<?> changeStatus(@RequestBody @Validated(SysUserRequest.changeStatus.class) SysUserRequest sysUserRequest) {
        sysUserService.editStatus(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 重置密码
     *
     * @author luojie
     * @date 2020/11/6 13:48
     */
    @PostResource(name = "系统用户_重置密码", path = "/sysUser/resetPwd")
    @BusinessLog
    public ResponseData<?> resetPwd(@RequestBody @Validated(SysUserRequest.resetPwd.class) SysUserRequest sysUserRequest) {
        sysUserService.resetPassword(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 授权角色
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权角色", path = "/sysUser/grantRole")
    @BusinessLog
    public ResponseData<?> grantRole(@RequestBody @Validated(SysUserRequest.grantRole.class) SysUserRequest sysUserRequest) {
        sysUserService.grantRole(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 授权数据
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @PostResource(name = "系统用户_授权数据", path = "/sysUser/grantData")
    @BusinessLog
    public ResponseData<?> grantData(@RequestBody @Validated(SysUserRequest.grantData.class) SysUserRequest sysUserRequest) {
        sysUserService.grantData(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_查看", path = "/sysUser/detail")
    public ResponseData<SysUserDTO> detail(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.detail(sysUserRequest));
    }

    /**
     * 获取当前登录用户的信息
     *
     * @author fengshuonan
     * @date 2021/1/1 19:01
     */
    @GetResource(name = "获取当前登录用户的信息", path = "/sysUser/currentUserInfo", requiredPermission = false)
    public ResponseData<SysUserDTO> currentUserInfo() {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setUserId(loginUser.getUserId());
        return new SuccessResponseData<>(sysUserService.detail(sysUserRequest));
    }

    /**
     * 查询系统用户
     *
     * @author luojie
     * @date 2020/11/6 13:49
     */
    @GetResource(name = "系统用户_查询", path = "/sysUser/page")
    @Wrapper(UserExpandWrapper.class)
    public ResponseData<PageResult<SysUserDTO>> page(SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.findPage(sysUserRequest));
    }

    /**
     * 导出用户
     *
     * @author luojie
     * @date 2020/11/6 13:57
     */
    @GetResource(name = "系统用户_导出", path = "/sysUser/export")
    @BusinessLog
    public void export(HttpServletResponse response) {
        sysUserService.export(response);
    }

    /**
     * 获取用户选择树数据（用在系统通知，选择发送人的时候）
     *
     * @author liuhanqing
     * @date 2021/1/15 8:28
     */
    @GetResource(name = "获取用户选择树数据（用在系统通知，选择发送人的时候）", path = "/sysUser/getUserSelectTree")
    public ResponseData<List<UserSelectTreeNode>> getUserTree() {
        return new SuccessResponseData<>(this.sysUserService.userSelectTree(new SysUserRequest()));
    }

    /**
     * 获取用户数据范围列表
     *
     * @author luojie
     * @date 2020/11/6 13:51
     */
    @GetResource(name = "系统用户_获取用户数据范围列表", path = "/sysUser/getUserDataScope")
    public ResponseData<List<Long>> ownData(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        List<Long> userBindDataScope = sysUserService.getUserBindDataScope(sysUserRequest.getUserId());
        return new SuccessResponseData<>(userBindDataScope);
    }

    /**
     * 获取用户的角色列表
     *
     * @author luojie
     * @date 2020/11/6 13:50
     */
    @GetResource(name = "系统用户_获取用户的角色列表", path = "/sysUser/getUserRoles")
    public ResponseData<List<SysUserRole>> ownRole(@Validated(SysUserRequest.detail.class) SysUserRequest sysUserRequest) {
        Long userId = sysUserRequest.getUserId();
        return new SuccessResponseData<>(sysUserRoleService.findListByUserId(userId));
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
    public ResponseData<List<SimpleDict>> selector(SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.selector(sysUserRequest));
    }

    /**
     * 获取所有用户ID和名称列表
     *
     * @author majianguo
     * @date 2022/1/17 14:24
     **/
    @GetResource(name = "获取所有用户ID和名称列表", path = "/sysUser/getAllUserIdList")
    public ResponseData<List<SysUserRequest>> getAllUserIdList() {
        return new SuccessResponseData<>(sysUserService.getAllUserIdList());
    }

    /**
     * 运维平台接口检测
     *
     * @author majianguo
     * @date 2022/1/27 14:29
     **/
    @GetResource(name = "运维平台接口检测", path = "/sysUser/devopsApiCheck", requiredLogin = false, requiredPermission = false)
    public ResponseData<Integer> devopsApiCheck(SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.devopsApiCheck(sysUserRequest));
    }

    /**
     * 根据用户主键获取用户对应的token
     *
     * @author majianguo
     * @date 2022/1/17 14:24
     **/
    @GetResource(name = "根据用户主键获取用户对应的token", path = "/sysUser/getTokenByUserId")
    public ResponseData<String> getTokenByUserId(Long userId) {
        return new SuccessResponseData<>(sysUserService.getTokenByUserId(userId));
    }
}
