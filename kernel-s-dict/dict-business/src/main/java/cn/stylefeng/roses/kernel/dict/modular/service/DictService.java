package cn.stylefeng.roses.kernel.dict.modular.service;

import cn.stylefeng.roses.kernel.dict.modular.entity.Dict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;

import java.util.List;

/**
 * 字典详情管理
 *
 * @author fengshuonan
 * @date 2020/10/29 17:43
 */
public interface DictService extends IService<Dict> {

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
     * @param dictRequest 字典对象
     * @return 字典的详情
     * @author fengshuonan
     * @date 2020/10/30 16:15
     */
    Dict findDetail(DictRequest dictRequest);

    /**
     * 获取字典列表
     *
     * @param dictRequest 字典对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    List<Dict> findList(DictRequest dictRequest);

    /**
     * 获取字典列表（带分页）
     *
     * @param dictRequest 查询条件
     * @return 带分页的列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    PageResult<Dict> findPageList(DictRequest dictRequest);

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

}
