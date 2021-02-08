package cn.stylefeng.roses.kernel.dict.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostResource(name = "添加字典", path = "/dict/addDict", requiredPermission = false)
    public ResponseData addDict(@RequestBody @Validated(DictRequest.add.class) DictRequest dictRequest) {
        this.dictService.add(dictRequest);
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
        this.dictService.del(dictRequest);
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
        this.dictService.edit(dictRequest);
        return new SuccessResponseData();
    }

    /**
     * 获取字典详情
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典详情", path = "/dict/getDictDetail", requiredPermission = false)
    public ResponseData getDictDetail(@RequestParam("dictId") Long dictId) {
        SysDict detail = this.dictService.detail(dictId);
        return new SuccessResponseData(detail);
    }

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/getDictList", requiredPermission = false)
    public ResponseData getDictList(DictRequest dictRequest) {
        List<SysDict> sysDictList = this.dictService.findList(dictRequest);
        return new SuccessResponseData(sysDictList);
    }

    /**
     * 获取字典列表(分页)
     *
     * @author fengshuonan
     * @date 2020/10/29 16:35
     */
    @GetResource(name = "获取字典列表", path = "/dict/getDictListPage", requiredPermission = false)
    public ResponseData getDictListPage(DictRequest dictRequest) {
        PageResult<SysDict> page = this.dictService.findPage(dictRequest);
        return new SuccessResponseData(page);
    }

    /**
     * 获取字典下拉列表，用在新增和修改字典，选择字典的父级
     * <p>
     * 当传参数dictId是，查询结果会排除参数dictId字典的所有子级和dictId字典本身
     *
     * @author fengshuonan
     * @date 2020/12/11 16:35
     */
    @GetResource(name = "获取字典列表(排除下级)", path = "/dict/getDictListExcludeSub", requiredPermission = false)
    public ResponseData getDictListExcludeSub(@RequestParam(value = "dictId", required = false) Long dictId) {
        List<SysDict> sysDictList = this.dictService.getDictListExcludeSub(dictId);
        return new SuccessResponseData(sysDictList);
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
     * code校验，校验code是否重复
     *
     * @author fengshuonan
     * @date 2020/10/29 16:36
     */
    @GetResource(name = "code校验", path = "/dict/validateCodeAvailable", requiredPermission = false)
    public ResponseData validateCodeAvailable(@Validated(DictRequest.validateAvailable.class) DictRequest dictRequest) {
        boolean flag = this.dictService.validateCodeAvailable(dictRequest);
        return new SuccessResponseData(flag);
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
