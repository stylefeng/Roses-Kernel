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
package cn.stylefeng.roses.kernel.auth.api.loginuser.api;

import cn.stylefeng.roses.kernel.auth.api.loginuser.pojo.LoginUserRequest;
import cn.stylefeng.roses.kernel.auth.api.loginuser.pojo.SessionValidateResponse;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取当前登录用户的远程调用方法，供微服务使用
 *
 * @author fengshuonan
 * @date 2021/9/29 10:08
 */
public interface LoginUserRemoteApi {

    /**
     * 通过token获取登录的用户
     *
     * @author fengshuonan
     * @date 2021/9/29 10:08
     */
    @RequestMapping(value = "/loginUserRemote/getLoginUserByToken", method = RequestMethod.POST)
    LoginUser getLoginUserByToken(@RequestBody LoginUserRequest loginUserRequest);

    /**
     * 判断token是否存在会话
     *
     * @author fengshuonan
     * @date 2021/9/29 11:39
     */
    @RequestMapping(value = "/loginUserRemote/haveSession", method = RequestMethod.GET)
    SessionValidateResponse haveSession(@RequestParam("token") String token);

    /**
     * 通过loginUser获取刷新后的LoginUser对象
     *
     * @author fengshuonan
     * @date 2021/9/29 11:39
     */
    @RequestMapping(value = "/loginUserRemote/getEffectiveLoginUser", method = RequestMethod.POST)
    LoginUser getEffectiveLoginUser(@RequestBody LoginUser loginUser);

}

