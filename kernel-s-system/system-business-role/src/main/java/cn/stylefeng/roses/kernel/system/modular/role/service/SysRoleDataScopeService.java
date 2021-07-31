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

import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleDataScopeRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRoleDataScope;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色数据范围service接口
 *
 * @author majianguo
 * @date 2020/11/5 上午11:21
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 授权数据
     *
     * @param sysRoleRequest 授权参数
     * @author majianguo
     * @date 2020/11/5 上午11:20
     */
    void grantDataScope(SysRoleRequest sysRoleRequest);

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author majianguo
     * @date 2020/11/5 上午11:21
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

    /**
     * 新增
     *
     * @param sysRoleDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 删除
     *
     * @param sysRoleDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 根据角色id 删除角色数据范围
     *
     * @param roleId 角色id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delByRoleId(Long roleId);


    /**
     * 修改
     *
     * @param sysRoleDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 查询-详情
     *
     * @param sysRoleDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysRoleDataScope detail(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 查询-列表
     *
     * @param sysRoleDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysRoleDataScope> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest);


}
