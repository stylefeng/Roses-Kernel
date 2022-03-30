package cn.stylefeng.roses.kernel.expand.modular.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandField;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandFieldRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandFieldService;
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
 * 业务拓展-字段信息控制器
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@RestController
@ApiResource(name = "业务拓展-字段信息")
public class SysExpandFieldController {

    @Resource
    private SysExpandFieldService sysExpandFieldService;

    /**
     * 添加
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "添加", path = "/sysExpandField/add")
    public ResponseData<SysExpandField> add(@RequestBody @Validated(SysExpandFieldRequest.add.class) SysExpandFieldRequest sysExpandFieldRequest) {
        sysExpandFieldService.add(sysExpandFieldRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "删除", path = "/sysExpandField/delete")
    public ResponseData<?> delete(@RequestBody @Validated(SysExpandFieldRequest.delete.class) SysExpandFieldRequest sysExpandFieldRequest) {
        sysExpandFieldService.del(sysExpandFieldRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @PostResource(name = "编辑", path = "/sysExpandField/edit")
    public ResponseData<?> edit(@RequestBody @Validated(SysExpandFieldRequest.edit.class) SysExpandFieldRequest sysExpandFieldRequest) {
        sysExpandFieldService.edit(sysExpandFieldRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "查看详情", path = "/sysExpandField/detail")
    public ResponseData<SysExpandField> detail(@Validated(SysExpandFieldRequest.detail.class) SysExpandFieldRequest sysExpandFieldRequest) {
        return new SuccessResponseData<>(sysExpandFieldService.detail(sysExpandFieldRequest));
    }

    /**
     * 获取列表
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "获取列表", path = "/sysExpandField/list")
    public ResponseData<List<SysExpandField>> list(SysExpandFieldRequest sysExpandFieldRequest) {
        return new SuccessResponseData<>(sysExpandFieldService.findList(sysExpandFieldRequest));
    }

    /**
     * 获取列表（带分页）
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    @GetResource(name = "分页查询", path = "/sysExpandField/page")
    public ResponseData<PageResult<SysExpandField>> page(SysExpandFieldRequest sysExpandFieldRequest) {
        return new SuccessResponseData<>(sysExpandFieldService.findPage(sysExpandFieldRequest));
    }

}
