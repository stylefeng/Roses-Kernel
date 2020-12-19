package cn.stylefeng.roses.kernel.resource.modular.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.modular.entity.SysResource;
import cn.stylefeng.roses.kernel.resource.modular.pojo.ResourceTreeNode;
import cn.stylefeng.roses.kernel.resource.modular.service.SysResourceService;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.resource.request.ResourceRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 获取资源列表
     *
     * @author fengshuonan
     * @date 2020/11/24 19:47
     */
    @GetResource(name = "获取资源列表", path = "/resource/pageList")
    public ResponseData pageList(ResourceRequest resourceRequest) {
        Page<SysResource> result = this.sysResourceService.getResourceList(resourceRequest);
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
        List<SysResource> menuResourceList = this.sysResourceService.getMenuResourceList(resourceRequest);
        return new SuccessResponseData(menuResourceList);
    }

    /**
     * 获取资源树列表，用于接口文档页面
     *
     * @author fengshuonan
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取资源树列表，用于接口文档页面", path = "/resource/getTree", requiredLogin = false, requiredPermission = false)
    public ResponseData getTree() {
        List<ResourceTreeNode> resourceTree = sysResourceService.getResourceTree();
        return new SuccessResponseData(resourceTree);
    }

    /**
     * 获取接口详情
     *
     * @author fengshuonan
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取接口详情", path = "/resource/getDetail", requiredLogin = false, requiredPermission = false)
    public ResponseData getResourceDetail(@Validated(BaseRequest.detail.class) ResourceRequest resourceRequest) {
        ResourceDefinition resourceDetail = sysResourceService.getResourceDetail(resourceRequest);
        return new SuccessResponseData(resourceDetail);
    }

}
