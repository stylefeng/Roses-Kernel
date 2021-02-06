package cn.stylefeng.roses.kernel.timer.modular.listener;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.timer.api.TimerExeService;
import cn.stylefeng.roses.kernel.timer.api.enums.TimerJobStatusEnum;
import cn.stylefeng.roses.kernel.timer.modular.entity.SysTimers;
import cn.stylefeng.roses.kernel.timer.modular.param.SysTimersParam;
import cn.stylefeng.roses.kernel.timer.modular.service.SysTimersService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 项目启动，将数据库所有定时任务开启
 *
 * @author fengshuonan
 * @date 2021/1/12 20:40
 */
public class TaskRunListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        SysTimersService sysTimersService = SpringUtil.getBean(SysTimersService.class);
        TimerExeService timerExeService = SpringUtil.getBean(TimerExeService.class);

        // 开启任务调度
        timerExeService.start();

        // 获取数据库所有开启状态的任务
        SysTimersParam sysTimersParam = new SysTimersParam();
        sysTimersParam.setJobStatus(TimerJobStatusEnum.RUNNING.getCode());
        List<SysTimers> list = sysTimersService.findList(sysTimersParam);

        // 添加定时任务到调度器
        for (SysTimers sysTimers : list) {
            timerExeService.startTimer(String.valueOf(sysTimers.getTimerId()), sysTimers.getCron(), sysTimers.getActionClass());
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
