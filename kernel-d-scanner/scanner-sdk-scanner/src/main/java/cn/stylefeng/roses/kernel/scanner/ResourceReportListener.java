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
package cn.stylefeng.roses.kernel.scanner;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.listener.ApplicationReadyListener;
import cn.stylefeng.roses.kernel.scanner.api.DevOpsReportApi;
import cn.stylefeng.roses.kernel.scanner.api.ResourceCollectorApi;
import cn.stylefeng.roses.kernel.scanner.api.ResourceReportApi;
import cn.stylefeng.roses.kernel.scanner.api.constants.ScannerConstants;
import cn.stylefeng.roses.kernel.scanner.api.holder.InitScanFlagHolder;
import cn.stylefeng.roses.kernel.scanner.api.pojo.devops.DevOpsReportProperties;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ReportResourceParam;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import cn.stylefeng.roses.kernel.scanner.api.pojo.scanner.ScannerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;

/**
 * 监听项目初始化完毕，汇报资源到服务（可为远程服务，可为本服务）
 *
 * @author fengshuonan
 * @date 2020/10/19 22:27
 */
@Slf4j
public class ResourceReportListener extends ApplicationReadyListener implements Ordered {

    @Override
    public void eventCallback(ApplicationReadyEvent event) {

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        // 获取有没有开资源扫描开关
        ScannerProperties scannerProperties = applicationContext.getBean(ScannerProperties.class);
        if (!scannerProperties.getOpen()) {
            // 设置已经扫描标识
            InitScanFlagHolder.setFlag();
            return;
        }

        // 如果项目还没进行资源扫描
        if (!InitScanFlagHolder.getFlag()) {

            // 获取当前系统的所有资源
            ResourceCollectorApi resourceCollectorApi = applicationContext.getBean(ResourceCollectorApi.class);
            Map<String, Map<String, ResourceDefinition>> modularResources = resourceCollectorApi.getModularResources();

            // 持久化资源，发送资源到资源服务或本项目
            ResourceReportApi resourceService = applicationContext.getBean(ResourceReportApi.class);
            List<SysResourcePersistencePojo> persistencePojos = resourceService.reportResourcesAndGetResult(new ReportResourceParam(scannerProperties.getAppCode(), modularResources));

            // 向DevOps一体化平台汇报资源
            DevOpsReportProperties devOpsReportProperties = applicationContext.getBean(DevOpsReportProperties.class);
            // 如果配置了相关属性则进行DevOps资源汇报
            if (ObjectUtil.isAllNotEmpty(devOpsReportProperties,
                    devOpsReportProperties.getServerHost(),
                    devOpsReportProperties.getProjectInteractionSecretKey(),
                    devOpsReportProperties.getProjectUniqueCode(),
                    devOpsReportProperties.getServerHost())) {
                DevOpsReportApi devOpsReportApi = applicationContext.getBean(DevOpsReportApi.class);
                try {
                    devOpsReportApi.reportResources(devOpsReportProperties, persistencePojos);
                } catch (Exception e) {
                    log.error("向DevOps平台汇报异常出现网络错误，如无法联通DevOps平台可关闭相关配置。", e);
                }
            }

            // 设置标识已经扫描过
            InitScanFlagHolder.setFlag();
        }

    }

    @Override
    public int getOrder() {
        return ScannerConstants.REPORT_RESOURCE_LISTENER_SORT;
    }

}
