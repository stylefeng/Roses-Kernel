package cn.stylefeng.roses.kernel.expand.modular.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandData;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandDataRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandDataService;
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
 * 业务拓展-具体数据控制器
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@RestController
@ApiResource(name = "业务拓展-具体数据")
public class SysExpandDataController {

    @Resource
    private SysExpandDataService sysExpandDataService;

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "删除", path = "/sysExpandData/delete")
    public ResponseData<?> delete(@RequestBody @Validated(SysExpandDataRequest.delete.class) SysExpandDataRequest sysExpandDataRequest) {
        sysExpandDataService.del(sysExpandDataRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "查看详情", path = "/sysExpandData/detail")
    public ResponseData<SysExpandData> detail(@Validated(SysExpandDataRequest.detail.class) SysExpandDataRequest sysExpandDataRequest) {
        return new SuccessResponseData<>(sysExpandDataService.detail(sysExpandDataRequest));
    }

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "获取列表", path = "/sysExpandData/list")
    public ResponseData<List<SysExpandData>> list(SysExpandDataRequest sysExpandDataRequest) {
        return new SuccessResponseData<>(sysExpandDataService.findList(sysExpandDataRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "分页查询", path = "/sysExpandData/page")
    public ResponseData<PageResult<SysExpandData>> page(SysExpandDataRequest sysExpandDataRequest) {
        return new SuccessResponseData<>(sysExpandDataService.findPage(sysExpandDataRequest));
    }

}
