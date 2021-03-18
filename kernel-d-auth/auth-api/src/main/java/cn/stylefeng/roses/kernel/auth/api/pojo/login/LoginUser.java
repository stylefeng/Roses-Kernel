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
package cn.stylefeng.roses.kernel.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 登录用户信息
 *
 * @author fengshuonan
 * @date 2020/10/17 9:58
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 超级管理员标识，true-是超级管理员
     */
    private Boolean superAdmin;

    /**
     * 用户基本信息
     */
    private SimpleUserInfo simpleUserInfo;

    /**
     * 用户角色信息
     */
    private List<SimpleRoleInfo> simpleRoleInfoList;

    /**
     * 公司/组织id
     */
    private Long organizationId;

    /**
     * 职务信息
     */
    private Long positionId;

    /**
     * 用户数据范围信息
     */
    private Set<DataScopeTypeEnum> dataScopeTypeEnums;
    private Set<Long> dataScopeUserIds;
    private Set<Long> dataScopeOrganizationIds;

    /**
     * 可用资源集合
     */
    private Set<String> resourceUrls;

    /**
     * 用户拥有的按钮编码集合
     */
    private Set<String> buttonCodes;

    /**
     * 登录的时间
     */
    private Date loginTime;

    /**
     * 用户的token，当返回用户会话信息时候回带token
     */
    private String token;

    /**
     * 其他信息，Dict为Map的拓展
     */
    private Dict otherInfos;

    /**
     * 用户的ws-url
     */
    private String wsUrl;

    /**
     * 当前用户语种的标识，例如：chinese，english
     * <p>
     * 这个值是根据字典获取，字典类型编码 languages
     * <p>
     * 默认语种是中文
     */
    private String tranLanguageCode = RuleConstants.CHINES_TRAN_LANGUAGE_CODE;

    public String getWsUrl() {
        AtomicReference<String> returnUrl = new AtomicReference<>(StrUtil.EMPTY);
        Optional.ofNullable(this.wsUrl).ifPresent(url -> {
            Map<String, Long> user = new HashMap<>(1);
            user.put("userId", this.userId);
            returnUrl.set(StrUtil.format(url, user));
        });
        return returnUrl.get();
    }

}
