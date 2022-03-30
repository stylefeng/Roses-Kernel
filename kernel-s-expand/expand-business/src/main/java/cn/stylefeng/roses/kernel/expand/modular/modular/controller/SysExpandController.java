package cn.stylefeng.roses.kernel.expand.modular.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpand;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandService;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务拓展控制器
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@RestController
@ApiResource(name = "业务拓展")
public class SysExpandController {

    @Resource
    private SysExpandService sysExpandService;

    /**
     * 添加
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "添加", path = "/sysExpand/add")
    public ResponseData<SysExpand> add(@RequestBody @Validated(SysExpandRequest.add.class) SysExpandRequest sysExpandRequest) {
        sysExpandService.add(sysExpandRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "删除", path = "/sysExpand/delete")
    public ResponseData<?> delete(@RequestBody @Validated(SysExpandRequest.delete.class) SysExpandRequest sysExpandRequest) {
        sysExpandService.del(sysExpandRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "编辑", path = "/sysExpand/edit")
    public ResponseData<?> edit(@RequestBody @Validated(SysExpandRequest.edit.class) SysExpandRequest sysExpandRequest) {
        sysExpandService.edit(sysExpandRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改业务状态
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "修改业务状态", path = "/sysExpand/updateStatus")
    public ResponseData<?> updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) SysExpandRequest sysExpandRequest) {
        sysExpandService.updateStatus(sysExpandRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "查看详情", path = "/sysExpand/detail")
    public ResponseData<SysExpand> detail(@Validated(SysExpandRequest.detail.class) SysExpandRequest sysExpandRequest) {
        return new SuccessResponseData<>(sysExpandService.detail(sysExpandRequest));
    }

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "获取列表", path = "/sysExpand/list")
    public ResponseData<List<SysExpand>> list(SysExpandRequest sysExpandRequest) {
        return new SuccessResponseData<>(sysExpandService.findList(sysExpandRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "分页查询", path = "/sysExpand/page")
    public ResponseData<PageResult<SysExpand>> page(SysExpandRequest sysExpandRequest) {
        return new SuccessResponseData<>(sysExpandService.findPage(sysExpandRequest));
    }

}
