package cn.stylefeng.roses.kernel.system.modular.title.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.title.SysTitleRequest;
import cn.stylefeng.roses.kernel.system.modular.title.entity.SysTitle;
import cn.stylefeng.roses.kernel.system.modular.title.service.SysTitleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 标题图片配置管理控制器
 *
 * @author xixiaowei
 * @date 2021/12/13 17:29
 */
@RestController
@ApiResource(name = "标题图片配置管理")
public class SysTitleController {

    @Resource
    private SysTitleService sysTitleService;

    /**
     * 添加标题图片配置
     *
     * @author xixiaowei
     * @date 2021/12/14 9:26
     */
    @PostResource(name = "添加标题图片配置", path = "/sysTitle/add")
    public ResponseData add(@RequestBody @Validated(SysTitleRequest.add.class) SysTitleRequest sysTitleParam) {
        sysTitleService.add(sysTitleParam);
        return new SuccessResponseData();
    }

    /**
     * 删除标题图片配置
     *
     * @author xixiaowei
     * @date 2021/12/14 9:28
     */
    @PostResource(name = "删除标题图片配置", path = "/sysTitle/del")
    public ResponseData del(@RequestBody @Validated(SysTitleRequest.delete.class) SysTitleRequest sysTitleParam) {
        sysTitleService.del(sysTitleParam);
        return new SuccessResponseData();
    }

    /**
     * 修改标题图片配置
     *
     * @author xixiaowei
     * @date 2021/12/14 9:53
     */
    @PostResource(name = "修改标题图片配置", path = "/sysTitle/edit")
    public ResponseData edit(@RequestBody @Validated(SysTitleRequest.edit.class) SysTitleRequest sysTitleParam) {
        sysTitleService.edit(sysTitleParam);
        return new SuccessResponseData();
    }

    /**
     * 根据名称查询标题图片配置
     *
     * @author xixiaowei
     * @date 2021/12/14 10:26
     */
    @GetResource(name = "查询标题图片配置", path = "/sysTitle/find", responseClass = SysTitle.class)
    public ResponseData findPage(SysTitleRequest sysTitleParam) {
        return new SuccessResponseData(sysTitleService.findPage(sysTitleParam));
    }

    /**
     * 复制标题
     *
     * @author xixiaowei
     * @date 2021/12/14 11:29
     */
    @PostResource(name = "复制标题", path = "/sysTitle/copy")
    public ResponseData copyTitle(@RequestBody @Validated(SysTitleRequest.add.class) SysTitleRequest sysTitleRequest) {
        sysTitleService.copyTitle(sysTitleRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改启用状态
     *
     * @author xixiaowei
     * @date 2021/12/14 11:43
     */
    @PostResource(name = "修改启用状态", path = "/sysTitle/updateStatus")
    public ResponseData updateStatus(@RequestBody @Validated(SysTitleRequest.edit.class) SysTitleRequest sysTitleRequest) {
        sysTitleService.updateStatus(sysTitleRequest);
        return new SuccessResponseData();
    }
}
