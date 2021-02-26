package cn.stylefeng.roses.kernel.system.modular.notice.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.notice.SysNoticeRequest;
import cn.stylefeng.roses.kernel.system.modular.notice.service.SysNoticeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 通知管理控制器
 *
 * @author liuhanqing
 * @date 2021/1/8 19:47
 */
@RestController
@ApiResource(name = "通知管理")
public class SysNoticeController {

    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 添加通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:44
     */
    @PostResource(name = "添加通知管理", path = "/sysNotice/add")
    public ResponseData add(@RequestBody @Validated(SysNoticeRequest.add.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.add(sysNoticeParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:54
     */
    @PostResource(name = "编辑通知管理", path = "/sysNotice/edit")
    public ResponseData edit(@RequestBody @Validated(SysNoticeRequest.edit.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.edit(sysNoticeParam);
        return new SuccessResponseData();
    }

    /**
     * 删除通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:54
     */
    @PostResource(name = "删除通知管理", path = "/sysNotice/delete")
    public ResponseData delete(@RequestBody @Validated(SysNoticeRequest.delete.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.del(sysNoticeParam);
        return new SuccessResponseData();
    }

    /**
     * 查看通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 9:49
     */
    @GetResource(name = "查看通知管理", path = "/sysNotice/detail")
    public ResponseData detail(@Validated(SysNoticeRequest.detail.class) SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData(sysNoticeService.detail(sysNoticeParam));
    }

    /**
     * 查询通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 21:23
     */
    @GetResource(name = "查询通知管理", path = "/sysNotice/page")
    public ResponseData page(SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData(sysNoticeService.findPage(sysNoticeParam));
    }

    /**
     * 通知管理列表
     *
     * @author liuhanqing
     * @date 2021/1/9 14:55
     */
    @GetResource(name = "通知管理列表", path = "/sysNotice/list")
    public ResponseData list(SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData(sysNoticeService.findList(sysNoticeParam));
    }

}
