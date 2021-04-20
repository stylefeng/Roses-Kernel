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
package cn.stylefeng.roses.kernel.monitor.api;

import cn.stylefeng.roses.kernel.monitor.api.pojo.prometheus.PromResultInfo;

import java.util.List;

/**
 * 监控管理prometheus
 *
 * @author chenli
 * @date 2021/1/10 16:09
 */
public interface PrometheusApi {

    /**
     * prometheus采集监控指标
     *
     * @param promURL prometheus地址
     * @param promQL  prometheus查询表达式
     * @param isRate  prometheus是否需要计算周期内数值函数
     * @param promQL  prometheus需要计算的指标
     * @return Map<String, Object>  指标名称与指标数据的集合
     * @author chenli
     * @date 2021/1/10 17:37
     */
    List<PromResultInfo> getMetricInfo(String promURL, String promQL, String isRate, String rateMetric);

    /**
     * 配置prometheus无效链接则关闭prometheus相关菜单
     *
     * @author chenli
     * @date 2021/3/3 16:08
     */
    void closePrometheusMenu();

    /**
     * 配置prometheus有效链接无效则显示prometheus相关菜单
     *
     * @author chenli
     * @date 2021/3/3 16:08
     */
    void displayPrometheusMenu();

}
