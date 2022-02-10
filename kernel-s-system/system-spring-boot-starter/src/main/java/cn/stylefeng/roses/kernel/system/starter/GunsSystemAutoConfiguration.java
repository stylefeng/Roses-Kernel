package cn.stylefeng.roses.kernel.system.starter;

import cn.stylefeng.roses.kernel.system.modular.home.aop.InterfaceStatisticsAop;
import cn.stylefeng.roses.kernel.system.modular.home.holder.InterfaceStatisticsHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统自动配置
 *
 * @author xixiaowei
 * @date 2022/2/9 17:57
 */
@Configuration
public class GunsSystemAutoConfiguration {

    /**
     * 接口统计的AOP
     *
     * @author xixiaowei
     * @date 2022/2/9 14:00
     */
    @Bean
    public InterfaceStatisticsAop interfaceStatisticsAspect() {
        return new InterfaceStatisticsAop();
    }

    /**
     * 定时统计接口访问次数
     *
     * @author xixiaowei
     * @date 2022/2/9 17:58
     */
    @Bean
    @ConditionalOnMissingBean(InterfaceStatisticsHolder.class)
    public InterfaceStatisticsHolder interfaceStatisticsHolder() {
        return new InterfaceStatisticsHolder();
    }
}
