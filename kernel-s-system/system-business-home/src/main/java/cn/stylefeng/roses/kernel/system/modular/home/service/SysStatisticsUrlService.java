package cn.stylefeng.roses.kernel.system.modular.home.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.home.entity.SysStatisticsUrl;
import cn.stylefeng.roses.kernel.system.modular.home.pojo.request.SysStatisticsUrlRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 常用功能列表 服务类
 *
 * @author fengshuonan
 * @date 2022/02/10 21:17
 */
public interface SysStatisticsUrlService extends IService<SysStatisticsUrl> {

    /**
     * 新增
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void add(SysStatisticsUrlRequest sysStatisticsUrlRequest);

    /**
     * 删除
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void del(SysStatisticsUrlRequest sysStatisticsUrlRequest);

    /**
     * 编辑
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    void edit(SysStatisticsUrlRequest sysStatisticsUrlRequest);

    /**
     * 查询详情
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    SysStatisticsUrl detail(SysStatisticsUrlRequest sysStatisticsUrlRequest);

    /**
     * 获取列表
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @return List<SysStatisticsUrl>   返回结果
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    List<SysStatisticsUrl> findList(SysStatisticsUrlRequest sysStatisticsUrlRequest);

    /**
     * 获取列表（带分页）
     *
     * @param sysStatisticsUrlRequest 请求参数
     * @return PageResult<SysStatisticsUrl>   返回结果
     * @author fengshuonan
     * @date 2022/02/10 21:17
     */
    PageResult<SysStatisticsUrl> findPage(SysStatisticsUrlRequest sysStatisticsUrlRequest);

}
