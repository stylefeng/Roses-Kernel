package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 添加字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午12:36
     */
    @PostResource(name = "添加字典类型", path = "/dictType/addDictType", requiredPermission = false)
    public ResponseData addDictType(@RequestBody @Validated(DictTypeRequest.add.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.addDictType(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型", path = "/dictType/updateDictType", requiredPermission = false)
    public ResponseData updateDictType(@RequestBody @Validated(DictTypeRequest.edit.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.updateDictType(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型状态
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午12:36
     */
    @PostResource(name = "修改字典类型状态", path = "/dictType/updateStatus", requiredPermission = false)
    public ResponseData updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.updateDictTypeStatus(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午12:36
     */
    @PostResource(name = "删除字典类型", path = "/dictType/deleteDictType", requiredPermission = false)
    public ResponseData deleteDictType(@RequestBody @Validated(DictTypeRequest.delete.class) DictTypeRequest dictTypeRequest) {
        this.dictTypeService.deleteDictType(dictTypeRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典类型列表
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @PostResource(name = "获取字典类型列表", path = "/dictType/getDictTypeList", requiredPermission = false)
    public ResponseData getDictTypeList(@RequestBody DictTypeRequest dictTypeRequest) {
        List<SysDictType> sysDictTypeList = dictTypeService.getDictTypeList(dictTypeRequest);
        return new SuccessResponseData(sysDictTypeList);
    }

    /**
     * 获取字典类型列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/30 21:46
     */
    @PostResource(name = "获取字典类型列表(分页)", path = "/dictType/getDictTypePageList", requiredPermission = false)
    public ResponseData getDictTypePageList(@RequestBody DictTypeRequest dictTypeRequest) {
        Page<SysDictType> page = PageFactory.defaultPage();
        PageResult<SysDictType> dictTypePageList = dictTypeService.getDictTypePageList(page, dictTypeRequest);
        return new SuccessResponseData(dictTypePageList);
    }

    /**
     * code校验
     *
     * @author fengshuonan
     * @date 2020/10/30 21:53
     */
    @PostResource(name = "code校验", path = "/dictType/validateCodeAvailable", requiredPermission = false)
    public ResponseData validateCodeAvailable(@RequestBody DictTypeRequest dictTypeRequest) {
        boolean flag = this.dictTypeService.validateCodeAvailable(dictTypeRequest);
        return new SuccessResponseData(flag);
    }

}
