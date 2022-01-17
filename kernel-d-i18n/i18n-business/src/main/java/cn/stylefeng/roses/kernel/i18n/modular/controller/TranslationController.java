/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.i18n.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
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
    public ResponseData<?> add(@RequestBody @Validated(TranslationRequest.add.class) TranslationRequest translationRequest) {
        this.translationService.add(translationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑多语言翻译记录
     *
     * @author fengshuonan
     * @date 2021/1/24 19:17
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/edit")
    public ResponseData<?> edit(@RequestBody @Validated(BaseRequest.edit.class) TranslationRequest translationRequest) {
        this.translationService.edit(translationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除多语言配置
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "新增多语言配置", path = "/i18n/delete")
    public ResponseData<?> delete(@RequestBody @Validated(BaseRequest.delete.class) TranslationRequest translationRequest) {
        this.translationService.del(translationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除某个语种
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @PostResource(name = "删除某个语种", path = "/i18n/deleteTranLanguage")
    public ResponseData<?> deleteTranLanguage(@RequestBody @Validated(TranslationRequest.deleteTranLanguage.class) TranslationRequest translationRequest) {
        this.translationService.deleteTranLanguage(translationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看多语言详情
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/detail")
    public ResponseData<Translation> detail(@Validated(BaseRequest.detail.class) TranslationRequest translationRequest) {
        Translation detail = this.translationService.detail(translationRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 查看多语言配置列表
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "新增多语言配置", path = "/i18n/page")
    public ResponseData<PageResult<Translation>> page(TranslationRequest translationRequest) {
        PageResult<Translation> page = this.translationService.findPage(translationRequest);
        return new SuccessResponseData<>(page);
    }

}


