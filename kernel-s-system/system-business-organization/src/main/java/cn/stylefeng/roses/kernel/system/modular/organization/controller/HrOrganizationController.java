package cn.stylefeng.roses.kernel.system.modular.organization.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.ztree.ZTreeNode;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrOrganizationService;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrOrganizationRequest;
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
    public ResponseData add(@RequestBody @Validated(HrOrganizationRequest.add.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.add(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "编辑系统组织机构", path = "/hrOrganization/edit")
    public ResponseData edit(@RequestBody @Validated(HrOrganizationRequest.edit.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.edit(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "删除系统组织机构", path = "/hrOrganization/delete")
    public ResponseData delete(@RequestBody @Validated(HrOrganizationRequest.delete.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.delete(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改组织机构状态
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "修改组织机构状态", path = "/hrOrganization/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(HrOrganizationRequest.updateStatus.class) HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.updateStatus(hrOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "查看详情系统组织机构", path = "/hrOrganization/detail")
    public ResponseData detail(@Validated(HrOrganizationRequest.detail.class) HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.detail(hrOrganizationRequest));
    }

    /**
     * 分页查询系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "分页查询系统组织机构", path = "/hrOrganization/page")
    public ResponseData page(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.page(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "获取全部系统组织机构", path = "/hrOrganization/list")
    public ResponseData list(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.list(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构树，用于新增，编辑时选择上级节点
     *
     * @author chenjinlong
     * @date 2021/01/05 15:55
     */
    @GetResource(name = "获取全部系统组织机构树", path = "/hrOrganization/tree")
    public ResponseData tree(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.tree(hrOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构树，用于新增，编辑时选择上级节点
     *
     * @author chenjinlong
     * @date 2021/01/05 15:55
     */
    @GetResource(name = "获取全部系统组织机构树", path = "/hrOrganization/treeLayui")
    public ResponseData treeLayui(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData(hrOrganizationService.treeLayui(hrOrganizationRequest));
    }

    /**
     * 获取zTree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（layui版本）
     *
     * @author fengshuonan
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "获取zTree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（layui版本）", path = "/hrOrganization/zTree")
    public List<ZTreeNode> layuiSelectParentMenuTreeList(@Validated(HrOrganizationRequest.orgZTree.class) HrOrganizationRequest hrOrganizationRequest) {
        return hrOrganizationService.orgZTree(hrOrganizationRequest, false);
    }

    /**
     * 获取zTree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（antd vue版本）
     *
     * @author fengshuonan
     * @date 2021/1/9 18:37
     */
    @GetResource(name = "获取zTree形式的组织机构树（用于角色配置数据范围类型，并且数据范围类型是指定组织机构时）（antd vue版本）", path = "/hrOrganization/treeWithChildren")
    public ResponseData treeWithChildren(@Validated(HrOrganizationRequest.orgZTree.class) HrOrganizationRequest hrOrganizationRequest) {
        List<ZTreeNode> zTreeNodes = hrOrganizationService.orgZTree(hrOrganizationRequest, true);
        return new SuccessResponseData(zTreeNodes);
    }


}
