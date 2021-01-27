package cn.stylefeng.roses.kernel.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
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
