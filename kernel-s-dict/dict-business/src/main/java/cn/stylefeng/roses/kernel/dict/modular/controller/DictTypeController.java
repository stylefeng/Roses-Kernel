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
package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
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
import java.util.List;

/**
 * 字典类型管理
 *
 * @author fengshuonan
 * @date 2020/10/30 21:46
 */
@RestController
@ApiResource(name = "字典类型管理")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    /**
     * 添加字典类型
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "添加字典类型", path = "/dictType/add", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(DictTypeRequest.add.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.add(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除字典类型
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "删除字典类型", path = "/dictType/delete", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(DictTypeRequest.delete.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.del(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改字典类型
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型", path = "/dictType/edit", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(DictTypeRequest.edit.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.edit(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 修改字典类型状态
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型状态", path = "/dictType/updateStatus", requiredPermission = false)
    @BusinessLog
    public ResponseData<?> updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.editStatus(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取字典类型详情", path = "/dictType/detail", requiredPermission = false)
    public ResponseData<SysDictType> detail(@Validated(BaseRequest.detail.class) DictTypeRequest dictTypeRequest) {
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取字典类型列表
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表", path = "/dictType/list", requiredPermission = false)
    public ResponseData<List<SysDictType>> list(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(dictTypeService.findList(dictTypeRequest));
    }

    /**
     * 获取字典类型列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表(分页)", path = "/dictType/page", requiredPermission = false)
    public ResponseData<PageResult<SysDictType>> page(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(dictTypeService.findPage(dictTypeRequest));
    }

    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取系统配置字典类型详情", path = "/dictType/getConfigDictTypeDetail", requiredPermission = false)
    public ResponseData<SysDictType> getConfigDictTypeDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取语种字典类型型详情", path = "/dictType/getTranslationDetail", requiredPermission = false)
    public ResponseData<SysDictType> getTranslationDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData<>(detail);
    }

}
