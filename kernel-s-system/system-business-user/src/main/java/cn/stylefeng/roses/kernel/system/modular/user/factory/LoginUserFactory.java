package cn.stylefeng.roses.kernel.system.modular.user.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.file.FileInfoApi;
import cn.stylefeng.roses.kernel.file.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.rule.enums.SexEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.*;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.pojo.organization.DataScopeResponse;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeResponse;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 组装当前登录用户的信息
 *
 * @author fengshuonan
 * @date 2020/11/26 22:25
 */
public class LoginUserFactory {

    /**
     * 创建登录用户
     *
     * @author fengshuonan
     * @date 2020/11/26 22:32
     */
    public static LoginUser createLoginUser(SysUser sysUser) {

        // 一些需要调用的别的模块的接口
        FileOperatorApi fileOperatorApi = SpringUtil.getBean(FileOperatorApi.class);
        FileInfoApi fileInfoApi = SpringUtil.getBean(FileInfoApi.class);
        RoleServiceApi roleServiceApi = SpringUtil.getBean(RoleServiceApi.class);
        ResourceServiceApi resourceServiceApi = SpringUtil.getBean(ResourceServiceApi.class);
        SysEmployeeApi sysEmployeeApi = SpringUtil.getBean(SysEmployeeApi.class);
        DataScopeApi dataScopeApi = SpringUtil.getBean(DataScopeApi.class);
        AppServiceApi appServiceApi = SpringUtil.getBean(AppServiceApi.class);

        // 填充基本信息
        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(sysUser, loginUser);
        Long userId = sysUser.getId();

        // 填充用户主组织机构id
        SysEmployeeResponse userMainEmployee = sysEmployeeApi.getUserMainEmployee(userId);
        loginUser.setOrganizationId(userMainEmployee.getOrganizationId());

        // 获取头像文件详细信息
        SysFileInfoResponse fileInfoWithoutContent = fileInfoApi.getFileInfoWithoutContent(sysUser.getAvatar());
        // 填充用户头像url
        String fileAuthUrl = fileOperatorApi.getFileAuthUrl(
                fileInfoWithoutContent.getFileBucket(),
                fileInfoWithoutContent.getFileObjectName(),
                FileConfigExpander.getDefaultFileTimeoutSeconds() * 1000);
        loginUser.setAvatar(fileAuthUrl);

        // 填充管理员转化
        loginUser.setSuperAdmin(YesOrNotEnum.Y.getCode().equals(sysUser.getSuperAdminFlag()));

        // 填充性别枚举转换
        loginUser.setSex(SexEnum.codeToEnum(sysUser.getSex()).getMessage());

        // 填充手机号
        loginUser.setMobilePhone(sysUser.getPhone());

        // 填充用户的数据范围
        DataScopeResponse dataScopeResponse = dataScopeApi.getDataScope(userId);
        loginUser.setDataScopeTypes(dataScopeResponse.getDataScopeTypeEnums());
        loginUser.setOrganizationIdDataScope(dataScopeResponse.getOrganizationIds());
        loginUser.setUserIdDataScope(dataScopeResponse.getUserIds());

        // 填充用户角色信息
        Set<SimpleDict> roleList = getRoleList(dataScopeResponse.getSysRoleResponses());
        loginUser.setRoles(roleList);

        // 通过用户角色，获取角色所有的资源id
        List<Long> roleIds = roleList.stream().map(SimpleDict::getId).collect(Collectors.toList());
        List<String> resourceIdsList = roleServiceApi.getRoleResourceList(roleIds);
        // 根据资源id获取资源的所有详情信息
        List<ResourceDefinition> resourceDefinitions = resourceServiceApi.getResourceListByIds(resourceIdsList);
        Set<String> resourceUrls = resourceDefinitions.stream().map(ResourceDefinition::getUrl).collect(Collectors.toSet());
        // 填充用户的urls
        loginUser.setResourceUrls(resourceUrls);

        // 用户包含的app信息
        Set<String> appCodes = resourceDefinitions.stream().map(ResourceDefinition::getAppCode).collect(Collectors.toSet());
        Set<SimpleDict> appsByAppCodes = appServiceApi.getAppsByAppCodes(appCodes);
        // 填充应用信息
        loginUser.setApps(appsByAppCodes);

        return loginUser;
    }

    /**
     * 组装角色信息
     *
     * @author fengshuonan
     * @date 2020/11/29 19:12
     */
    private static Set<SimpleDict> getRoleList(List<SysRoleResponse> sysRoleResponses) {

        if (sysRoleResponses == null || sysRoleResponses.isEmpty()) {
            return new HashSet<>();
        }

        Set<SimpleDict> simpleRoles = new HashSet<>();
        for (SysRoleResponse sysRoleResponse : sysRoleResponses) {
            SimpleDict simpleRole = new SimpleDict();
            simpleRole.setId(sysRoleResponse.getId());
            simpleRole.setName(sysRoleResponse.getName());
            simpleRole.setCode(sysRoleResponse.getCode());
            simpleRoles.add(simpleRole);
        }

        return simpleRoles;
    }

}
