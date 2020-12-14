package cn.stylefeng.roses.kernel.resource.scanner;

import cn.stylefeng.roses.kernel.resource.api.ResourceCollectorApi;
import cn.stylefeng.roses.kernel.resource.api.ResourceReportApi;
import cn.stylefeng.roses.kernel.resource.api.holder.InitScanFlagHolder;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ReportResourceParam;
import cn.stylefeng.roses.kernel.resource.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.resource.api.pojo.scanner.ScannerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * 监听项目初始化完毕，汇报资源到服务（可为远程服务，可为本服务）
 *
 * @author fengshuonan
 * @date 2020/10/19 22:27
 */
@Slf4j
public class ResourceReportListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // 如果是配置中心的上下文略过，spring cloud环境environment会读取不到
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        // 获取有没有开资源扫描开关
        ScannerProperties scannerProperties = applicationContext.getBean(ScannerProperties.class);
        if (!scannerProperties.getOpen()) {
            return;
        }

        // 如果项目还没进行资源扫描
        if (!InitScanFlagHolder.getFlag()) {

            // 获取当前系统的所有资源
            ResourceCollectorApi resourceCollectorApi = applicationContext.getBean(ResourceCollectorApi.class);
            Map<String, Map<String, ResourceDefinition>> modularResources = resourceCollectorApi.getModularResources();

            // 持久化资源，发送资源到资源服务或本项目（单体项目）
            ResourceReportApi resourceService = applicationContext.getBean(ResourceReportApi.class);
            resourceService.reportResources(new ReportResourceParam(scannerProperties.getAppCode(), modularResources));

            // 设置标识已经扫描过
            InitScanFlagHolder.setFlag();
        }

    }

}
