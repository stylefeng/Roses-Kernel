package cn.stylefeng.roses.kernel.system.modular.organization.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysOrganizationService;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysOrganizationRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统组织机构控制器
 *
 * @author fengshuonan
 * @date 2020/11/18 21:55
 */
@RestController
@ApiResource(name = "系统组织机构管理")
public class SysOrganizationController {

    @Resource
    private SysOrganizationService sysorganizationService;

    /**
     * 添加系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "添加系统组织机构", path = "/sysOrganization/add")
    public ResponseData add(@RequestBody @Validated(SysOrganizationRequest.add.class) SysOrganizationRequest sysOrganizationRequest) {
        sysorganizationService.add(sysOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "编辑系统组织机构", path = "/sysOrganization/edit")
    public ResponseData edit(@RequestBody @Validated(SysOrganizationRequest.edit.class) SysOrganizationRequest sysOrganizationRequest) {
        sysorganizationService.edit(sysOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "删除系统组织机构", path = "/sysOrganization/delete")
    public ResponseData delete(@RequestBody @Validated(SysOrganizationRequest.delete.class) SysOrganizationRequest sysOrganizationRequest) {
        sysorganizationService.delete(sysOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改组织机构状态
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @PostResource(name = "修改组织机构状态", path = "/sysOrganization/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(SysOrganizationRequest.updateStatus.class) SysOrganizationRequest sysOrganizationRequest) {
        sysorganizationService.updateStatus(sysOrganizationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "查看详情系统组织机构", path = "/sysOrganization/detail")
    public ResponseData detail(@Validated(SysOrganizationRequest.detail.class) SysOrganizationRequest sysOrganizationRequest) {
        return new SuccessResponseData(sysorganizationService.detail(sysOrganizationRequest));
    }

    /**
     * 分页查询系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "分页查询系统组织机构", path = "/sysOrganization/page")
    public ResponseData page(SysOrganizationRequest sysOrganizationRequest) {
        return new SuccessResponseData(sysorganizationService.page(sysOrganizationRequest));
    }

    /**
     * 获取全部系统组织机构
     *
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    @GetResource(name = "获取全部系统组织机构", path = "/sysOrganization/list")
    public ResponseData list(SysOrganizationRequest sysOrganizationRequest) {
        return new SuccessResponseData(sysorganizationService.list(sysOrganizationRequest));
    }


}
