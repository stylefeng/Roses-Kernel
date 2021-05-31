package cn.stylefeng.roses.kernel.system.modular.resource.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiResourceRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResourceField;
import cn.stylefeng.roses.kernel.system.modular.resource.service.ApiResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 接口信息控制器
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
@RestController
@ApiResource(name = "接口信息")
public class ApiResourceController {

    @Resource
    private ApiResourceService apiResourceService;

    /**
     * 添加
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "添加", path = "/apiResource/add")
    public ResponseData add(@RequestBody @Validated(ApiResourceRequest.add.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.add(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "删除", path = "/apiResource/delete")
    public ResponseData delete(@RequestBody @Validated(ApiResourceRequest.delete.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.del(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "编辑", path = "/apiResource/edit")
    public ResponseData edit(@RequestBody @Validated(ApiResourceRequest.edit.class) ApiResourceRequest apiResourceRequest) {
        apiResourceService.edit(apiResourceRequest);
        return new SuccessResponseData();
    }

    /**
     * 重置
     *
     * @return {@link cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData}
     * @author majianguo
     * @date 2021/5/27 下午3:33
     **/
    @PostResource(name = "编辑", path = "/apiResource/reset", responseClass = cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource.class)
    public ResponseData reset(@RequestBody @Validated(ApiResourceRequest.reset.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.reset(apiResourceRequest));
    }

    /**
     * 请求记录
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @PostResource(name = "请求记录", path = "/apiResource/record", responseClass = cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource.class)
    public ResponseData record(@RequestBody @Validated(ApiResourceRequest.record.class) ApiResourceRequest apiResourceRequest) {
        cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource apiResource = apiResourceService.record(apiResourceRequest);
        return new SuccessResponseData(apiResource);
    }

    /**
     * 查看详情
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "查看详情", path = "/apiResource/detail", responseClass = cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource.class)
    public ResponseData detail(@Validated(ApiResourceRequest.detail.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.detail(apiResourceRequest));
    }

    /**
     * 获取列表
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "获取列表", path = "/apiResource/list", responseClass = cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource.class)
    public ResponseData list(ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.findList(apiResourceRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "分页查询", path = "/apiResource/page", responseClass = cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource.class)
    public ResponseData page(ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.findPage(apiResourceRequest));
    }

    /**
     * 查询该资源所有字段
     *
     * @author majianguo
     * @date 2021/05/21 15:03
     */
    @GetResource(name = "查询该资源所有字段", path = "/apiResource/allField", responseClass = ApiResourceField.class)
    public ResponseData allField(@Validated(ApiResourceRequest.allField.class) ApiResourceRequest apiResourceRequest) {
        return new SuccessResponseData(apiResourceService.allField(apiResourceRequest));
    }

}