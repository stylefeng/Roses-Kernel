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
package cn.stylefeng.roses.kernel.system.modular.organization.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.tree.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.enums.OrgTypeEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrOrganizationRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.OrganizationTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrOrganization;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrOrganizationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统组织机构控制器
 *
 * @author fengshuonan
 * @date 2020/11/18 21:55
 */
@RestController
@ApiResource(name = "系统组织机构管理")
public class HrOrganizationController {

    @Resource
    private HrOrganizationService hrOrganizationService;

    /**
     * 添加系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "添加系统组织机构", path = "/hrOrganization/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(HrOrganizationRequest.add.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.add(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "删除系统组织机构", path = "/hrOrganization/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(HrOrganizationRequest.delete.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.del(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "编辑系统组织机构", path = "/hrOrganization/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(HrOrganizationRequest.edit.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.edit(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改组织机构状态
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "修改组织机构状态", path = "/hrOrganization/updateStatus")
    @BusinessLog
    public ResponseData<?> updateStatus(@RequestBody @Validated(HrOrganizationRequest.updateStatus.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.updateStatus(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "查看详情系统组织机构", path = "/hrOrganization/detail")
    public ResponseData<HrOrganization> detail(@Validated(HrOrganizationRequest.detail.class) HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.detail(hrOrganizationRequest));
    }

    /**
     * 分页查询系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "分页查询系统组织机构", path = "/hrOrganization/page")
    public ResponseData<PageResult<HrOrganization>> page(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.findPage(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "获取全部系统组织机构", path = "/hrOrganization/list")
    public ResponseData<List<HrOrganization>> list(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.findList(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构树（用于新增，编辑组织机构时选择上级节点，用于获取用户管理界面左侧组织机构树）
     *
     * @author chenjinlong
     * @date 2021/01/05 15:55
     */
    @GetResource(name = "获取全部系统组织机构树", path = "/hrOrganization/tree")
    public ResponseData<List<OrganizationTreeNode>> organizationTree(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * 获取公司管理组织机构树
     *
     * @author fengshuonan
     * @date 2022/5/21 11:24
     */
    @GetResource(name = "获取公司管理组织机构树", path = "/hrOrganization/companyTree")
    public ResponseData<List<OrganizationTreeNode>> companyTree(HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationRequest.setOrgType(OrgTypeEnum.COMPANY.getCode());
        return new SuccessResponseData<>(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * 获取组织机构树（用于用户绑定数据范围，可以渲染是否选中信息）
     *
     * @author fengshuonan
     * @date 2021/3/19 22:20
     */
    @GetResource(name = "获取组织机构树(用于用户绑定数据范围)", path = "/hrOrganization/userBindOrgScope")
    public ResponseData<List<OrganizationTreeNode>> userBindOrgScope(@Validated(HrOrganizationRequest.userBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.organizationTree(hrOrganizationRequest));
    }

    /**
     * Layui版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）
     *
     * @author fengshuonan
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "Layui版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）", path = "/hrOrganization/roleBindOrgScope")
    public List<ZTreeNode> roleBindOrgScope(@Validated(HrOrganizationRequest.roleBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        return hrOrganizationService.orgZTree(hrOrganizationRequest, false);
    }

    /**
     * AntdVue版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）
     *
     * @author fengshuonan
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "AntdVue版本--获取组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）", path = "/hrOrganization/roleBindOrgScopeAntdv")
    public ResponseData<List<ZTreeNode>> roleBindOrgScopeAntdv(@Validated(HrOrganizationRequest.roleBindOrgScope.class) HrOrganizationRequest hrOrganizationRequest) {
        List<ZTreeNode> zTreeNodes = hrOrganizationService.orgZTree(hrOrganizationRequest, true);
        return new SuccessResponseData<>(zTreeNodes);
    }

}
