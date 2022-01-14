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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

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
    @ChineseDescription("用户主键id")
    private Long userId;

    /**
     * 账号
     */
    @ChineseDescription("账号")
    private String account;

    /**
     * 超级管理员标识，true-是超级管理员
     */
    @ChineseDescription("超级管理员标识，true-是超级管理员")
    private Boolean superAdmin;

    /**
     * 用户基本信息
     */
    @ChineseDescription("用户基本信息")
    private SimpleUserInfo simpleUserInfo;

    /**
     * 用户角色信息
     */
    @ChineseDescription("用户角色信息")
    private List<SimpleRoleInfo> simpleRoleInfoList;

    /**
     * 公司/组织id
     */
    @ChineseDescription("公司/组织id")
    private Long organizationId;

    /**
     * 职务信息
     */
    @ChineseDescription("职务信息")
    private Long positionId;

    /**
     * 用户数据范围枚举
     */
    @ChineseDescription("用户数据范围枚举")
    private Set<DataScopeTypeEnum> dataScopeTypeEnums;

    /**
     * 用户数据范围用户信息
     */
    @ChineseDescription("用户数据范围用户信息")
    private Set<Long> dataScopeUserIds;

    /**
     * 用户数据范围组织信息
     */
    @ChineseDescription("用户数据范围组织信息")
    private Set<Long> dataScopeOrganizationIds;

    /**
     * 可用资源集合
     */
    @ChineseDescription("可用资源集合")
    private Set<String> resourceUrls;

    /**
     * 用户拥有的按钮编码集合
     */
    @ChineseDescription("用户拥有的按钮编码集合")
    private Set<String> buttonCodes;

    /**
     * 登录的时间
     */
    @ChineseDescription("登录的时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 用户的token，当返回用户会话信息时候回带token
     */
    @ChineseDescription("用户的token，当返回用户会话信息时候回带token")
    private String token;

    /**
     * 其他信息，Dict为Map的拓展
     */
    @ChineseDescription("其他信息，Dict为Map的拓展")
    private Dict otherInfos;

    /**
     * 用户的ws-url
     */
    @ChineseDescription("用户的ws-url")
    private String wsUrl;

    /**
     * 头像url
     */
    @ChineseDescription("用户头像url")
    private String avatarUrl;

    /**
     * 当前用户语种的标识，例如：chinese，english
     * <p>
     * 这个值是根据字典获取，字典类型编码 languages
     * <p>
     * 默认语种是中文
     */
    @ChineseDescription("当前用户语种的标识")
    private String tranLanguageCode = RuleConstants.CHINESE_TRAN_LANGUAGE_CODE;

    /**
     * 租户的编码
     */
    @ChineseDescription("租户的编码")
    private String tenantCode;

    /**
     * 当前登录用户是否是C端用户（默认不是C端用户）
     */
    @ChineseDescription("是否是C端用户")
    private Boolean customerFlag = false;

    public String getWsUrl() {
        if (ObjectUtil.isEmpty(this.wsUrl)) {
            return "";
        }

        Map<String, String> params = new HashMap<>(1);
        params.put("token", this.token);
        return StrUtil.format(this.wsUrl, params);
    }

}
