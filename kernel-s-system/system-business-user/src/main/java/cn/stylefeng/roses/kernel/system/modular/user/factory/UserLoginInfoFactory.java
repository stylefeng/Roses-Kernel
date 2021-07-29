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
package cn.stylefeng.roses.kernel.system.modular.user.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.auth.api.prop.LoginUserPropExpander;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.login.CurrentUserInfoResponse;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.DataScopeDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserOrgDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组装当前登录用户的信息
 *
 * @author fengshuonan
 * @date 2020/11/26 22:25
 */
public class UserLoginInfoFactory {

    /**
     * 组装登录用户信息对象
     *
     * @param sysUser                 用户信息
     * @param roleResponseList        角色信息
     * @param dataScopeResponse       数据范围信息
     * @param userOrgInfo             组织机构信息
     * @param resourceUrlsListByCodes 用户的所有资源url
     * @param roleButtonCodes         用户的所拥有的按钮编码
     * @author fengshuonan
     * @date 2020/12/26 17:53
     */
    public static UserLoginInfoDTO userLoginInfoDTO(SysUser sysUser,
                                                    List<SysRoleDTO> roleResponseList,
                                                    DataScopeDTO dataScopeResponse,
                                                    SysUserOrgDTO userOrgInfo,
                                                    Set<String> resourceUrlsListByCodes,
                                                    Set<String> roleButtonCodes) {

        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();

        // 设置用户加密的密码和状态
        userLoginInfoDTO.setUserPasswordHexed(sysUser.getPassword());
        userLoginInfoDTO.setUserStatus(sysUser.getStatusFlag());

        // 创建登录用户对象
        LoginUser loginUser = new LoginUser();

        // 填充用户账号，账号id，管理员类型
        loginUser.setAccount(sysUser.getAccount());
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setSuperAdmin(YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag()));

        // 填充用户基本信息
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        BeanUtil.copyProperties(sysUser, simpleUserInfo);
        loginUser.setSimpleUserInfo(simpleUserInfo);

        // 填充用户角色信息
        if (!roleResponseList.isEmpty()) {
            ArrayList<SimpleRoleInfo> simpleRoleInfos = new ArrayList<>();
            for (SysRoleDTO sysRoleResponse : roleResponseList) {
                SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
                BeanUtil.copyProperties(sysRoleResponse, simpleRoleInfo);
                simpleRoleInfos.add(simpleRoleInfo);
            }
            loginUser.setSimpleRoleInfoList(simpleRoleInfos);
        }

        // 填充用户公司和职务
        if (userOrgInfo != null) {
            loginUser.setOrganizationId(userOrgInfo.getOrgId());
            loginUser.setPositionId(userOrgInfo.getPositionId());
        }

        // 设置用户数据范围
        if (dataScopeResponse != null) {
            loginUser.setDataScopeTypeEnums(dataScopeResponse.getDataScopeTypeEnums());
            loginUser.setDataScopeOrganizationIds(dataScopeResponse.getOrganizationIds());
            loginUser.setDataScopeUserIds(dataScopeResponse.getUserIds());
        }

        // 设置用户拥有的资源
        loginUser.setResourceUrls(resourceUrlsListByCodes);

        // 基于接口拓展用户登录信息
        Map<String, LoginUserPropExpander> beansOfLoginUserExpander = SpringUtil.getBeansOfType(LoginUserPropExpander.class);
        if (beansOfLoginUserExpander != null && beansOfLoginUserExpander.size() > 0) {
            for (Map.Entry<String, LoginUserPropExpander> stringLoginUserPropExpanderEntry : beansOfLoginUserExpander.entrySet()) {
                stringLoginUserPropExpanderEntry.getValue().expandAction(loginUser);
            }
        }

        // 填充用户拥有的按钮编码
        loginUser.setButtonCodes(roleButtonCodes);

        // 设置用户的登录时间
        loginUser.setLoginTime(new Date());

        // 响应dto
        userLoginInfoDTO.setLoginUser(loginUser);
        return userLoginInfoDTO;
    }

    /**
     * 转化为当前登陆用户信息的详情
     *
     * @author fengshuonan
     * @date 2021/3/25 10:06
     */
    public static CurrentUserInfoResponse parseUserInfo(LoginUser loginUser) {

        SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);
        CurrentUserInfoResponse currentUserInfoResponse = new CurrentUserInfoResponse();

        // 设置用户id
        currentUserInfoResponse.setUserId(loginUser.getUserId());

        // 设置组织id
        currentUserInfoResponse.setOrganizationId(loginUser.getOrganizationId());

        // 登录人的ws-url
        currentUserInfoResponse.setWsUrl(loginUser.getWsUrl());

        // 设置用户昵称
        currentUserInfoResponse.setNickname(loginUser.getSimpleUserInfo().getNickName());

        // 姓名
        currentUserInfoResponse.setRealName(loginUser.getSimpleUserInfo().getRealName());

        // 设置头像，并获取头像的url
        Long avatarFileId = loginUser.getSimpleUserInfo().getAvatar();
        String userAvatarUrl = sysUserService.getUserAvatarUrl(avatarFileId, loginUser.getToken());
        currentUserInfoResponse.setAvatar(userAvatarUrl);

        // 设置角色
        List<SimpleRoleInfo> simpleRoleInfoList = loginUser.getSimpleRoleInfoList();
        Set<String> roleCodes = simpleRoleInfoList.stream().map(SimpleRoleInfo::getRoleCode).collect(Collectors.toSet());
        currentUserInfoResponse.setRoles(roleCodes);

        // 设置用户拥有的按钮权限
        currentUserInfoResponse.setAuthorities(loginUser.getButtonCodes());

        return currentUserInfoResponse;
    }

}
