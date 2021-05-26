package cn.stylefeng.roses.kernel.system.modular.resource.mapper;

import cn.stylefeng.roses.kernel.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 接口分组 Mapper 接口
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
public interface ApiGroupMapper extends BaseMapper<ApiGroup> {

    /**
     * 修改pids
     *
     * @author majianguo
     * @date 2021/5/22 上午10:03
     **/
    void updateSubPids(@Param("paramCondition") ParentIdsUpdateRequest parentIdsUpdateRequest);
}