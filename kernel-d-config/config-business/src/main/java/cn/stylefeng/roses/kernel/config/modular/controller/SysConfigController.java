package cn.stylefeng.roses.kernel.config.modular.controller;

import cn.stylefeng.roses.kernel.config.modular.param.SysConfigParam;
import cn.stylefeng.roses.kernel.config.modular.service.SysConfigService;
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
 * 参数配置控制器
 *
 * @author stylefeng
 * @date 2020/4/13 22:46
 */
@RestController
@ApiResource(name = "参数配置控制器")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 添加系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "添加系统参数配置", path = "/sysConfig/add")
    public ResponseData add(@RequestBody @Validated(SysConfigParam.add.class) SysConfigParam sysConfigParam) {
        sysConfigService.add(sysConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "编辑系统参数配置", path = "/sysConfig/edit")
    public ResponseData edit(@RequestBody @Validated(SysConfigParam.edit.class) SysConfigParam sysConfigParam) {
        sysConfigService.edit(sysConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:11
     */
    @PostResource(name = "删除系统参数配置", path = "/sysConfig/delete")
    public ResponseData delete(@RequestBody @Validated(SysConfigParam.delete.class) SysConfigParam sysConfigParam) {
        sysConfigService.delete(sysConfigParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:12
     */
    @GetResource(name = "查看系统参数配置", path = "/sysConfig/detail")
    public ResponseData detail(@Validated(SysConfigParam.detail.class) SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.detail(sysConfigParam));
    }


    /**
     * 分页查询配置列表
     *
     * @author fengshuonan
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "分页查询配置列表", path = "/sysConfig/page")
    public ResponseData page(SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.page(sysConfigParam));
    }

    /**
     * 系统参数配置列表
     *
     * @author fengshuonan
     * @date 2020/4/14 11:10
     */
    @GetResource(name = "系统参数配置列表", path = "/sysConfig/list")
    public ResponseData list(SysConfigParam sysConfigParam) {
        return new SuccessResponseData(sysConfigService.list(sysConfigParam));
    }

}


