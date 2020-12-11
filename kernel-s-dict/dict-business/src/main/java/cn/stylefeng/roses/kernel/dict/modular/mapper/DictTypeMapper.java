package cn.stylefeng.roses.kernel.dict.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.roses.kernel.dict.modular.entity.DictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型表 Mapper 接口
 *
 * @author fengshuonan
 * @date 2020/10/30 21:04
 */
public interface DictTypeMapper extends BaseMapper<DictType> {

    /**
     * 获取字典类型列表
     *
     * @param page            分页对象
     * @param dictTypeRequest 字典类型对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:41
     */
    List<DictType> findList(Page<DictType> page, @Param("dictTypeRequest") DictTypeRequest dictTypeRequest);

}
