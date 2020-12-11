package cn.stylefeng.roses.kernel.timer.modular.tasks;

import cn.stylefeng.roses.kernel.timer.api.TimerAction;
import org.springframework.stereotype.Component;

/**
 * 这是一个定时任务的示例程序
 *
 * @author stylefeng
 * @date 2020/6/30 22:09
 */
@Component
public class SystemOutTaskRunner implements TimerAction {

    @Override
    public void action() {
        System.out.println("这是一个定时任务测试的程序，一直输出这行内容！");
    }

}
