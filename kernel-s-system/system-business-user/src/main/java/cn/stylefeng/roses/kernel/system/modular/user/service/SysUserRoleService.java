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
package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserRole;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.UserRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户角色service接口
 *
 * @author chenjinlong
 * @date 2021/2/3 15:23
 */
public interface SysUserRoleService extends IService<SysUserRole> {


    /**
     * 新增
     *
     * @param userRoleRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(UserRoleRequest userRoleRequest);

    /**
     * 删除
     *
     * @param userRoleRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(UserRoleRequest userRoleRequest);

    /**
     * 根据用户id删除角色
     *
     * @param userId 用户id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     *
     * @param userRoleRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(UserRoleRequest userRoleRequest);

    /**
     * 查询-详情
     *
     * @param userRoleRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysUserRole detail(UserRoleRequest userRoleRequest);

    /**
     * 查询-列表
     *
     * @param userRoleRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysUserRole> findList(UserRoleRequest userRoleRequest);

    /**
     * 根据userId查询列表
     *
     * @param userId 用户id
     * @return
     * @author chenjinlong
     * @date 2021/2/3 15:06
     */
    List<SysUserRole> findListByUserId(Long userId);

    /**
     * 根据userId查询角色集合
     *
     * @param userId 用户id
     * @return 用户角色集合
     * @author chenjinlong
     * @date 2021/2/3 15:09
     */
    List<Long> findRoleIdsByUserId(Long userId);

    /**
     * 角色分配
     *
     * @param sysUserRequest 请求参数
     * @return
     * @author chenjinlong
     * @date 2021/2/3 15:16
     */
    void assignRoles(SysUserRequest sysUserRequest);

}
