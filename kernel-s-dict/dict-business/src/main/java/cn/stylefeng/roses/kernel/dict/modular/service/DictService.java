package cn.stylefeng.roses.kernel.dict.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.DictApi;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.rule.pojo.ztree.ZTreeNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典详情管理
 *
 * @author fengshuonan
 * @date 2020/10/29 17:43
 */
public interface DictService extends IService<SysDict>, DictApi {

    /**
     * 新增字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void addDict(DictRequest dictRequest);

    /**
     * 修改字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void updateDict(DictRequest dictRequest);

    /**
     * 更新字典状态
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 18:47
     */
    void updateDictStatus(DictRequest dictRequest);

    /**
     * 删除字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void deleteDict(DictRequest dictRequest);

    /**
     * 查询字典详情
     *
     * @param dictId 字典id
     * @return 字典的详情
     * @author fengshuonan
     * @date 2020/10/30 16:15
     */
    SysDict findDetail(Long dictId);

    /**
     * 获取字典列表
     *
     * @param dictRequest 字典对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    List<SysDict> findList(DictRequest dictRequest);

    /**
     * 获取字典列表（带分页）
     *
     * @param dictRequest 查询条件
     * @return 带分页的列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    PageResult<SysDict> findPageList(DictRequest dictRequest);

    /**
     * 获取树形字典列表
     *
     * @param dictRequest 查询条件
     * @return 字典信息列表
     * @author fengshuonan
     * @date 2020/10/29 18:50
     */
    List<TreeDictInfo> getTreeDictList(DictRequest dictRequest);

    /**
     * 字典的code校验重复
     *
     * @param dictRequest 查询条件
     * @return true-code没有重复，false-code存在重复
     * @author fengshuonan
     * @date 2020/10/29 18:50
     */
    boolean validateCodeAvailable(DictRequest dictRequest);

    /**
     * 获取字典下拉列表，用在新增和修改字典，选择字典的父级
     * <p>
     * 当传参数dictId是，查询结果会排除参数dictId字典的所有子级和dictId字典本身
     *
     * @author fengshuonan
     * @date 2020/12/11 16:35
     */
    List<SysDict> getDictListExcludeSub(Long dictId);

    /**
     * 获取字典的ztree列表
     *
     * @author huangyao
     * @date 2021/1/12 14:27
     */
    List<ZTreeNode> dictZTree(DictRequest dictRequest);

    /**
     * 分页查询
     *
     * @param dictRequest 参数
     * @author chenjinlong
     * @date 2021/1/13 10:57
     */
    PageResult<SysDict> page(DictRequest dictRequest);

}
