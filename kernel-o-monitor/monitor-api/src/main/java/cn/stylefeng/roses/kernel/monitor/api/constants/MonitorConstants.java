package cn.stylefeng.roses.kernel.monitor.api.constants;

/**
 * 监控模块常量
 *
 * @author fengshuonan
 * @date 2021/1/31 22:33
 */
public interface MonitorConstants {

    /**
     * 监控模块的名称
     */
    String MONITOR_MODULE_NAME = "kernel-o-monitor";

    /**
     * 异常枚举的步进值
     */
    String MONITOR_EXCEPTION_STEP_CODE = "27";

    /**
     * prometheus查询命令
     */
    String MONITOR_PROMETHEUS_QUERY = "query";

    /**
     * prometheus查询区间向量命令
     */
    String MONITOR_PROMETHEUS_QUERY_RANGE = "query_range";

    /**
     * prometheus查询开始时间
     */
    String MONITOR_PROMETHEUS_START = "start";

    /**
     * prometheus查询结束时间
     */
    String MONITOR_PROMETHEUS_END = "end";

    /**
     * prometheus查询步长
     */
    String MONITOR_PROMETHEUS_STEP = "step";
}
