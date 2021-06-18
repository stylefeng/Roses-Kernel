package cn.stylefeng.roses.kernel.system.modular.resource.mapper;

import cn.stylefeng.roses.kernel.system.api.pojo.resource.ApiGroupRequest;
import cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口信息 Mapper 接口
 *
 * @author majianguo
 * @date 2021/05/21 15:03
 */
public interface ApiResourceMapper extends BaseMapper<ApiResource> {

    /**
     * 数据列表
     *
     * @param apiGroupRequest 分组请求数据
     * @return {@link java.util.List<cn.stylefeng.roses.kernel.system.modular.resource.entity.ApiResource>}
     * @author majianguo
     * @date 2021/6/18 下午6:30
     **/
    List<ApiResource> dataList(@Param("paramCondition") ApiGroupRequest apiGroupRequest);
}