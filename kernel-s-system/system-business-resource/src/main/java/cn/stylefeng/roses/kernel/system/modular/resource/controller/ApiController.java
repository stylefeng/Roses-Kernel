package cn.stylefeng.roses.kernel.system.modular.resource.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.LayuiApiResourceTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ResourceRequest;
import org.springframework.validation.annotation.Validated;
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
@ApiResource(name = "API接口管理")
public class ApiController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获取资源树列表，用于接口文档页面
     *
     * @author fengshuonan
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取接口树列表（用于接口文档页面）", path = "/resource/getTree", requiredLogin = false)
    public ResponseData getTree() {
        List<LayuiApiResourceTreeNode> resourceTree = sysResourceService.getApiResourceTree();
        return new SuccessResponseData(resourceTree);
    }

    /**
     * 获取接口详情
     *
     * @author fengshuonan
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取API详情（用于接口文档页面）", path = "/resource/getDetail", requiredLogin = false)
    public ResponseData getResourceDetail(@Validated(BaseRequest.detail.class) ResourceRequest resourceRequest) {
        ResourceDefinition resourceDetail = sysResourceService.getApiResourceDetail(resourceRequest);
        return new SuccessResponseData(resourceDetail);
    }

}
