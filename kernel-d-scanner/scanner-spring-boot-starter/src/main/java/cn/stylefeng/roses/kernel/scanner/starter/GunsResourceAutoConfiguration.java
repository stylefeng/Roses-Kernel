package cn.stylefeng.roses.kernel.scanner.starter;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.scanner.ApiResourceScanner;
import cn.stylefeng.roses.kernel.scanner.DefaultResourceCollector;
import cn.stylefeng.roses.kernel.scanner.api.ResourceCollectorApi;
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

}
