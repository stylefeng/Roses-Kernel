package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典详情管理，具体管理某个字典类型下的条目
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
@RestController
@ApiResource(name = "字典详情管理")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 添加字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "添加字典", path = "/dict/addDict", requiredPermission = false)
    public ResponseData addDictType(@RequestBody @Validated(DictRequest.add.class) DictRequest dictRequest) {
        this.dictService.addDict(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "修改字典", path = "/dict/updateDict", requiredPermission = false)
    public ResponseData updateDict(@RequestBody @Validated(DictRequest.edit.class) DictRequest dictRequest) {
        this.dictService.updateDict(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 更新字典状态
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "更新字典状态", path = "/dict/updateDictStatus", requiredPermission = false)
    public ResponseData updateDictStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) DictRequest dictRequest) {
        this.dictService.updateDictStatus(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "删除字典", path = "/dict/deleteDict", requiredPermission = false)
    public ResponseData deleteDict(@RequestBody @Validated(DictRequest.delete.class) DictRequest dictRequest) {
        this.dictService.deleteDict(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典详情
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典详情", path = "/dict/getDictDetail", requiredPermission = false)
    public ResponseData getDictDetail(@RequestBody @Validated(DictRequest.detail.class) DictRequest dictRequest) {
        SysDict detail = this.dictService.findDetail(dictRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "获取字典列表", path = "/dict/getDictList", requiredPermission = false)
    public ResponseData getDictList(@RequestBody DictRequest dictRequest) {
        List<SysDict> dictList = this.dictService.findList(dictRequest);
        return new SuccessResponseData(dictList);
    }

    /**
     * 获取字典列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "获取字典列表", path = "/dict/getDictListPage", requiredPermission = false)
    public ResponseData getDictListPage(@RequestBody DictRequest dictRequest) {
        PageResult<SysDict> page = this.dictService.findPageList(dictRequest);
        return new SuccessResponseData(page);
    }

    /**
     * 获取树形字典列表
     *
     * @author fengshuonan
     * @date 2020/10/29 16:36
     */
    @PostResource(name = "获取树形字典列表", path = "/dict/getDictTreeList", requiredPermission = false)
    public ResponseData getDictTreeList(@RequestBody @Validated(DictRequest.treeList.class) DictRequest dictRequest) {
        List<TreeDictInfo> treeDictList = this.dictService.getTreeDictList(dictRequest);
        return new SuccessResponseData(treeDictList);
    }

    /**
     * code校验
     *
     * @author fengshuonan
     * @date 2020/10/29 16:36
     */
    @PostResource(name = "code校验", path = "/dict/validateCodeAvailable", requiredPermission = false)
    public ResponseData validateCodeAvailable(@RequestBody @Validated(DictRequest.treeList.class) DictRequest dictRequest) {
        boolean flag = this.dictService.validateCodeAvailable(dictRequest);
        return new SuccessResponseData(flag);
    }

}
