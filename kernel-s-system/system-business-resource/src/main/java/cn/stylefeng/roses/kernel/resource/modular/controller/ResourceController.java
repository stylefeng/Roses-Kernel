package cn.stylefeng.roses.kernel.resource.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.resource.modular.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.resource.modular.service.SysResourceService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.resource.ResourceRequest;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源管理控制器
 *
 * @author fengshuonan
 * @date 2020/11/24 19:47
 */
@RestController
@ApiResource(name = "资源管理")
public class ResourceController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获取资源列表
     *
     * @author fengshuonan
     * @date 2020/11/24 19:47
     */
    @GetResource(name = "获取资源列表", path = "/resource/pageList")
    public ResponseData pageList(ResourceRequest resourceRequest) {
        PageResult<SysResource> result = this.sysResourceService.findPage(resourceRequest);
        return new SuccessResponseData(result);
    }

    /**
     * 获取资源下拉列表（获取菜单资源）
     *
     * @author fengshuonan
     * @date 2020/11/24 19:51
     */
    @GetResource(name = "获取资源下拉列表", path = "/resource/getMenuResourceList")
    public ResponseData getMenuResourceList(ResourceRequest resourceRequest) {
        List<SysResource> menuResourceList = this.sysResourceService.findList(resourceRequest);
        return new SuccessResponseData(menuResourceList);
    }

    /**
     * 获取资源平级树列表，用于分配接口权限（适用于layui版本）
     *
     * @author majianguo
     * @date 2021/1/9 15:07
     */
    @GetResource(name = "获取资源平级树列表，用于分配接口权限", path = "/resource/getLateralTree")
    public List<ResourceTreeNode> getLateralTree(SysRoleRequest sysRoleRequest) {
        return sysResourceService.getResourceTree(sysRoleRequest.getRoleId(), true);
    }

    /**
     * 获取资源树列表，用于分配接口权限（适用于antd vue版本）
     *
     * @author majianguo
     * @date 2021/1/9 15:07
     */
    @GetResource(name = "获取资源树列表，用于分配接口权限", path = "/resource/getLateralTreeChildren")
    public ResponseData getLateralTreeChildren(SysRoleRequest sysRoleRequest) {
        List<ResourceTreeNode> resourceLateralTree = sysResourceService.getResourceTree(sysRoleRequest.getRoleId(), false);
        return new SuccessResponseData(resourceLateralTree);
    }

}
