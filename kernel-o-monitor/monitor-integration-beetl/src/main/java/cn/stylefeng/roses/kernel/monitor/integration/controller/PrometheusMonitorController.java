/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.monitor.integration.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.monitor.api.PrometheusApi;
import cn.stylefeng.roses.kernel.monitor.api.constants.MonitorConstants;
import cn.stylefeng.roses.kernel.monitor.api.pojo.prometheus.PromResultInfo;
import cn.stylefeng.roses.kernel.monitor.system.holder.SystemHardwareInfoHolder;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 来自prometheus的监控
 *
 * @author chenli
 * @date 2020/12/30 16:40
 */
@Controller
@ApiResource(name = "来自prometheus的监控")
public class PrometheusMonitorController {

    @Value("${spring.application.name}")
    private String name;

    @Value("${prometheus.url}")
    private String prometheusUrl;

    @Value("${prometheus.instance}")
    private String prometheusInstance;

    @Resource
    private PrometheusApi service;

    /**
     * tomcat监控页面
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "tomcat监控首页", path = "/view/monitor/tomcatInfo")
    public String tomcatIndex() {
        return "/modular/system/monitor/tomcatInfo.html";
    }

    /**
     * tomcat监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "tomcat监控数据", path = "/view/monitor/getTomcatInfo")
    @ResponseBody
    public String tomcatInfo() {
        Map<String, Object> metricMap = getMetricInfos(getPromQl(), "tomcat_", "", "");
        return JSON.toJSONString(metricMap);
    }

    /**
     * jvm监控页面
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "jvm监控页面", path = "/view/monitor/jvmInfo")
    public String jvmIndex() {
        return "/modular/system/monitor/jvmInfo.html";
    }

    /**
     * jvm监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "jvm监控数据", path = "/view/monitor/getJvmInfo")
    @ResponseBody
    public String jvmInfo(@RequestParam("id") String id, @RequestParam("area") String area) {
        Map<String, Object> metricMap = getMetricInfos(getPromQl(id, area), "jvm_", "", "");
        return JSON.toJSONString(metricMap);
    }

    /**
     * 性能监控页面
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "性能监控页面", path = "/view/monitor/performanceInfo")
    public String performanceIndex() {
        return "/modular/system/monitor/performanceInfo.html";
    }

    /**
     * 性能监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "CPU监控数据", path = "/view/monitor/getCpuInfo")
    @ResponseBody
    public String cpuInfo() {
        Map<String, Object> metricMap = getMetricInfos(getPromQl(), "cpu_", "", "");
        return JSON.toJSONString(metricMap);
    }

    /**
     * 服务器负载监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "服务器负载监控数据", path = "/view/monitor/getLoadInfo")
    @ResponseBody
    public String loadInfo() {
        Map<String, Object> metricMap = getMetricInfos(getPromQl(), "system_", "", "");
        return JSON.toJSONString(metricMap);
    }

    /**
     * 进程监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "进程监控数据", path = "/view/monitor/getProcessInfo")
    @ResponseBody
    public String processInfo() {
        Map<String, Object> metricMap = getMetricInfos(getPromQl(), "process_", "", "");
        return JSON.toJSONString(metricMap);
    }

    /**
     * 日志监控页面
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "日志监控页面", path = "/view/monitor/logbackInfo")
    public String logbackIndex() {
        return "/modular/system/monitor/logbackInfo.html";
    }

    /**
     * 日志监控数据
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    @GetResource(name = "日志监控数据", path = "/view/monitor/getLogbackInfo")
    @ResponseBody
    public String logbackInfo(@RequestParam("level") String level, @RequestParam("timeInterval") String timeInterval, @RequestParam("isRate") String isRate, @RequestParam("rateMetric") String rateMetric) {
        if (StrUtil.isEmpty(timeInterval)) {
            timeInterval = "[5m]";
        }
        Map<String, Object> metricMap = getMetricInfos(getIratePromQl(level, timeInterval), "logback_", isRate, rateMetric);
        return JSON.toJSONString(metricMap);
    }

    /**
     * 组装prometheus查询sql
     *
     * @param id   同一指标不同分类的id号
     * @param area 查询区域jvm常用
     * @author chenli
     * @date 2021/1/4 16:32
     */
    private String getPromQl(String id, String area) {
        StringBuilder promql = new StringBuilder("{application=\"");
        promql.append(name);
        if (!StrUtil.isEmpty(id)) {
            promql.append("\",id=\"");
            promql.append(id);
        }
        if (!StrUtil.isEmpty(area)) {
            promql.append("\",area=\"");
            promql.append(area);
        }
        if (!StrUtil.isEmpty(prometheusInstance)) {
            promql.append("\",instance=\"");
            promql.append(prometheusInstance);
        }
        promql.append("\"}");
        return promql.toString();
    }

    /**
     * 组装prometheus查询sql方法重写不带参数
     *
     * @author chenli
     * @date 2021/1/4 16:32
     */
    private String getPromQl() {
        StringBuilder promql = new StringBuilder("{application=\"");
        promql.append(name);
        if (!StrUtil.isEmpty(prometheusInstance)) {
            promql.append("\",instance=\"");
            promql.append(prometheusInstance);
        }
        promql.append("\"}");
        return promql.toString();
    }

    /**
     * 组装prometheus平均值查询sql方法重写不带参数
     *
     * @param level        日志统计查询参数，info、warn、error、trace、debug
     * @param timeInterval 统计时间区间单位通常为分钟(m)
     * @author chenli
     * @date 2021/1/4 16:32
     */
    private String getIratePromQl(String level, String timeInterval) {
        StringBuilder promql = new StringBuilder("{application=\"");
        promql.append(name);
        if (!StrUtil.isEmpty(prometheusInstance)) {
            promql.append("\",instance=\"");
            promql.append(prometheusInstance);
        }
        if (!StrUtil.isEmpty(level)) {
            promql.append("\",level=\"");
            promql.append(level);
        }
        promql.append("\"}");
        promql.append(timeInterval);
        return promql.toString();
    }

    /**
     * 分别输出监控名称以及对应的值
     *
     * @param promQL prometheus查询sql
     * @param metric prometheus指标前缀，比如："jvm_"
     * @param isRate prometheus计算函数，不需要计算周期内值就直接为"",需要计算则写计算函数以及对应的指标
     * @author chenli
     * @date 2021/1/4 16:32
     */
    private Map<String, Object> getMetricInfos(String promQL, String metric, String isRate, String rateMetric) {
        Map<String, Object> metricMap = new HashMap<>();
        if (!StrUtil.isEmpty(prometheusUrl)) {
            List<PromResultInfo> promResultInfos = service.getMetricInfo(prometheusUrl.concat(MonitorConstants.PROMETHEUS_QUERY_RANGE), promQL, isRate, rateMetric);
            if (Objects.isNull(promResultInfos)) {
                return metricMap;
            }
            for (PromResultInfo promResultInfo : promResultInfos) {
                String metricName = promResultInfo.getMetric().get__name__();
                JSONArray valueArray = JSONArray.parseArray(JSON.toJSONString(promResultInfo.getValues()).replaceAll("\\\\", "").replace("\"", ""));
                if (!StrUtil.isEmpty(metricName)) {
                    if (metricName.contains(metric)) {
                        // 得到的数据为数组，需要转为json字符串去除双引号再转化为JSONArray，JSONArray是echarts时间序列图需要的数据格式
                        metricMap.put(metricName, valueArray);
                    }
                } else {
                    // 查询指定的指标
                    metricMap.put(rateMetric, valueArray);
                }
            }
        }
        return metricMap;
    }
}
