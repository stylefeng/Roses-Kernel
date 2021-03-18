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
package cn.stylefeng.roses.kernel.auth.api.cookie;

import javax.servlet.http.Cookie;

/**
 * cookie的创建器，用在session创建时，给httpServletResponse添加cookie
 * <p>
 * 每个公司情况不一样，所以预留拓展接口
 *
 * @author fengshuonan
 * @date 2020/12/27 13:28
 */
public abstract class SessionCookieCreator {

    /**
     * 创建cookie的操作
     * <p>
     * 这里不要重写这个方法，重写后名称对不上可能导致登录后权限校验失败
     *
     * @param cookieName            cookie的名称
     * @param cookieValue           cookie的值
     * @param sessionExpiredSeconds cookie过期时间
     * @author fengshuonan
     * @date 2020/12/27 13:29
     */
    public Cookie createCookie(String cookieName, String cookieValue, Integer sessionExpiredSeconds) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(sessionExpiredSeconds);
        this.expandCookieProp(cookie);
        return cookie;
    }

    /**
     * 拓展cookie的配置
     *
     * @author fengshuonan
     * @date 2020/12/27 13:41
     */
    public abstract void expandCookieProp(Cookie cookie);

}
