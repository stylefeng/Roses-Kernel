package cn.stylefeng.roses.kernel.i18n.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
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
    public ResponseData addItem(TranslationRequest translationRequest) {
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
    public ResponseData editItem(TranslationRequest translationRequest) {
        this.translationService.update(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除多语言配置
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/delete")
    public ResponseData delete(TranslationRequest translationRequest) {
        this.translationService.delete(translationRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看多语言详情
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/detail")
    public ResponseData detail(TranslationRequest translationRequest) {
        Translation detail = this.translationService.findDetail(translationRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 查看多语言配置列表
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/page")
    public ResponseData list(TranslationRequest translationRequest) {
        PageResult<Translation> page = this.translationService.findPage(translationRequest);
        return new SuccessResponseData(page);
    }

}


