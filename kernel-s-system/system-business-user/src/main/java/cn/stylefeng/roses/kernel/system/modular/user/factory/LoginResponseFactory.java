package cn.stylefeng.roses.kernel.system.modular.user.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.auth.LoginResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.login.LoginDetailsResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleAuthDetail;
import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleUserDetail;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录返回结果组装
 *
 * @author fengshuonan
 * @date 2021/1/7 17:11
 */
public class LoginResponseFactory {

    /**
     * 用于登录接口的返回值创建
     *
     * @author fengshuonan
     * @date 2021/1/7 17:12
     */
    public static LoginDetailsResponse createLoginDetail(LoginResponse loginResponse) {

        SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);

        LoginDetailsResponse loginDetailsResponse = new LoginDetailsResponse();

        // 获取用户详细信息
        LoginUser loginUser = loginResponse.getLoginUser();
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();

        // 获取用户角色信息
        List<SimpleRoleInfo> simpleRoleInfoList = loginUser.getSimpleRoleInfoList();

        // 组装token和过期时间
        loginDetailsResponse.setToken(loginResponse.getToken());
        loginDetailsResponse.setExpireAt(loginResponse.getExpireAt());

        // 组装用户信息
        SimpleUserDetail simpleUserDetail = new SimpleUserDetail();
        simpleUserDetail.setUserId(loginUser.getUserId());
        simpleUserDetail.setName(simpleUserInfo.getRealName());

        // todo 地址信息
        simpleUserDetail.setAddress("北京市");
        simpleUserDetail.setPosition("总经理");

        // 设置头像，并获取头像的url
        Long avatarFileId = loginResponse.getLoginUser().getSimpleUserInfo().getAvatar();
        String userAvatarUrl = sysUserService.getUserAvatarUrl(avatarFileId, loginResponse.getToken());

        simpleUserDetail.setAvatar(userAvatarUrl);
        loginDetailsResponse.setUser(simpleUserDetail);

        // 组装权限
        ArrayList<SimpleAuthDetail> authInfos = new ArrayList<>();
        for (SimpleRoleInfo simpleRoleInfo : simpleRoleInfoList) {
            SimpleAuthDetail simpleAuthDetail = new SimpleAuthDetail();
            simpleAuthDetail.setId(simpleRoleInfo.getRoleCode());
            // todo 没有按钮信息
            simpleAuthDetail.setOperation(null);
            authInfos.add(simpleAuthDetail);
        }
        loginDetailsResponse.setPermissions(authInfos);
        loginDetailsResponse.setRoles(authInfos);

        // 登录人的ws-url
        loginDetailsResponse.setWsUrl(loginUser.getWsUrl());

        return loginDetailsResponse;
    }

}
