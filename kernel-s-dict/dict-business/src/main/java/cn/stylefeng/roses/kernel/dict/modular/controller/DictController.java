package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
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
 * 字典详情管理，具体管理某个字典类型下的条目
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
@RestController
@ApiResource(name = "字典详情管理")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 添加字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "添加字典", path = "/dict/add", requiredPermission = false)
    public ResponseData add(@RequestBody @Validated(DictRequest.add.class) DictRequest dictRequest) {
        this.dictService.add(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "删除字典", path = "/dict/delete", requiredPermission = false)
    public ResponseData delete(@RequestBody @Validated(DictRequest.delete.class) DictRequest dictRequest) {
        this.dictService.del(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 修改字典条目
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @PostResource(name = "修改字典", path = "/dict/edit", requiredPermission = false)
    public ResponseData edit(@RequestBody @Validated(DictRequest.edit.class) DictRequest dictRequest) {
        this.dictService.edit(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典详情
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典详情", path = "/dict/detail", requiredPermission = false)
    public ResponseData detail(@Validated(BaseRequest.detail.class) DictRequest dictRequest) {
        SysDict detail = this.dictService.detail(dictRequest);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/list", requiredPermission = false)
    public ResponseData list(DictRequest dictRequest) {
        return new SuccessResponseData(this.dictService.findList(dictRequest));
    }

    /**
     * 获取字典列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/page", requiredPermission = false)
    public ResponseData page(DictRequest dictRequest) {
        return new SuccessResponseData(this.dictService.findPage(dictRequest));
    }


    /**
     * 获取树形字典列表（antdv在用）
     *
     * @author fengshuonan
     * @date 2020/10/29 16:36
     */
    @GetResource(name = "获取树形字典列表", path = "/dict/getDictTreeList", requiredPermission = false)
    public ResponseData getDictTreeList(@Validated(DictRequest.treeList.class) DictRequest dictRequest) {
        List<TreeDictInfo> treeDictList = this.dictService.getTreeDictList(dictRequest);
        return new SuccessResponseData(treeDictList);
    }

    /**
     * 获取系统配置分组字典列表(分页)（给系统配置界面，左侧获取配置的分类用）
     *
     * @author chenjinlong
     * @date 2021/1/25 11:47
     */
    @GetResource(name = "获取系统配置分组字典列表", path = "/dict/getConfigGroupPage", requiredPermission = false)
    public ResponseData getConfigGroupPage(DictRequest dictRequest) {
        dictRequest.setDictTypeCode(DictConstants.CONFIG_GROUP_DICT_TYPE_CODE);
        PageResult<SysDict> page = this.dictService.findPage(dictRequest);
        return new SuccessResponseData(page);
    }

    /**
     * 获取多语言字典列表(分页)（给多语言界面，左侧获取多语言的分类用）
     *
     * @author chenjinlong
     * @date 2021/1/25 11:47
     */
    @GetResource(name = "获取多语言字典列表", path = "/dict/getLanguagesPage", requiredPermission = false)
    public ResponseData getLanguagesPage(DictRequest dictRequest) {
        dictRequest.setDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        PageResult<SysDict> page = this.dictService.findPage(dictRequest);
        return new SuccessResponseData(page);
    }

}
