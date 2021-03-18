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
package cn.stylefeng.roses.kernel.auth.permission;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.PermissionServiceApi;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.AUTH_EXPIRED_ERROR;
import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.PERMISSION_RES_VALIDATE_ERROR;

/**
 * 权限相关的service
 *
 * @author fengshuonan
 * @date 2020/10/22 15:49
 */
@Service
public class PermissionServiceImpl implements PermissionServiceApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Override
    public void checkPermission(String token, String requestUrl) {

        // 1. 校验token是否传参
        if (StrUtil.isEmpty(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }

        // 2. 获取token对应的用户信息
        LoginUser session = sessionManagerApi.getSession(token);
        if (session == null) {
            throw new AuthException(AUTH_EXPIRED_ERROR);
        }

        // 3. 验证用户有没有当前url的权限
        Set<String> resourceUrls = session.getResourceUrls();
        if (resourceUrls == null || resourceUrls.size() == 0) {
            throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
        } else {
            if (!resourceUrls.contains(requestUrl)) {
                throw new AuthException(PERMISSION_RES_VALIDATE_ERROR);
            }
        }
    }

}
