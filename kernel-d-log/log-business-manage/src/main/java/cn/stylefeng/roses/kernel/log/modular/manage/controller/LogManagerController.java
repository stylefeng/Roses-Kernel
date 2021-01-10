package cn.stylefeng.roses.kernel.log.modular.manage.controller;

import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerParam;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
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
     * 查询日志列表
     *
     * @author luojie
     * @date 2020/11/3 12:58
     */
    @GetResource(name = "查询日志列表", path = "/logManager/list")
    public ResponseData list(@RequestBody LogManagerParam logManagerParam) {
        return new SuccessResponseData(logManagerApi.queryLogList(logManagerParam));
    }

    /**
     * 查询日志
     *
     * @author tengshuqi
     * @date 2021/1/8 17:36
     */
    @GetResource(name = "查询日志列表", path = "/logManager/page")
    public ResponseData page(LogManagerParam logManagerParam) {
        return new SuccessResponseData(logManagerApi.queryLogListPage(logManagerParam));
    }

    /**
     * 删除日志
     *
     * @author luojie
     * @date 2020/11/3 13:47
     */
    @PostResource(name = "删除日志", path = "/logManager/delete")
    public ResponseData delete(@RequestBody @Validated(LogManagerParam.delete.class) LogManagerParam logManagerParam) {
        logManagerApi.deleteLogs(logManagerParam);
        return new SuccessResponseData();
    }

}
