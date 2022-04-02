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
package cn.stylefeng.roses.kernel.scanner.starter;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.scanner.ApiResourceScanner;
import cn.stylefeng.roses.kernel.scanner.DefaultResourceCollector;
import cn.stylefeng.roses.kernel.scanner.DevOpsReportImpl;
import cn.stylefeng.roses.kernel.scanner.api.DevOpsReportApi;
import cn.stylefeng.roses.kernel.scanner.api.ResourceCollectorApi;
import cn.stylefeng.roses.kernel.scanner.api.pojo.devops.DevOpsReportProperties;
import cn.stylefeng.roses.kernel.scanner.api.pojo.scanner.ScannerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 17:24
 */
@Configuration
public class GunsResourceAutoConfiguration {

    public static final String SCANNER_PREFIX = "scanner";

    public static final String DEVOPS_REPORT_PREFIX = "devops";

    @Value("${spring.application.name:}")
    private String springApplicationName;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    /**
     * 资源扫描器的配置
     *
     * @author fengshuonan
     * @date 2020/12/3 17:54
     */
    @Bean
    @ConfigurationProperties(prefix = SCANNER_PREFIX)
    public ScannerProperties scannerProperties() {
        return new ScannerProperties();
    }

    /**
     * DevOps一体化平台的交互配置
     *
     * @author fengshuonan
     * @date 2020/12/3 17:54
     */
    @Bean
    @ConfigurationProperties(prefix = DEVOPS_REPORT_PREFIX)
    public DevOpsReportProperties devOpsReportProperties() {
        return new DevOpsReportProperties();
    }

    /**
     * 资源扫描器
     *
     * @author fengshuonan
     * @date 2020/12/1 17:29
     */
    @Bean
    @ConditionalOnMissingBean(ApiResourceScanner.class)
    @ConditionalOnProperty(prefix = GunsResourceAutoConfiguration.SCANNER_PREFIX, name = "open", havingValue = "true")
    public ApiResourceScanner apiResourceScanner(ResourceCollectorApi resourceCollectorApi, ScannerProperties scannerProperties) {
        if (StrUtil.isBlank(scannerProperties.getAppCode())) {
            scannerProperties.setAppCode(springApplicationName);
        }
        if (StrUtil.isBlank(scannerProperties.getContextPath())) {
            scannerProperties.setContextPath(contextPath);
        }
        return new ApiResourceScanner(resourceCollectorApi, scannerProperties);
    }

    /**
     * 资源搜集器
     *
     * @author fengshuonan
     * @date 2020/12/1 17:29
     */
    @Bean
    @ConditionalOnMissingBean(ResourceCollectorApi.class)
    @ConditionalOnProperty(prefix = GunsResourceAutoConfiguration.SCANNER_PREFIX, name = "open", havingValue = "true")
    public ResourceCollectorApi resourceCollectorApi() {
        return new DefaultResourceCollector();
    }

    /**
     * 向DevOps平台汇报资源
     *
     * @author fengshuonan
     * @date 2022/4/2 14:41
     */
    @Bean
    public DevOpsReportApi devOpsReportApi() {
        return new DevOpsReportImpl();
    }

}
