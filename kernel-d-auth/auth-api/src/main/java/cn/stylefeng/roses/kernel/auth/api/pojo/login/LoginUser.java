package cn.stylefeng.roses.kernel.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
     * 姓名
     */
    private String realName;

    /**
     * 账号
     */
    private String account;

    /**
     * 公司/组织id
     */
    private Long organizationId;

    /**
     * 头像（图片最终访问的url）
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别（具体SexEnum枚举类）（M-男，F-女）
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 超级管理员标识，true-是超级管理员
     */
    private Boolean superAdmin;

    /**
     * 用户数据范围类型的集合
     */
    private Set<DataScopeTypeEnum> dataScopeTypes;

    /**
     * 用户数据范围（userId的集合）
     * <p>
     * 用户能看哪些用户数据的权限
     */
    private Set<Long> userIdDataScope;

    /**
     * 组织机构数据范围（组织架构id的集合）
     * <p>
     * 用户能看哪些组织机构数据的信息
     */
    private Set<Long> organizationIdDataScope;

    /**
     * 具备应用信息
     */
    private Set<SimpleDict> apps;

    /**
     * 角色信息
     */
    private Set<SimpleDict> roles;

    /**
     * 可用资源集合
     */
    private Set<String> resourceUrls;

    /**
     * 其他信息，Dict为Map的拓展
     */
    private Dict otherInfos;

}
