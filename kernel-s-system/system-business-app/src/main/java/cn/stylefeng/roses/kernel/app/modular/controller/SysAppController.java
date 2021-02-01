package cn.stylefeng.roses.kernel.app.modular.controller;

import cn.stylefeng.roses.kernel.app.modular.service.SysAppService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.system.pojo.app.request.SysAppRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统应用控制器
 *
 * @author fengshuonan
 * @date 2020/3/20 21:25
 */
@RestController
@ApiResource(name = "系统应用")
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 添加系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:44
     */
    @PostResource(name = "添加系统应用", path = "/sysApp/add")
    public ResponseData add(@RequestBody @Validated(SysAppRequest.add.class) SysAppRequest sysAppParam) {
        sysAppService.add(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "删除系统应用", path = "/sysApp/delete")
    public ResponseData delete(@RequestBody @Validated(SysAppRequest.delete.class) SysAppRequest sysAppParam) {
        sysAppService.del(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统应用
     *
     * @author fengshuonan
     * @date 2020/3/25 14:54
     */
    @PostResource(name = "编辑系统应用", path = "/sysApp/edit")
    public ResponseData edit(@RequestBody @Validated(SysAppRequest.edit.class) SysAppRequest sysAppParam) {
        sysAppService.edit(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统应用
     *
     * @author fengshuonan
     * @date 2020/3/26 9:49
     */
    @GetResource(name = "查看系统应用", path = "/sysApp/detail")
    public ResponseData detail(@Validated(SysAppRequest.detail.class) SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @author fengshuonan
     * @date 2020/4/19 14:55
     */
    @GetResource(name = "系统应用列表", path = "/sysApp/list")
    public ResponseData list(SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.findList(sysAppParam));
    }

    /**
     * 查询系统应用
     *
     * @author fengshuonan
     * @date 2020/3/20 21:23
     */
    @GetResource(name = "查询系统应用", path = "/sysApp/page")
    public ResponseData page(SysAppRequest sysAppParam) {
        return new SuccessResponseData(sysAppService.findPage(sysAppParam));
    }

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "设为默认应用", path = "/sysApp/updateActiveFlag")
    public ResponseData setAsDefault(@RequestBody @Validated(SysAppRequest.updateActiveFlag.class) SysAppRequest sysAppParam) {
        sysAppService.updateActiveFlag(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 修改应用状态
     *
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    @PostResource(name = "修改应用状态", path = "/sysApp/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(SysAppRequest.updateStatus.class) SysAppRequest sysAppParam) {
        sysAppService.updateStatus(sysAppParam);
        return new SuccessResponseData();
    }

}
