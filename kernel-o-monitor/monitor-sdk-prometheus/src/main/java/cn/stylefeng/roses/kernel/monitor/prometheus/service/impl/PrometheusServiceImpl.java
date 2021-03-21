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
