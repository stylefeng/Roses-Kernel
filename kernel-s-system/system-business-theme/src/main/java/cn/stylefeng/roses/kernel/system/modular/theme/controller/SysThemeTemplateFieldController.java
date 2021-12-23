package cn.stylefeng.roses.kernel.system.modular.theme.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统主题模板属性控制器
 *
 * @author xixiaowei
 * @date 2021/12/17 10:28
 */
@RestController
@ApiResource(name = "系统主题模板属性管理")
public class SysThemeTemplateFieldController {

    @Resource
    private SysThemeTemplateFieldService sysThemeTemplateFieldService;

    /**
     * 增加系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:22
     */
    @PostResource(name = "增加系统主题模板属性", path = "/sysThemeTemplateField/add")
    public ResponseData add(@RequestBody @Validated(SysThemeTemplateFieldRequest.add.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
        sysThemeTemplateFieldService.add(sysThemeTemplateFieldParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:25
     */
    @PostResource(name = "删除系统主题模板属性", path = "/sysThemeTemplateField/del")
    public ResponseData del(@RequestBody @Validated(SysThemeTemplateFieldRequest.delete.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
        sysThemeTemplateFieldService.del(sysThemeTemplateFieldParam);
        return new SuccessResponseData();
    }

    /**
     * 修改系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:38
     */
    @PostResource(name = "修改系统模板属性", path = "/sysThemeTemplateField/edit")
    public ResponseData edit(@RequestBody @Validated(SysThemeTemplateFieldRequest.edit.class) SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
        sysThemeTemplateFieldService.edit(sysThemeTemplateFieldParam);
        return new SuccessResponseData();
    }

    /**
     * 查询系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:49
     */
    @GetResource(name = "查询系统主题模板属性", path = "/sysThemeTemplateField/detail")
    public ResponseData detail(SysThemeTemplateFieldRequest sysThemeTemplateFieldParam) {
        return new SuccessResponseData(sysThemeTemplateFieldService.detail(sysThemeTemplateFieldParam));
    }
}
