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
package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.rule.enums.SexEnum;
import cn.stylefeng.roses.kernel.system.api.MenuServiceApi;
import cn.stylefeng.roses.kernel.system.api.enums.MenuFrontTypeEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexRoleInfo;
import cn.stylefeng.roses.kernel.system.api.pojo.login.v3.IndexUserInfoV3;
import cn.stylefeng.roses.kernel.system.modular.user.service.IndexUserInfoService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取首页用户登录信息
 *
 * @author fengshuonan
 * @date 2022/4/8 15:40
 */
@Service
public class IndexUserInfoServiceImpl implements IndexUserInfoService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private MenuServiceApi menuServiceApi;

    @Override
    public IndexUserInfoV3 userInfoV3(Integer menuFrontType) {

        // 获取当前登录用户
        LoginUser loginUser = LoginContext.me().getLoginUser();

        IndexUserInfoV3 indexUserInfoV3 = new IndexUserInfoV3();

        // 设置用户id
        indexUserInfoV3.setUserId(loginUser.getUserId());

        // 设置用户账号
        indexUserInfoV3.setUsername(loginUser.getAccount());

        // 设置昵称
        indexUserInfoV3.setNickname(loginUser.getSimpleUserInfo().getNickName());

        // 设置头像，并获取头像的url
        Long avatarFileId = loginUser.getSimpleUserInfo().getAvatar();
        String userAvatarUrl = sysUserService.getUserAvatarUrl(avatarFileId, loginUser.getToken());
        indexUserInfoV3.setAvatar(userAvatarUrl);

        // 设置性别
        indexUserInfoV3.setSex(loginUser.getSimpleUserInfo().getSex());

        // 设置电话
        indexUserInfoV3.setPhone(loginUser.getSimpleUserInfo().getPhone());

        // 设置邮箱
        indexUserInfoV3.setEmail(loginUser.getSimpleUserInfo().getEmail());

        // 设置出生日期
        indexUserInfoV3.setBirthday(loginUser.getSimpleUserInfo().getBirthday());

        // 设置组织机构id
        indexUserInfoV3.setOrganizationId(loginUser.getOrganizationId());

        // 设置状态（暂时不设置）
        indexUserInfoV3.setStatus(null);

        // 设置性别名称
        indexUserInfoV3.setSexName(SexEnum.codeToMessage(indexUserInfoV3.getSex()));

        // 设置组织机构名称，暂时不设置，用到再设置
        indexUserInfoV3.setOrganizationName(null);

        // 设置角色
        indexUserInfoV3.setRoles(buildRoles(loginUser));

        // 获取用户菜单和权限信息
        if (ObjectUtil.isEmpty(menuFrontType)) {
            menuFrontType = MenuFrontTypeEnum.FRONT.getCode();
        }
        indexUserInfoV3.setAuthorities(menuServiceApi.buildAuthorities(menuFrontType));

        // 登录人的ws-url
        indexUserInfoV3.setWsUrl(loginUser.getWsUrl());

        // 权限编码
        indexUserInfoV3.setAuthCodes(loginUser.getButtonCodes());

        return indexUserInfoV3;
    }

    /**
     * 获取用户的角色信息
     *
     * @author fengshuonan
     * @date 2022/4/8 15:53
     */
    private List<IndexRoleInfo> buildRoles(LoginUser loginUser) {

        List<SimpleRoleInfo> simpleRoleInfoList = loginUser.getSimpleRoleInfoList();

        ArrayList<IndexRoleInfo> indexRoleInfos = new ArrayList<>();
        if (simpleRoleInfoList.size() > 0) {
            for (SimpleRoleInfo simpleRoleInfo : simpleRoleInfoList) {
                IndexRoleInfo indexRoleInfo = new IndexRoleInfo();
                BeanUtil.copyProperties(simpleRoleInfo, indexRoleInfo);
                indexRoleInfos.add(indexRoleInfo);
            }
        }

        return indexRoleInfos;
    }

}
