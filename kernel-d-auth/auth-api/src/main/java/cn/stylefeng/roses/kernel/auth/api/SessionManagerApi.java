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
package cn.stylefeng.roses.kernel.auth.api;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

import java.util.List;

/**
 * 用户会话管理
 * <p>
 * 会话指的是用户登录后和服务器一直保持一个交互状态的维护
 * <p>
 * 会话具有时效性，反之，当用户不再访问系统的时候，会话应该自动失效
 *
 * @author fengshuonan
 * @date 2020/10/19 16:47
 */
public interface SessionManagerApi {

    /**
     * 创建会话
     *
     * @param token     用户登录的token
     * @param loginUser 登录的用户
     * @author fengshuonan
     * @date 2020/10/19 16:47
     */
    void createSession(String token, LoginUser loginUser, Boolean createCookie);

    /**
     * 更新当前会话的loginUser对象的内容
     *
     * @param token     用户的当前token
     * @param loginUser 新的登录用户信息
     * @author fengshuonan
     * @date 2021/1/9 10:43
     */
    void updateSession(String token, LoginUser loginUser);

    /**
     * 通过token获取会话
     *
     * @param token 用户token
     * @return token对应用户的详细信息
     * @author fengshuonan
     * @date 2020/10/19 16:48
     */
    LoginUser getSession(String token);

    /**
     * 根据token删除一个会话
     *
     * @param token 用户token
     * @author fengshuonan
     * @date 2020/10/19 16:48
     */
    void removeSession(String token);

    /**
     * 删除用户所有的会话，但除了参数传的token的会话
     * <p>
     * 用在单端登录中，一个账号只能在一个浏览器登录
     *
     * @param token 用户token
     * @author fengshuonan
     * @date 2020/10/21 16:18
     */
    void removeSessionExcludeToken(String token);

    /**
     * 判断一个token是否还存在会话状态
     *
     * @param token 用户token
     * @return true-存在会话，false-不存在会话或者失效了
     * @author fengshuonan
     * @date 2020/10/19 16:49
     */
    boolean haveSession(String token);

    /**
     * 刷新会话的过期时间，刷新后用户当前的过期时间将重置
     *
     * @param token 用户的token
     * @author fengshuonan
     * @date 2020/10/19 16:50
     */
    void refreshSession(String token);

    /**
     * 销毁当前用户对应的会话cookie
     * <p>
     * 一般用在单体不分离版本中
     *
     * @author fengshuonan
     * @date 2021/1/2 20:25
     */
    void destroySessionCookie();

    /**
     * 获取在线用户列表
     *
     * @author peihongwei
     * @date 2021/1/9 10:41
     */
    List<LoginUser> onlineUserList();

}
