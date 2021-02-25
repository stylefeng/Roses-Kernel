package cn.stylefeng.roses.kernel.dict.modular.service;


import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典类型管理
 *
 * @author fengshuonan
 * @date 2020/10/29 18:54
 */
public interface DictTypeService extends IService<SysDictType> {

    /**
     * 添加字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void add(DictTypeRequest dictTypeRequest);

    /**
     * 删除字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void del(DictTypeRequest dictTypeRequest);

    /**
     * 修改字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void edit(DictTypeRequest dictTypeRequest);

    /**
     * 修改字典状态
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:56
     */
    void editStatus(DictTypeRequest dictTypeRequest);

    /**
     * 查询-详情-按实体对象
     *
     * @param dictTypeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysDictType detail(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表
     *
     * @param dictTypeRequest 字典类型请求
     * @return 字典类型列表
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    List<SysDictType> findList(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表（带分页）
     *
     * @param dictTypeRequest 字典类型请求
     * @return 字典类型列表
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest);

}
