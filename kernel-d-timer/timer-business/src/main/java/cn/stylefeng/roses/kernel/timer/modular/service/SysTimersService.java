package cn.stylefeng.roses.kernel.timer.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.timer.modular.entity.SysTimers;
import cn.stylefeng.roses.kernel.timer.modular.param.SysTimersParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 定时任务 服务类
 *
 * @author stylefeng
 * @date 2020/6/30 18:26
 */
public interface SysTimersService extends IService<SysTimers> {

    /**
     * 添加定时任务
     *
     * @param sysTimersParam 添加参数
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    void add(SysTimersParam sysTimersParam);

    /**
     * 删除定时任务
     *
     * @param sysTimersParam 删除参数
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    void del(SysTimersParam sysTimersParam);

    /**
     * 编辑定时任务
     *
     * @param sysTimersParam 编辑参数
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    void edit(SysTimersParam sysTimersParam);

    /**
     * 启动任务
     *
     * @param sysTimersParam 启动参数
     * @author stylefeng
     * @date 2020/7/1 14:36
     */
    void start(SysTimersParam sysTimersParam);

    /**
     * 停止任务
     *
     * @param sysTimersParam 停止参数
     * @author stylefeng
     * @date 2020/7/1 14:36
     */
    void stop(SysTimersParam sysTimersParam);

    /**
     * 查看详情定时任务
     *
     * @param sysTimersParam 查看参数
     * @return 定时任务
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    SysTimers detail(SysTimersParam sysTimersParam);

    /**
     * 分页查询定时任务
     *
     * @param sysTimersParam 查询参数
     * @return 查询分页结果
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    PageResult<SysTimers> findPage(SysTimersParam sysTimersParam);

    /**
     * 查询所有定时任务
     *
     * @param sysTimersParam 查询参数
     * @return 定时任务列表
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    List<SysTimers> findList(SysTimersParam sysTimersParam);

    /**
     * 获取所有可执行的任务列表
     *
     * @return TimerTaskRunner的所有子类名称集合
     * @author stylefeng
     * @date 2020/7/1 14:36
     */
    List<String> getActionClasses();

}
