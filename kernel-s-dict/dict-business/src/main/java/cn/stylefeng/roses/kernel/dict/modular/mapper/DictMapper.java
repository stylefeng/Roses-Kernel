package cn.stylefeng.roses.kernel.dict.modular.mapper;

import cn.stylefeng.roses.kernel.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典详情的管理
 *
 * @author fengshuonan
 * @date 2020/10/29 17:20
 */
public interface DictMapper extends BaseMapper<SysDict> {

    /**
     * 获取分页字典列表
     *
     * @param dictId 字典id
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:21
     */
    SysDict detail(@Param("dictId") Long dictId);

    /**
     * 获取分页字典列表
     *
     * @param page        分页参数
     * @param dictRequest 查询条件对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:21
     */
    List<SysDict> findPage(@Param("page") Page<SysDict> page, @Param("dictRequest") DictRequest dictRequest);

    /**
     * 获取字典下拉列表，用在新增和修改字典，选择字典的父级
     * <p>
     * 当传参数dictId是，查询结果会排除参数dictId字典的所有子级和dictId字典本身
     *
     * @param dictId 字典id
     * @author fengshuonan
     * @date 2020/12/11 16:35
     */
    List<SysDict> getDictListExcludeSub(@Param("dictId") Long dictId);

    /**
     * 修改下级字典的pids
     *
     * @param parentIdsUpdateRequest 查询条件对象
     * @author fengshuonan
     * @date 2020/12/11 17:21
     */
    void updateSubPids(@Param("paramCondition") ParentIdsUpdateRequest parentIdsUpdateRequest);

    /**
     * 删除自己和下级
     *
     * @param dictId 字典id
     * @author fengshuonan
     * @date 2020/12/11 16:35
     */
    void deleteSub(@Param("dictId") Long dictId);

}
