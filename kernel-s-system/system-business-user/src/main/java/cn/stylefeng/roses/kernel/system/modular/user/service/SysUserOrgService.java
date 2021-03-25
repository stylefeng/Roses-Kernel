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

import cn.stylefeng.roses.kernel.system.api.UserOrgServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.UserOrgRequest;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户组织机构关联信息
 *
 * @author fengshuonan
 * @date 2020/12/19 22:17
 */
public interface SysUserOrgService extends IService<SysUserOrg>, UserOrgServiceApi {

    /**
     * 新增
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(UserOrgRequest userOrgResponse);

    /**
     * 新增
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(Long userId, Long orgId);

    /**
     * 新增
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @param positionId 职位id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(Long userId, Long orgId, Long positionId);

    /**
     * 删除
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(UserOrgRequest userOrgResponse);

    /**
     * 删除
     *
     * @param userId 用户id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(UserOrgRequest userOrgResponse);

    /**
     * 修改
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(Long userId, Long orgId);

    /**
     * 修改
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @param positionId 职位id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(Long userId, Long orgId, Long positionId);

    /**
     * 详情
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysUserOrg detail(UserOrgRequest userOrgResponse);

    /**
     * 查询-列表
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysUserOrg> findList(UserOrgRequest userOrgResponse);


}
