package cn.stylefeng.roses.kernel.timer.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;
import cn.stylefeng.roses.kernel.timer.api.TimerExeService;
import cn.stylefeng.roses.kernel.timer.api.enums.TimerJobStatusEnum;
import cn.stylefeng.roses.kernel.timer.api.exception.TimerException;
import cn.stylefeng.roses.kernel.timer.api.exception.enums.TimerExceptionEnum;
import cn.stylefeng.roses.kernel.timer.modular.entity.SysTimers;
import cn.stylefeng.roses.kernel.timer.modular.mapper.SysTimersMapper;
import cn.stylefeng.roses.kernel.timer.modular.param.SysTimersParam;
import cn.stylefeng.roses.kernel.timer.modular.service.SysTimersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务 服务实现类
 *
 * @author stylefeng
 * @date 2020/6/30 18:26
 */
@Service
public class SysTimersServiceImpl extends ServiceImpl<SysTimersMapper, SysTimers> implements SysTimersService {

    @Resource
    private TimerExeService timerExeService;

    @Override
    public void add(SysTimersParam sysTimersParam) {

        // 将dto转为实体
        SysTimers sysTimers = new SysTimers();
        BeanUtil.copyProperties(sysTimersParam, sysTimers);

        // 设置为停止状态，点击启动时启动任务
        sysTimers.setJobStatus(TimerJobStatusEnum.STOP.getCode());

        this.save(sysTimers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysTimersParam sysTimersParam) {

        // 更新库中记录
        SysTimers oldTimer = this.querySysTimers(sysTimersParam);
        BeanUtil.copyProperties(sysTimersParam, oldTimer);
        this.updateById(oldTimer);

        // 查看被编辑的任务的状态
        Integer jobStatus = oldTimer.getJobStatus();

        // 如果任务正在运行，则停掉这个任务，从新运行任务
        if (jobStatus.equals(TimerJobStatusEnum.RUNNING.getCode())) {
            CronUtil.remove(String.valueOf(oldTimer.getTimerId()));
            timerExeService.startTimer(
                    String.valueOf(sysTimersParam.getTimerId()),
                    sysTimersParam.getCron(),
                    sysTimersParam.getActionClass());
        }
    }

    @Override
    public void delete(SysTimersParam sysTimersParam) {

        // 先停止id为参数id的定时器
        CronUtil.remove(String.valueOf(sysTimersParam.getTimerId()));

        // 逻辑删除定时任务
        LambdaUpdateWrapper<SysTimers> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysTimers::getDelFlag, YesOrNotEnum.Y.getCode());
        updateWrapper.eq(SysTimers::getTimerId, sysTimersParam.getTimerId());
        this.update(updateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void start(SysTimersParam sysTimersParam) {

        // 更新库中的状态
        LambdaUpdateWrapper<SysTimers> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysTimers::getJobStatus, TimerJobStatusEnum.RUNNING.getCode())
                .eq(SysTimers::getTimerId, sysTimersParam.getTimerId());
        this.update(wrapper);

        // 添加定时任务调度
        SysTimers sysTimers = this.querySysTimers(sysTimersParam);
        timerExeService.startTimer(String.valueOf(sysTimers.getTimerId()), sysTimers.getCron(), sysTimers.getActionClass());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void stop(SysTimersParam sysTimersParam) {

        // 更新库中的状态
        LambdaUpdateWrapper<SysTimers> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysTimers::getJobStatus, TimerJobStatusEnum.STOP.getCode())
                .eq(SysTimers::getTimerId, sysTimersParam.getTimerId());
        this.update(wrapper);

        // 关闭定时任务调度
        SysTimers sysTimers = this.querySysTimers(sysTimersParam);
        timerExeService.stopTimer(String.valueOf(sysTimers.getTimerId()));
    }

    @Override
    public SysTimers detail(SysTimersParam sysTimersParam) {
        return this.querySysTimers(sysTimersParam);
    }

    @Override
    public PageResult<SysTimers> page(SysTimersParam sysTimersParam) {

        // 构造条件
        LambdaQueryWrapper<SysTimers> queryWrapper = createWrapper(sysTimersParam);

        // 查询分页结果
        Page<SysTimers> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysTimers> list(SysTimersParam sysTimersParam) {
        LambdaQueryWrapper<SysTimers> queryWrapper = createWrapper(sysTimersParam);
        return this.list(queryWrapper);
    }

    @Override
    public List<String> getActionClasses() {

        // 获取spring容器中的这类bean
        Map<String, TimerAction> timerActionMap = SpringUtil.getBeansOfType(TimerAction.class);
        if (ObjectUtil.isNotEmpty(timerActionMap)) {
            Collection<TimerAction> values = timerActionMap.values();
            return values.stream().map(i -> i.getClass().getName()).collect(Collectors.toList());
        } else {
            return CollectionUtil.newArrayList();
        }
    }

    /**
     * 获取定时任务详情
     *
     * @author stylefeng
     * @date 2020/6/30 18:26
     */
    private SysTimers querySysTimers(SysTimersParam sysTimersParam) {
        SysTimers sysTimers = this.getById(sysTimersParam.getTimerId());
        if (ObjectUtil.isEmpty(sysTimers) || sysTimers.getDelFlag().equals(YesOrNotEnum.Y.getCode())) {
            String userTip = StrUtil.format(TimerExceptionEnum.JOB_DETAIL_NOT_FOUND.getUserTip(), sysTimersParam.getTimerId());
            throw new TimerException(TimerExceptionEnum.JOB_DETAIL_NOT_FOUND, userTip);
        }
        return sysTimers;
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/12/19 17:02
     */
    private LambdaQueryWrapper<SysTimers> createWrapper(SysTimersParam sysTimersParam) {

        LambdaQueryWrapper<SysTimers> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(sysTimersParam)) {
            // 拼接查询条件-任务名称
            if (ObjectUtil.isNotEmpty(sysTimersParam.getTimerName())) {
                queryWrapper.like(SysTimers::getTimerName, sysTimersParam.getTimerName());
            }
            // 拼接查询条件-状态（字典 1运行  2停止）
            if (ObjectUtil.isNotEmpty(sysTimersParam.getJobStatus())) {
                queryWrapper.like(SysTimers::getJobStatus, sysTimersParam.getJobStatus());
            }
        }

        // 查询未删除的
        queryWrapper.ne(SysTimers::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按类型升序排列，同类型的排在一起
        queryWrapper.orderByDesc(BaseEntity::getCreateTime);

        return queryWrapper;
    }

}
