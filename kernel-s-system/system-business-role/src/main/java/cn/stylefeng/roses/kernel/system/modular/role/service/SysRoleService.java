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
package cn.stylefeng.roses.kernel.system.modular.role.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.api.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色service接口
 *
 * @author majianguo
 * @date 2020/11/5 上午11:12
 */
public interface SysRoleService extends IService<SysRole>, RoleServiceApi {

    /**
     * 添加系统角色
     *
     * @param sysRoleRequest 添加参数
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    void add(SysRoleRequest sysRoleRequest);

    /**
     * 删除系统角色
     *
     * @param sysRoleRequest 删除参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void del(SysRoleRequest sysRoleRequest);

    /**
     * 编辑系统角色
     *
     * @param sysRoleRequest 编辑参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void edit(SysRoleRequest sysRoleRequest);

    /**
     * 查看系统角色
     *
     * @param sysRoleRequest 查看参数
     * @return 系统角色
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    SysRoleDTO detail(SysRoleRequest sysRoleRequest);

    /**
     * 查询系统角色
     *
     * @param sysRoleRequest 查询参数
     * @return 查询分页结果
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest);

    /**
     * 根据角色名模糊搜索系统角色列表
     *
     * @param sysRoleRequest 查询参数
     * @return 增强版hashMap，格式：[{"id":456, "name":"总经理(zjl)"}]
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    List<SimpleDict> findList(SysRoleRequest sysRoleRequest);

    /**
     * 授权菜单和按钮
     *
     * @author majianguo
     * @date 2021/1/9 18:13
     */
    void grantMenuAndButton(SysRoleRequest sysRoleMenuButtonRequest);

    /**
     * 角色绑定菜单，新界面用
     *
     * @author fengshuonan
     * @date 2021/8/11 10:02
     */
    void grantMenu(SysRoleRequest sysRoleMenuButtonRequest);

    /**
     * 角色绑定按钮，新界面用
     *
     * @author fengshuonan
     * @date 2021/8/11 10:02
     */
    void grantButton(SysRoleRequest sysRoleMenuButtonRequest);

    /**
     * 授权数据范围（组织机构）
     *
     * @param sysRoleRequest 授权参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void grantDataScope(SysRoleRequest sysRoleRequest);

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    List<SimpleDict> dropDown();

    /***
     * 查询角色拥有数据
     *
     * @param sysRoleRequest 查询参数
     * @return 数据范围id集合
     * @author majianguo
     * @date 2020/11/5 上午11:15
     */
    List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest);

}
