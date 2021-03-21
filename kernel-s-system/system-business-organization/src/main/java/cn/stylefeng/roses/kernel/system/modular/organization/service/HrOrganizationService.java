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
package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.api.OrganizationServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.OrganizationTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统组织机构服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
public interface HrOrganizationService extends IService<HrOrganization>, OrganizationServiceApi {

    /**
     * 添加系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void add(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 删除系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void del(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 编辑系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void edit(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 修改组织机构状态
     *
     * @param hrOrganizationRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/18 22:38
     */
    void updateStatus(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查看详情系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    HrOrganization detail(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 分页查询系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    PageResult<HrOrganization> findPage(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询所有系统组织机构
     *
     * @param hrOrganizationRequest 组织机构请求参数
     * @return 组织机构详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    List<HrOrganization> findList(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取全部系统组织机构树（用于新增，编辑组织机构时选择上级节点，用于获取用户管理界面左侧组织机构树）
     *
     * @param hrOrganizationRequest 查询参数
     * @return 系统组织机构树
     * @author chenjinlong
     * @date 2020/11/6 13:41
     */
    List<OrganizationTreeNode> organizationTree(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取ztree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（layui版本）
     *
     * @param hrOrganizationRequest 请求参数
     * @param buildTree             是否构建成树结构的节点，true-带树结构，false-不带
     * @return ztree形式的组织机构树
     * @author fengshuonan
     * @date 2021/1/9 18:40
     */
    List<ZTreeNode> orgZTree(HrOrganizationRequest hrOrganizationRequest, boolean buildTree);

    /**
     * 查询所有参数组织架构id集合的所有层级的父id，包含父级的父级等
     *
     * @param organizationIds 组织架构id集合
     * @return 被查询参数id集合的所有层级父级id，包含他们本身
     * @author fengshuonan
     * @date 2020/11/6 14:24
     */
    Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds);

}
