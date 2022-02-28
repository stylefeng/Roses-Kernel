package cn.stylefeng.roses.kernel.system.modular.theme.controller;

import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统主题模板属性关系控制器
 *
 * @author xixiaowei
 * @date 2021/12/24 10:55
 */
@RestController
@ApiResource(name = "系统主题模板属性关系管理")
public class SysThemeTemplateRelController {

    @Resource
    private SysThemeTemplateRelService sysThemeTemplateRelService;

    /**
     * 增加系统主题模板属性关系
     *
     * @author xixiaowei
     * @date 2021/12/24 11:17
     */
    @PostResource(name = "增加系统主题模板属性关系", path = "/sysThemeTemplateRel/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysThemeTemplateRelRequest.add.class) SysThemeTemplateRelRequest sysThemeTemplateParam) {
        sysThemeTemplateRelService.add(sysThemeTemplateParam);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统主题模板属性关系
     *
     * @author xixiaowei
     * @date 2021/12/24 11:23
     */
    @PostResource(name = "删除系统主题模板属性关系", path = "/sysThemeTemplateRel/del")
    @BusinessLog
    public ResponseData<?> del(@RequestBody SysThemeTemplateRelRequest sysThemeTemplateRelParam) {
        sysThemeTemplateRelService.del(sysThemeTemplateRelParam);
        return new SuccessResponseData<>();
    }
}
