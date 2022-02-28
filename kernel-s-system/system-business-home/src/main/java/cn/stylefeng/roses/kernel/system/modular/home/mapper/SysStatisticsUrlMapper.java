package cn.stylefeng.roses.kernel.system.modular.home.mapper;

import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsUrl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 常用功能列表 Mapper 接口
 *
 * @author fengshuonan
 * @date 2022/02/10 21:17
 */
public interface SysStatisticsUrlMapper extends BaseMapper<SysStatisticsUrl> {

    /**
     * 根据统计urlId集合获取菜单id集合
     *
     * @author fengshuonan
     * @date 2022/2/12 18:55
     */
    List<Long> getMenuIdsByStatUrlIdList(@Param("statUrlIds") List<Long> statUrlIds);

}
