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
package cn.stylefeng.roses.kernel.system.modular.user.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.login.LoginDetailsResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleAuthDetail;
import cn.stylefeng.roses.kernel.system.api.pojo.login.details.SimpleUserDetail;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.wrapper.api.BaseWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 登录用户的包装类
 *
 * @author fengshuonan
 * @date 2021/3/22 21:22
 */
public class LoginUserWrapper implements BaseWrapper<LoginUser> {

    @Override
    public Map<String, Object> doWrap(LoginUser loginUser) {
        SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);

        LoginDetailsResponse loginDetailsResponse = new LoginDetailsResponse();

        // 获取用户详细信息
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();

        // 获取用户角色信息
        List<SimpleRoleInfo> simpleRoleInfoList = loginUser.getSimpleRoleInfoList();

        // 组装用户信息
        SimpleUserDetail simpleUserDetail = new SimpleUserDetail();
        simpleUserDetail.setUserId(loginUser.getUserId());
        simpleUserDetail.setName(simpleUserInfo.getRealName());

        // 设置头像，并获取头像的url
        Long avatarFileId = loginUser.getSimpleUserInfo().getAvatar();
        String userAvatarUrl = sysUserService.getUserAvatarUrl(avatarFileId, loginUser.getToken());

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
        loginDetailsResponse.setAuthorities(authInfos);
        loginDetailsResponse.setRoles(authInfos);

        // 登录人的ws-url
        loginDetailsResponse.setWsUrl(loginUser.getWsUrl());

        return BeanUtil.beanToMap(loginDetailsResponse);
    }

}
