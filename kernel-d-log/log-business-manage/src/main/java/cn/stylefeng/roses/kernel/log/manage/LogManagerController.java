package cn.stylefeng.roses.kernel.log.manage;

import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志管理控制器
 *
 * @author luojie
 * @date 2020/11/3 12:44
 */
@RestController
@ApiResource(name = "日志管理控制器")
public class LogManagerController {

    /**
     * 日志管理api
     */
    @Resource
    private LogManagerApi logManagerApi;

    /**
     * 日志管理service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 查询日志列表
     *
     * @author luojie
     * @date 2020/11/3 12:58
     */
    @GetResource(name = "查询日志列表", path = "/logManager/list")
    public ResponseData list(@RequestBody LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.findList(logManagerRequest));
    }

    /**
     * 查询日志
     *
     * @author tengshuqi
     * @date 2021/1/8 17:36
     */
    @GetResource(name = "查询日志列表", path = "/logManager/page")
    public ResponseData page(LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.findPage(logManagerRequest));
    }

    /**
     * 删除日志
     *
     * @author luojie
     * @date 2020/11/3 13:47
     */
    @PostResource(name = "删除日志", path = "/logManager/delete")
    public ResponseData delete(@RequestBody @Validated(LogManagerRequest.delete.class) LogManagerRequest logManagerRequest) {
        sysLogService.delAll(logManagerRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看日志详情
     *
     * @author TSQ
     * @date 2021/1/11 17:36
     */
    @GetResource(name = "查看日志详情", path = "/logManager/detail")
    public ResponseData detail(@Validated(LogManagerRequest.detail.class) LogManagerRequest logManagerRequest) {
        return new SuccessResponseData(logManagerApi.detail(logManagerRequest));
    }

}
