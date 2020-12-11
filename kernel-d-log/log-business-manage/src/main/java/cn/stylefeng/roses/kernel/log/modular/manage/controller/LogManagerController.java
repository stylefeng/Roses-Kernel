package cn.stylefeng.roses.kernel.log.modular.manage.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerParam;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private LogManagerApi logManagerApi;

    /**
     * 查询日志列表
     *
     * @author luojie
     * @date 2020/11/3 12:58
     */
    @PostResource(name = "查询日志列表", path = "/logManager/list")
    public ResponseData list(@RequestBody LogManagerParam logManagerParam) {
        PageResult<LogRecordDTO> logRecordDtoPageResult = logManagerApi.queryLogListPage(logManagerParam);
        return new SuccessResponseData(logRecordDtoPageResult);
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
