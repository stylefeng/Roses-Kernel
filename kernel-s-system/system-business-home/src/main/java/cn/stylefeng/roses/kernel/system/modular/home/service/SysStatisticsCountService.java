package cn.stylefeng.roses.kernel.system.modular.home.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsCount;
import cn.stylefeng.roses.kernel.system.modular.home.pojo.request.SysStatisticsCountRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 常用功能的统计次数 服务类
 *
 * @author fengshuonan
 * @date 2022/02/10 21:17
 */
public interface SysStatisticsCountService extends IService<SysStatisticsCount> {

    /**
     * 新增
     *
     * @param sysStatisticsCountRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void add(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 删除
     *
     * @param sysStatisticsCountRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void del(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 编辑
     *
     * @param sysStatisticsCountRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void edit(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 查询详情
     *
     * @param sysStatisticsCountRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    SysStatisticsCount detail(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 获取列表
     *
     * @param sysStatisticsCountRequest 请求参数
     * @return List<SysStatisticsCount>   返回结果
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    List<SysStatisticsCount> findList(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 获取列表（带分页）
     *
     * @param sysStatisticsCountRequest 请求参数
     * @return PageResult<SysStatisticsCount>   返回结果
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    PageResult<SysStatisticsCount> findPage(SysStatisticsCountRequest sysStatisticsCountRequest);

    /**
     * 获取某个用户的统计次数
     *
     * @author fengshuonan
     * @date 2022/2/10 21:56
     */
    Integer getUserUrlCount(Long userId, Long statUrlId);

}
