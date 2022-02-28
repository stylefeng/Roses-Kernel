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
package cn.stylefeng.roses.kernel.system.api;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.system.api.pojo.user.OnlineUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.UserLoginInfoDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.OnlineUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户管理服务接口
 *
 * @author fengshuonan
 * @date 2020/10/20 13:20
 */
public interface UserServiceApi {

    /**
     * 获取用户登录需要的详细信息（用在第一次获取登录用户）
     *
     * @param account 账号
     * @return 用户登录需要的详细信息
     * @author fengshuonan
     * @date 2020/10/20 16:59
     */
    UserLoginInfoDTO getUserLoginInfo(String account);

    /**
     * 获取刷新后的登录用户（用在用户登录之后调用）
     * <p>
     * 以往用户登录后直接从session缓存中获取用户信息，不能及时更新，需要退出才可以获取最新信息
     * <p>
     * 本方法解决了实时获取当前登录用户不准确的问题
     *
     * @author fengshuonan
     * @date 2021/7/29 22:03
     */
    LoginUser getEffectiveLoginUser(LoginUser loginUser);

    /**
     * 更新用户的登录信息，一般为更新用户的登录时间和ip
     *
     * @param userId 用户id
     * @param date   用户登录时间
     * @param ip     用户登录的ip
     * @author fengshuonan
     * @date 2020/10/21 14:13
     */
    void updateUserLoginInfo(Long userId, Date date, String ip);

    /**
     * 根据机构id集合删除对应的用户-数据范围关联信息
     *
     * @param organizationIds 组织架构id集合
     * @author fengshuonan
     * @date 2020/10/20 16:59
     */
    void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds);

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @author majianguo
     * @date 2020/11/5 下午3:57
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 根据角色id删除对应的用户-角色表关联信息
     *
     * @param roleId 角色id
     * @author majianguo
     * @date 2020/11/5 下午3:58
     */
    void deleteUserRoleListByRoleId(Long roleId);

    /**
     * 获取用户单独绑定的数据范围，sys_user_data_scope表中的数据范围
     *
     * @param userId 用户id
     * @return 用户数据范围
     * @author fengshuonan
     * @date 2020/11/21 12:15
     */
    List<Long> getUserBindDataScope(Long userId);

    /**
     * 获取在线用户列表
     *
     * @author fengshuonan
     * @date 2021/1/10 9:56
     */
    List<OnlineUserDTO> onlineUserList(OnlineUserRequest onlineUserRequest);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @author majianguo
     * @date 2021/1/9 19:00
     */
    SysUserDTO getUserInfoByUserId(Long userId);

    /**
     * 查询全部用户ID(剔除被删除的)
     *
     * @param sysUserRequest 查询参数
     * @return List<Long> 用户id 集合
     * @author liuhanqing
     * @date 2021/1/4 22:09
     */
    List<Long> queryAllUserIdList(SysUserRequest sysUserRequest);

    /**
     * 根据用户id 判断用户是否存在
     *
     * @param userId 用户id
     * @return 用户信息
     * @author liuhanqing
     * @date 2021/1/4 22:55
     */
    Boolean userExist(Long userId);

    /**
     * 获取用户的头像url
     *
     * @author fengshuonan
     * @date 2021/12/29 17:27
     */
    String getUserAvatarUrlByUserId(Long userId);

}
