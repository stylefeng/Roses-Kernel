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
package cn.stylefeng.roses.kernel.auth.api.enums;

import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import lombok.Getter;

import static cn.stylefeng.roses.kernel.auth.api.exception.enums.AuthExceptionEnum.DATA_SCOPE_ERROR;

/**
 * 数据范围类型枚举，数据范围的值越小，数据权限越小
 *
 * @author fengshuonan
 * @date 2020/11/5 15:22
 */
@Getter
public enum DataScopeTypeEnum {

    /**
     * 仅本人数据
     */
    SELF(10, "仅本人数据"),

    /**
     * 本部门数据
     */
    DEPT(20, "本部门数据"),

    /**
     * 本部门及以下数据
     */
    DEPT_WITH_CHILD(30, "本部门及以下数据"),

    /**
     * 指定部门数据
     */
    DEFINE(40, "指定部门数据"),

    /**
     * 全部数据
     */
    ALL(50, "全部数据");

    private final Integer code;

    private final String message;

    DataScopeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     *
     * @author fengshuonan
     * @date 2020/10/29 18:59
     */
    public static DataScopeTypeEnum codeToEnum(Integer code) {
        if (null != code) {
            for (DataScopeTypeEnum dataScopeTypeEnum : DataScopeTypeEnum.values()) {
                if (dataScopeTypeEnum.getCode().equals(code)) {
                    return dataScopeTypeEnum;
                }
            }
        }
        throw new AuthException(DATA_SCOPE_ERROR, code);
    }

}
