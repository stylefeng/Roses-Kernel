package cn.stylefeng.roses.kernel.i18n.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 多语言接口控制器
 *
 * @author fengshuonan
 * @date 2021/1/24 19:18
 */
@RestController
@ApiResource(name = "多语言接口控制器")
public class TranslationController {

    @Resource
    private TranslationService translationService;

    /**
     * 新增多语言翻译记录
     *
     * @author fengshuonan
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/add")
    public ResponseData add(@RequestBody @Validated(TranslationRequest.add.class) TranslationRequest translationRequest) {
        this.translationService.add(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑多语言翻译记录
     *
     * @author fengshuonan
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/edit")
    public ResponseData edit(@RequestBody @Validated(BaseRequest.edit.class) TranslationRequest translationRequest) {
        this.translationService.edit(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除多语言配置
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/delete")
    public ResponseData delete(@RequestBody @Validated(BaseRequest.delete.class) TranslationRequest translationRequest) {
        this.translationService.del(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看多语言详情
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/detail")
    public ResponseData detail(@Validated(BaseRequest.detail.class) TranslationRequest translationRequest) {
        Translation detail = this.translationService.detail(translationRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 查看多语言配置列表
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/page")
    public ResponseData page(TranslationRequest translationRequest) {
        PageResult<Translation> page = this.translationService.getPage(translationRequest);
        return new SuccessResponseData(page);
    }

}


