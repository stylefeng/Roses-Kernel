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
package cn.stylefeng.roses.kernel.system.api.enums;

import cn.stylefeng.roses.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import lombok.Getter;

/**
 * 用户状态的枚举
 *
 * @author fengshuonan
 * @date 2020/10/20 18:19
 */
@Getter
public enum UserStatusEnum {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用"),

    /**
     * 冻结
     */
    FREEZE(3, "冻结");

    private final Integer code;

    private final String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转化为enum
     *
     * @author fengshuonan
     * @date 2020/10/21 9:29
     */
    public static UserStatusEnum toEnum(Integer code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode().equals(code)) {
                return userStatusEnum;
            }
        }
        return null;
    }

    /**
     * 获取code对应的message
     *
     * @author fengshuonan
     * @date 2020/10/21 9:29
     */
    public static String getCodeMessage(Integer code) {
        UserStatusEnum userStatusEnum = toEnum(code);
        if (userStatusEnum != null) {
            return userStatusEnum.getMessage();
        } else {
            return "";
        }
    }

    /**
     * 检查请求参数的状态是否正确
     *
     * @author stylefeng
     * @date 2020/4/30 22:43
     */
    public static void validateUserStatus(Integer code) {
        if (code == null) {
            throw new SystemModularException(SysUserExceptionEnum.REQUEST_USER_STATUS__EMPTY);
        }
        if (ENABLE.getCode().equals(code) || DISABLE.getCode().equals(code) || FREEZE.getCode().equals(code)) {
            return;
        }
        throw new SystemModularException(SysUserExceptionEnum.REQUEST_USER_STATUS_ERROR, code);
    }

}
