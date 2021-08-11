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
package cn.stylefeng.roses.kernel.system.api;

import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuButtonDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleMenuDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleResourceDTO;

import java.util.List;
import java.util.Set;

/**
 * 角色服务对外部模块的接口
 *
 * @author fengshuonan
 * @date 2020/11/5 19:17
 */
public interface RoleServiceApi {

    /**
     * 获取角色，通过传递角色id列表
     *
     * @param roleIds 角色id列表
     * @return 角色信息列表
     * @author fengshuonan
     * @date 2020/11/21 9:17
     */
    List<SysRoleDTO> getRolesByIds(List<Long> roleIds);

    /**
     * 获取角色对应的组织机构范围集合
     *
     * @param roleIds 角色id集合
     * @return 组织机构id集合
     * @author fengshuonan
     * @date 2020/11/21 9:56
     */
    List<Long> getRoleDataScopes(List<Long> roleIds);

    /**
     * 获取某些角色对应的菜单id集合
     *
     * @param roleIds 角色id集合
     * @return 菜单id集合
     * @author fengshuonan
     * @date 2020/11/22 23:00
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取角色的资源code集合
     *
     * @param roleIdList 角色id集合
     * @return 资源code集合
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    Set<String> getRoleResourceCodeList(List<Long> roleIdList);

    /**
     * 获取角色的资源code集合
     *
     * @param roleIdList 角色id集合
     * @return 资源code集合
     * @author majianguo
     * @date 2020/11/5 上午11:17
     */
    List<SysRoleResourceDTO> getRoleResourceList(List<Long> roleIdList);

    /**
     * 获取角色对应的按钮编码集合
     *
     * @param roleIdList 角色id集合
     * @return 角色拥有的按钮编码集合
     * @author fengshuonan
     * @date 2021/1/9 11:08
     */
    Set<String> getRoleButtonCodes(List<Long> roleIdList);

    /**
     * 获取角色拥有的菜单
     *
     * @param roleIdList 角色集合
     * @author majianguo
     * @date 2021/1/9 17:33
     */
    List<SysRoleMenuDTO> getRoleMenuList(List<Long> roleIdList);

    /**
     * 获取角色拥有的菜单按钮
     *
     * @param roleIdList 角色集合
     * @author majianguo
     * @date 2021/1/9 17:33
     */
    List<SysRoleMenuButtonDTO> getRoleMenuButtonList(List<Long> roleIdList);

}
