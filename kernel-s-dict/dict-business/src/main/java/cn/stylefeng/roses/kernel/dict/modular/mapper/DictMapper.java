package cn.stylefeng.roses.kernel.dict.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.roses.kernel.dict.modular.entity.Dict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典详情的管理
 *
 * @author fengshuonan
 * @date 2020/10/29 17:20
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 获取分页字典列表
     *
     * @param dictRequest 查询条件对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:21
     */
    Dict findDetail(@Param("dictRequest") DictRequest dictRequest);

    /**
     * 获取分页字典列表
     *
     * @param page        分页参数
     * @param dictRequest 查询条件对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 17:21
     */
    List<Dict> findList(Page<Dict> page, @Param("dictRequest") DictRequest dictRequest);

}
