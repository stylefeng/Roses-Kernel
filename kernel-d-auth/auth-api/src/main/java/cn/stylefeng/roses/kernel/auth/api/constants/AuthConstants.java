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
package cn.stylefeng.roses.kernel.auth.api.constants;

/**
 * auth，鉴权模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/16 11:05
 */
public interface AuthConstants {

    /**
     * auth模块的名称
     */
    String AUTH_MODULE_NAME = "kernel-d-auth";

    /**
     * 异常枚举的步进值
     */
    String AUTH_EXCEPTION_STEP_CODE = "03";

    /**
     * 登录用户的缓存前缀
     */
    String LOGGED_TOKEN_PREFIX = "LOGGED_TOKEN_";

    /**
     * 登录用户id的缓存前缀
     */
    String LOGGED_USERID_PREFIX = "LOGGED_USERID_";

    /**
     * 默认http请求携带token的header名称
     */
    String DEFAULT_AUTH_HEADER_NAME = "Authorization";

    /**
     * 获取http请求携带token的param的名称
     */
    String DEFAULT_AUTH_PARAM_NAME = "token";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * auth模块，jwt的失效时间，默认7天
     */
    Long DEFAULT_AUTH_JWT_TIMEOUT_SECONDS = 3600L * 24 * 7;

    /**
     * 验证码 session key
     */
    String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

}
