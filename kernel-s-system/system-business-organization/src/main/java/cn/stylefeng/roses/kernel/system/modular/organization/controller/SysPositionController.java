package cn.stylefeng.roses.kernel.system.modular.organization.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysPositionService;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysPositionRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统职位控制器
 *
 * @author fengshuonan
 * @date 2020/11/18 21:56
 */
@RestController
@ApiResource(name = "系统职位管理")
public class SysPositionController {

    @Resource
    private SysPositionService syspositionService;

    /**
     * 添加系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "添加系统职位", path = "/sysPosition/add")
    public ResponseData add(@RequestBody @Validated(SysPositionRequest.add.class) SysPositionRequest sysPositionRequest) {
        syspositionService.add(sysPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "编辑系统职位", path = "/sysPosition/edit")
    public ResponseData edit(@RequestBody @Validated(SysPositionRequest.edit.class) SysPositionRequest sysPositionRequest) {
        syspositionService.edit(sysPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "删除系统职位", path = "/sysPosition/delete")
    public ResponseData delete(@RequestBody @Validated(SysPositionRequest.delete.class) SysPositionRequest sysPositionRequest) {
        syspositionService.delete(sysPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 更新职位状态
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "更新职位状态", path = "/sysPosition/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) SysPositionRequest sysPositionRequest) {
        syspositionService.updateStatus(sysPositionRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看详情系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "查看详情系统职位", path = "/sysPosition/detail")
    public ResponseData detail(@Validated(SysPositionRequest.detail.class) SysPositionRequest sysPositionRequest) {
        return new SuccessResponseData(syspositionService.detail(sysPositionRequest));
    }

    /**
     * 分页查询系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "分页查询系统职位", path = "/sysPosition/page")
    public ResponseData page(SysPositionRequest sysPositionRequest) {
        return new SuccessResponseData(syspositionService.page(sysPositionRequest));
    }

    /**
     * 获取全部系统职位
     *
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "获取全部系统职位", path = "/sysPosition/list")
    public ResponseData list(SysPositionRequest sysPositionRequest) {
        return new SuccessResponseData(syspositionService.list(sysPositionRequest));
    }

}
