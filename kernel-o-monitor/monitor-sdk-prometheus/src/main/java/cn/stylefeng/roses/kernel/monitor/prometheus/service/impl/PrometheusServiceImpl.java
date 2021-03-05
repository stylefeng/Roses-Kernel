package cn.stylefeng.roses.kernel.monitor.prometheus.service.impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.stylefeng.roses.kernel.monitor.api.constants.MonitorConstants;
import cn.stylefeng.roses.kernel.monitor.api.exception.MonitorException;
import cn.stylefeng.roses.kernel.monitor.api.exception.enums.MonitorExceptionEnum;
import cn.stylefeng.roses.kernel.monitor.api.pojo.prometheus.PromResponseInfo;
import cn.stylefeng.roses.kernel.monitor.api.pojo.prometheus.PromResultInfo;
import cn.stylefeng.roses.kernel.monitor.prometheus.mapper.PrometheusMenuMapper;
import cn.stylefeng.roses.kernel.monitor.prometheus.service.PrometheusService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控管理prometheus
 *
 * @author chenli
 * @date 2021/1/10 16:09
 */
@Service
@Slf4j
public class PrometheusServiceImpl implements PrometheusService {

    @Resource
    private PrometheusMenuMapper mapper;

    /**
     * prometheus采集监控指标
     *
     * @author chenli
     * @date 2021/1/10 16:09
     */
    @Override
    public List<PromResultInfo> getMetricInfo(String promURL, String promQL, String isRate, String rateMetric) {
        Map<String, Object> paramMap = new HashMap<>();
        String httpRes = "";
        // prometheus查询命令
        if(!StrUtil.isEmpty(isRate)){
            paramMap.put(MonitorConstants.MONITOR_PROMETHEUS_QUERY, getRatePromQl(isRate,rateMetric,promQL));
        } else {
            paramMap.put(MonitorConstants.MONITOR_PROMETHEUS_QUERY, promQL);
        }

        // 查询5分钟数据
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 5);

        // 查询开始时间
        paramMap.put(MonitorConstants.MONITOR_PROMETHEUS_START, (calendar.getTime().getTime()) / 1000L);

        // 查询结束时间
        paramMap.put(MonitorConstants.MONITOR_PROMETHEUS_END, (new Date().getTime()) / 1000L);

        // 查询步长
        paramMap.put(MonitorConstants.MONITOR_PROMETHEUS_STEP, 15);
        // 获取查询结果
        try {
            httpRes = HttpUtil.get(promURL, paramMap);
        } catch (IORuntimeException e) {
            log.error(MonitorExceptionEnum.PROMETHEUS_CONFIG_ERROR.getUserTip(),"","");
            //log.error("prometheus配置异常，具体信息为：{}", e.getMessage());
            // log.error(DictExceptionEnum.DICT_CODE_REPEAT.getUserTip(), "", dictCode);
//            throw new MonitorException(MonitorExceptionEnum.PROMETHEUS_CONFIG_ERROR);
            return null;
        }
        PromResponseInfo responseInfo = JSON.parseObject(httpRes, PromResponseInfo.class);
        if (ObjectUtil.isEmpty(responseInfo)) {
            // 监控指标未产生数据
            return null;
        }
        if (StrUtil.isEmpty(responseInfo.getStatus()) || !"success".equals(responseInfo.getStatus())) {
            // prometheus查询失败
            log.error("prometheus配置异常，具体信息为：{}", responseInfo.getStatus());
            return null;
        }
        return responseInfo.getData().getResult();
    }

    /**
     * 组装prometheus rate promql
     *
     * @author chenli
     * @date 2021/1/10 16:10
     */
    private String getRatePromQl(String isRate,String rateMetric,String promQl){
        StringBuilder ratePromQlBuilder = new StringBuilder(isRate);
        ratePromQlBuilder.append("(").append(rateMetric).append(promQl).append(")");
        return ratePromQlBuilder.toString();
    }

    /**
     * 配置prometheus无效链接则关闭prometheus相关菜单
     *
     * @author chenli
     * @date 2021/3/3 16:08
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closePrometheusMenu(){ mapper.displayOrClosePrometheusMenu(StatusEnum.DISABLE.getCode());
    }

    /**
     * 配置prometheus有效链接无效则显示prometheus相关菜单
     *
     * @author chenli
     * @date 2021/3/3 16:08
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void displayPrometheusMenu(){
        mapper.displayOrClosePrometheusMenu(StatusEnum.ENABLE.getCode());
    }

}
