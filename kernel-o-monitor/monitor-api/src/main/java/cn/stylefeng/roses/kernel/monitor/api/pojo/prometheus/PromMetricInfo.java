package cn.stylefeng.roses.kernel.monitor.api.pojo.prometheus;

import lombok.Data;

/**
 * prometheus返回监控指标信息
 *
 * @author chenli
 * @date 2021/1/10 18:53
 */
@Data
public class PromMetricInfo {

    /**
     * prometheus监控指标id
     */
    private String id;

    /**
     * prometheus监控指标名称
     */
    private String __name__;

    /**
     * prometheus实例名称
     */
    private String instance;

    /**
     * prometheus任务名称
     */
    private String job;

    /**
     * prometheus jvm区域指标
     */
    private String area;

    /**
     * prometheus 日志监控级别
     */
    private String level;

    /**
     * prometheus 是否需要计算周期内数据
     */
    private String isRate;

    /**
     * prometheus 指定监控指标
     */
    private String rateMetric;
}

