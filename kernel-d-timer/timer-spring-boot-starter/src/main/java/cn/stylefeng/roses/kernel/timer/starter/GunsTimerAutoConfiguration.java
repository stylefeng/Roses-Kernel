package cn.stylefeng.roses.kernel.timer.starter;

import cn.stylefeng.roses.kernel.timer.api.TimerExeService;
import cn.stylefeng.roses.kernel.timer.hutool.HutoolTimerExeServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 21:34
 */
@Configuration
public class GunsTimerAutoConfiguration {

    /**
     * hutool的定时任务
     *
     * @author fengshuonan
     * @date 2020/12/1 21:18
     */
    @Bean
    @ConditionalOnMissingBean(TimerExeService.class)
    public TimerExeService timerExeService() {
        return new HutoolTimerExeServiceImpl();
    }

}
