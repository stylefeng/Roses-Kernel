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
package cn.stylefeng.roses.kernel.system.modular.resource.controller;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.LayuiApiResourceTreeNode;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.SysResource;
import cn.stylefeng.roses.kernel.system.modular.resource.service.SysResourceService;
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
    @GetResource(name = "获取接口树列表（用于接口文档页面）", path = "/resource/getTree", requiredLogin = false, responseClass = LayuiApiResourceTreeNode.class)
    public ResponseData getTree(ResourceRequest resourceRequest) {
        List<LayuiApiResourceTreeNode> resourceTree = sysResourceService.getApiResourceTree(resourceRequest);
        return new SuccessResponseData(resourceTree);
    }

    /**
     * 获取接口详情
     *
     * @author fengshuonan
     * @date 2020/12/18 15:50
     */
    @GetResource(name = "获取API详情（用于接口文档页面）", path = "/resource/getDetail", requiredLogin = false, responseClass = ResourceDefinition.class)
    public ResponseData getResourceDetail(@Validated(BaseRequest.detail.class) ResourceRequest resourceRequest) {
        ResourceDefinition resourceDetail = sysResourceService.getApiResourceDetail(resourceRequest);
        return new SuccessResponseData(resourceDetail);
    }

}
