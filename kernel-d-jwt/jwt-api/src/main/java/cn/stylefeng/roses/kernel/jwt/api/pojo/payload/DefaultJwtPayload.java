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
package cn.stylefeng.roses.kernel.jwt.api.pojo.payload;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Map;

/**
 * jwt的载体，也就是jwt本身带的一些信息
 *
 * @author fengshuonan
 * @date 2020/10/21 11:37
 */
@Data
public class DefaultJwtPayload {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 唯一表示id, 用于缓存登录用户的唯一凭证
     */
    private String uuid;

    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    /**
     * 过期时间戳
     */
    private Long expirationDate;

    /**
     * 单点认证中心的用户会话id，一般为一个uuid
     * <p>
     * 用来校验单点中心是否有本用户的会话
     */
    private String caToken;

    /**
     * 其他载体信息
     */
    private Map<String, Object> others;

    public DefaultJwtPayload() {
    }

    public DefaultJwtPayload(Long userId, String account, boolean rememberMe, String caToken) {
        this.userId = userId;
        this.account = account;
        this.uuid = IdUtil.fastUUID();
        this.rememberMe = rememberMe;
        this.caToken = caToken;
    }

}
