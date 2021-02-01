package cn.stylefeng.roses.kernel.timer.modular.controller;

import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.timer.modular.param.SysTimersParam;
import cn.stylefeng.roses.kernel.timer.modular.service.SysTimersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务控制器
 *
 * @author fengshuonan
 * @date 2020/10/27 14:30
 */
@RestController
@ApiResource(name = "定时任务管理")
public class SysTimersController {

    @Resource
    private SysTimersService sysTimersService;

    /**
     * 添加定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @PostResource(name = "添加定时任务", path = "/sysTimers/add")
    public ResponseData add(@RequestBody @Validated(SysTimersParam.add.class) SysTimersParam sysTimersParam) {
        sysTimersService.add(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @PostResource(name = "编辑定时任务", path = "/sysTimers/edit")
    public ResponseData edit(@RequestBody @Validated(SysTimersParam.edit.class) SysTimersParam sysTimersParam) {
        sysTimersService.edit(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 删除定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @PostResource(name = "删除定时任务", path = "/sysTimers/delete")
    public ResponseData del(@RequestBody @Validated(SysTimersParam.delete.class) SysTimersParam sysTimersParam) {
        sysTimersService.del(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 启动定时任务
     *
     * @author stylefeng
     * @date 2020/7/1 14:34
     */
    @PostResource(name = "启动定时任务", path = "/sysTimers/start")
    public ResponseData start(@RequestBody @Validated(SysTimersParam.startTimer.class) SysTimersParam sysTimersParam) {
        sysTimersService.start(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 停止定时任务
     *
     * @author stylefeng
     * @date 2020/7/1 14:34
     */
    @PostResource(name = "停止定时任务", path = "/sysTimers/stop")
    public ResponseData stop(@RequestBody @Validated(SysTimersParam.stopTimer.class) SysTimersParam sysTimersParam) {
        sysTimersService.stop(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 查看详情定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @GetResource(name = "查看详情定时任务", path = "/sysTimers/detail")
    public ResponseData detail(@Validated(SysTimersParam.detail.class) SysTimersParam sysTimersParam) {
        return new SuccessResponseData(sysTimersService.detail(sysTimersParam));
    }

    /**
     * 分页查询定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @GetResource(name = "分页查询定时任务", path = "/sysTimers/page")
    public ResponseData page(SysTimersParam sysTimersParam) {
        return new SuccessResponseData(sysTimersService.findPage(sysTimersParam));
    }

    /**
     * 获取全部定时任务
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    @GetResource(name = "获取全部定时任务", path = "/sysTimers/list")
    public ResponseData list(SysTimersParam sysTimersParam) {
        return new SuccessResponseData(sysTimersService.findList(sysTimersParam));
    }

    /**
     * 获取系统的所有任务列表
     *
     * @author stylefeng
     * @date 2020/7/1 14:34
     */
    @PostResource(name = "获取系统的所有任务列表", path = "/sysTimers/getActionClasses")
    public ResponseData getActionClasses() {
        List<String> actionClasses = sysTimersService.getActionClasses();
        return new SuccessResponseData(actionClasses);
    }

}
