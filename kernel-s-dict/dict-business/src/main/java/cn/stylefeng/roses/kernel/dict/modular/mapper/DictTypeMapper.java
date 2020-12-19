package cn.stylefeng.roses.kernel.dict.modular.mapper;

import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型表 Mapper 接口
 *
 * @author fengshuonan
 * @date 2020/10/30 21:04
 */
public interface DictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 获取字典类型列表
     *
     * @param page            分页对象
     * @param dictTypeRequest 字典类型对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:41
     */
    List<SysDictType> findList(Page<SysDictType> page, @Param("dictTypeRequest") DictTypeRequest dictTypeRequest);

}
