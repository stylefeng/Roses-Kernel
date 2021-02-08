package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
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
    @PostResource(name = "添加字典类型", path = "/dictType/addDictType", requiredPermission = false)
    public ResponseData addDictType(@RequestBody @Validated(DictTypeRequest.add.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.add(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除字典类型
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "删除字典类型", path = "/dictType/deleteDictType", requiredPermission = false)
    public ResponseData deleteDictType(@RequestBody @Validated(DictTypeRequest.delete.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.del(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型", path = "/dictType/updateDictType", requiredPermission = false)
    public ResponseData updateDictType(@RequestBody @Validated(DictTypeRequest.edit.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.edit(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型状态
     *
     * @author fengshuonan
     * @date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型状态", path = "/dictType/updateStatus", requiredPermission = false)
    public ResponseData updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.editStatus(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取字典类型详情", path = "/dictType/getDictDetail", requiredPermission = false)
    public ResponseData getDictDetail(@RequestBody @Validated(BaseRequest.detail.class) DictTypeRequest dictTypeRequest) {
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典类型列表
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表", path = "/dictType/getDictTypeList", requiredPermission = false)
    public ResponseData getDictTypeList(DictTypeRequest dictTypeRequest) {
        List<SysDictType> sysDictTypeList = dictTypeService.findList(dictTypeRequest);
        return new SuccessResponseData(sysDictTypeList);
    }

    /**
     * 获取字典类型列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @GetResource(name = "获取字典类型列表(分页)", path = "/dictType/getDictTypePageList", requiredPermission = false)
    public ResponseData getDictTypePageList(DictTypeRequest dictTypeRequest) {
        PageResult<SysDictType> dictTypePageList = dictTypeService.findPage(dictTypeRequest);
        return new SuccessResponseData(dictTypePageList);
    }

    /**
     * code校验
     *
     * @author fengshuonan
     * @date 2020/10/30 21:53
     */
    @GetResource(name = "code校验", path = "/dictType/validateCodeAvailable", requiredPermission = false)
    public ResponseData validateCodeAvailable(@Validated(DictTypeRequest.validateCode.class) DictTypeRequest dictTypeRequest) {
        boolean flag = this.dictTypeService.validateCodeAvailable(dictTypeRequest);
        return new SuccessResponseData(flag);
    }


    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取系统配置字典类型详情", path = "/dictType/getConfigDictTypeDetail", requiredPermission = false)
    public ResponseData getConfigDictTypeDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典类型详情
     *
     * @author fengshuonan
     * @date 2021/1/13 11:25
     */
    @GetResource(name = "获取语种字典类型型详情", path = "/dictType/getTranslationDetail", requiredPermission = false)
    public ResponseData getTranslationDetail(DictTypeRequest dictTypeRequest) {
        dictTypeRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        SysDictType detail = this.dictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData(detail);
    }

}
