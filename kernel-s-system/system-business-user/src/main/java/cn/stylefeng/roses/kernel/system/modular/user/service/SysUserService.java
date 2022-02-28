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
package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.api.UserServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.UserSelectTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统用户service
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
public interface SysUserService extends IService<SysUser>, UserServiceApi {

    /**
     * 增加用户
     *
     * @param sysUserRequest 请求参数封装
     * @author fengshuonan
     * @date 2020/11/21 12:32
     */
    void add(SysUserRequest sysUserRequest);

    /**
     * 删除系统用户
     *
     * @param sysUserRequest 删除参数
     * @author fengshuonan
     * @date 2020/11/21 14:54
     */
    void del(SysUserRequest sysUserRequest);

    /**
     * 编辑用户
     *
     * @param sysUserRequest 请求参数封装
     * @author fengshuonan
     * @date 2020/11/21 12:32
     */
    void edit(SysUserRequest sysUserRequest);

    /**
     * 更新用户信息（一般用于更新个人信息）
     *
     * @param sysUserRequest 请求参数封装
     * @author fengshuonan
     * @date 2020/11/21 12:32
     */
    void editInfo(SysUserRequest sysUserRequest);

    /**
     * 修改状态
     *
     * @param sysUserRequest 请求参数封装
     * @author fengshuonan
     * @date 2020/11/21 14:19
     */
    void editStatus(SysUserRequest sysUserRequest);

    /**
     * 修改密码
     *
     * @param sysUserRequest 请求参数封装
     * @author fengshuonan
     * @date 2020/11/21 14:26
     */
    void editPassword(SysUserRequest sysUserRequest);

    /**
     * 重置密码
     *
     * @param sysUserRequest 重置参数
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void resetPassword(SysUserRequest sysUserRequest);

    /**
     * 修改头像
     *
     * @param sysUserRequest 修改头像参数
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void editAvatar(SysUserRequest sysUserRequest);

    /**
     * 授权角色给某个用户
     *
     * @param sysUserRequest 授权参数
     * @author fengshuonan
     * @date 2020/11/21 14:43
     */
    void grantRole(SysUserRequest sysUserRequest);

    /**
     * 授权组织机构数据范围给某个用户
     *
     * @param sysUserRequest 授权参数
     * @author fengshuonan
     * @date 2020/11/21 14:48
     */
    void grantData(SysUserRequest sysUserRequest);

    /**
     * 查看用户详情
     *
     * @param sysUserRequest 查看参数
     * @return 用户详情结果
     * @author luojie
     * @date 2020/11/6 13:46
     */
    SysUserDTO detail(SysUserRequest sysUserRequest);

    /**
     * 查询系统用户
     *
     * @param sysUserRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/21 15:24
     */
    PageResult<SysUserDTO> findPage(SysUserRequest sysUserRequest);

    /**
     * 查询系统用户
     *
     * @param sysUserRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/21 15:24
     */
    List<SysUserDTO> getUserList(SysUserRequest sysUserRequest);

    /**
     * 导出用户
     *
     * @param response httpResponse
     * @author luojie
     * @date 2020/11/6 13:47
     */
    void export(HttpServletResponse response);

    /**
     * 用户选择树数据
     *
     * @param sysUserRequest 参数
     * @author liuhanqing
     * @date 2021/1/15 11:16
     */
    List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest);

    /**
     * 根据账号获取用户
     *
     * @param account 账号
     * @return 用户
     * @author luojie
     * @date 2020/11/6 15:09
     */
    SysUser getUserByAccount(String account);

    /**
     * 获取用户头像的url
     *
     * @param fileId 文件id
     * @author fengshuonan
     * @date 2020/12/27 19:13
     */
    String getUserAvatarUrl(Long fileId);

    /**
     * 获取用户头像的url
     *
     * @param fileId 文件id
     * @param token  预览文件带的token
     * @author fengshuonan
     * @date 2020/12/27 19:13
     */
    String getUserAvatarUrl(Long fileId, String token);

    /**
     * 根据机构id获取用户树节点列表
     *
     * @param orgId 机构id
     * @author liuhanqing
     * @date 2021/1/15 11:16
     */
    List<UserSelectTreeNode> getUserTreeNodeList(Long orgId, List<UserSelectTreeNode> treeNodeList);

    /**
     * 用户下拉列表选择
     *
     * @param sysUserRequest 查询参数
     * @return 用户列表集合
     * @author luojie
     * @date 2020/11/6 13:47
     */
    List<SimpleDict> selector(SysUserRequest sysUserRequest);

    /**
     * 批量删除用户
     *
     * @author fengshuonan
     * @date 2021/4/7 16:13
     */
    void batchDelete(SysUserRequest sysUserRequest);

    /**
     * 获取所有用户的id
     *
     * @author fengshuonan
     * @date 2021/6/20 12:10
     */
    List<Long> getAllUserIds();

    /**
     * 获取所有用户ID和名称列表
     *
     * @return {@link List< SysUserRequest>}
     * @author majianguo
     * @date 2022/1/17 15:05
     **/
    List<SysUserRequest> getAllUserIdList();

    /**
     * 根据用户主键获取用户对应的token
     *
     * @return {@link String}
     * @author majianguo
     * @date 2022/1/17 15:05
     **/
    String getTokenByUserId(Long userId);

    /**
     * 运维平台接口检测
     *
     * @return {@link Integer}
     * @author majianguo
     * @date 2022/1/27 14:29
     **/
    Integer devopsApiCheck(SysUserRequest sysUserRequest);
}
