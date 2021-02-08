package cn.stylefeng.roses.kernel.system.modular.user.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.auth.api.prop.LoginUserPropExpander;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.pojo.organization.DataScopeDTO;
import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.pojo.user.SysUserOrgDTO;
import cn.stylefeng.roses.kernel.system.pojo.user.UserLoginInfoDTO;

import java.util.*;

/**
 * 组装当前登录用户的信息
 *
 * @author fengshuonan
 * @date 2020/11/26 22:25
 */
public class UserLoginInfoFactory {

    /**
     * 组装登录用户信息对象
     *
     * @param sysUser                 用户信息
     * @param roleResponseList        角色信息
     * @param dataScopeResponse       数据范围信息
     * @param userOrgInfo             组织机构信息
     * @param resourceUrlsListByCodes 用户的所有资源url
     * @param roleButtonCodes         用户的所拥有的按钮编码
     * @author fengshuonan
     * @date 2020/12/26 17:53
     */
    public static UserLoginInfoDTO userLoginInfoDTO(SysUser sysUser,
                                                    List<SysRoleDTO> roleResponseList,
                                                    DataScopeDTO dataScopeResponse,
                                                    SysUserOrgDTO userOrgInfo,
                                                    Set<String> resourceUrlsListByCodes,
                                                    Set<String> roleButtonCodes) {

        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();

        // 设置用户加密的密码和状态
        userLoginInfoDTO.setUserPasswordHexed(sysUser.getPassword());
        userLoginInfoDTO.setUserStatus(sysUser.getStatusFlag());

        // 创建登录用户对象
        LoginUser loginUser = new LoginUser();

        // 填充用户账号，账号id，管理员类型
        loginUser.setAccount(sysUser.getAccount());
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setSuperAdmin(YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag()));

        // 填充用户基本信息
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        BeanUtil.copyProperties(sysUser, simpleUserInfo);
        loginUser.setSimpleUserInfo(simpleUserInfo);

        // 填充用户角色信息
        if (!roleResponseList.isEmpty()) {
            ArrayList<SimpleRoleInfo> simpleRoleInfos = new ArrayList<>();
            for (SysRoleDTO sysRoleResponse : roleResponseList) {
                SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
                BeanUtil.copyProperties(sysRoleResponse, simpleRoleInfo);
                simpleRoleInfos.add(simpleRoleInfo);
            }
            loginUser.setSimpleRoleInfoList(simpleRoleInfos);
        }

        // 填充用户公司和职务
        if (userOrgInfo != null) {
            loginUser.setOrganizationId(userOrgInfo.getOrgId());
            loginUser.setPositionId(userOrgInfo.getPositionId());
        }

        // 设置用户数据范围
        if (dataScopeResponse != null) {
            loginUser.setDataScopeTypeEnums(dataScopeResponse.getDataScopeTypeEnums());
            loginUser.setDataScopeOrganizationIds(dataScopeResponse.getOrganizationIds());
            loginUser.setDataScopeUserIds(dataScopeResponse.getUserIds());
        }

        // 设置用户拥有的资源
        loginUser.setResourceUrls(resourceUrlsListByCodes);

        // 基于接口拓展用户登录信息
        Map<String, LoginUserPropExpander> beansOfLoginUserExpander = SpringUtil.getBeansOfType(LoginUserPropExpander.class);
        if (beansOfLoginUserExpander != null && beansOfLoginUserExpander.size() > 0) {
            for (Map.Entry<String, LoginUserPropExpander> stringLoginUserPropExpanderEntry : beansOfLoginUserExpander.entrySet()) {
                stringLoginUserPropExpanderEntry.getValue().expandAction(loginUser);
            }
        }

        // 填充用户拥有的按钮编码
        loginUser.setButtonCodes(roleButtonCodes);

        // 设置用户的登录时间
        loginUser.setLoginTime(new Date());

        // 响应dto
        userLoginInfoDTO.setLoginUser(loginUser);
        return userLoginInfoDTO;
    }

}
