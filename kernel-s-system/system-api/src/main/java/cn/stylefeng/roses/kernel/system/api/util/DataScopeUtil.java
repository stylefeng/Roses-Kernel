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
package cn.stylefeng.roses.kernel.system.api.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.auth.api.exception.AuthException;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.system.api.exception.enums.organization.DataScopeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;

import java.util.Set;

/**
 * 数据范围相关的工具类，快捷方法
 *
 * @author fengshuonan
 * @date 2020/11/5 15:31
 */
public class DataScopeUtil {

    /**
     * 一句话获取当前登录用户的数据范围信息
     *
     * @author fengshuonan
     * @date 2020/11/5 16:07
     */
    public static String getDataScopeTip() {

        StringBuilder tips = new StringBuilder();

        // 获取当前登录用户
        LoginUser loginUser;
        try {
            loginUser = LoginContext.me().getLoginUser();
        } catch (AuthException e) {
            return tips.append("空：获取不到当前用户").toString();
        }

        Set<DataScopeTypeEnum> dataScopeTypes = loginUser.getDataScopeTypeEnums();
        if (dataScopeTypes == null) {
            return tips.append("空：数据范围为空").toString();
        }

        tips.append("用户数据范围类型：");
        if (!dataScopeTypes.isEmpty()) {
            for (DataScopeTypeEnum dataScopeType : dataScopeTypes) {
                tips.append(dataScopeType.getMessage()).append(",");
            }
        }

        Set<Long> userDataScope = loginUser.getDataScopeUserIds();
        tips.append("用户userId数据范围：");
        if (userDataScope != null && !userDataScope.isEmpty()) {
            for (Long id : userDataScope) {
                tips.append(id).append(",");
            }
        }

        Set<Long> organizationDataScope = loginUser.getDataScopeOrganizationIds();
        tips.append("用户organizationId数据范围：");
        if (organizationDataScope != null && !organizationDataScope.isEmpty()) {
            for (Long id : organizationDataScope) {
                tips.append(id).append(",");
            }
        }

        return tips.toString();
    }

    /**
     * 判断当前登录用户是否有某个组织架构id的数据范围
     *
     * @param organizationId 被校验的组织机构id
     * @author fengshuonan
     * @date 2020/11/5 15:31
     */
    public static boolean validateDataScopeByOrganizationId(Long organizationId) {

        // 获取当前登录用户
        LoginUser loginUser;
        try {
            loginUser = LoginContext.me().getLoginUser();
        } catch (AuthException e) {
            return false;
        }

        // 超级管理员包含所有数据范围
        if (loginUser.getSuperAdmin()) {
            return true;
        }

        // 获取用户的数据范围类型，user数据范围，组织机构数据范围
        Set<DataScopeTypeEnum> dataScopeTypes = loginUser.getDataScopeTypeEnums();
        Set<Long> organizationDataScope = loginUser.getDataScopeOrganizationIds();

        // 如果数据范围类型为空，则返回没权限
        if (dataScopeTypes == null || dataScopeTypes.isEmpty()) {
            return false;
        }

        // 如果数据范围类型里有全部数据，则返回成功
        if (dataScopeTypes.contains(DataScopeTypeEnum.ALL)) {
            return true;
        }

        // 如果仅有本人的权限
        if (dataScopeTypes.size() == 1) {
            DataScopeTypeEnum dataScopeTypeEnum = CollectionUtil.newArrayList(dataScopeTypes).get(0);
            if (dataScopeTypeEnum.equals(DataScopeTypeEnum.SELF)) {
                return false;
            }
        }

        // 如果部门范围为空，返回失败
        if (organizationDataScope == null || organizationDataScope.isEmpty()) {
            return false;
        }

        // 剩下的情况，就判断数据范围里有没有包含 organizationId
        return organizationDataScope.contains(organizationId);
    }

    /**
     * 快速校验用户是否有该组织机构的数据范围，如果没有就抛出异常直接
     *
     * @param organizationId 被校验的组织机构id
     * @author fengshuonan
     * @date 2020/11/5 15:31
     */
    public static void quickValidateDataScope(Long organizationId) {
        boolean validateResult = validateDataScopeByOrganizationId(organizationId);
        if (!validateResult) {
            throw new SystemModularException(DataScopeExceptionEnum.DATA_SCOPE_ERROR, DataScopeUtil.getDataScopeTip());
        }
    }

}
