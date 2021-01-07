package cn.stylefeng.roses.kernel.resource.api.constants;

/**
 * 资源扫描模块的常量
 *
 * @author fengshuonan
 * @date 2020/11/3 13:50
 */
public interface ScannerConstants {

    /**
     * 资源模块的名称
     */
    String RESOURCE_MODULE_NAME = "kernel-d-scanner";

    /**
     * 异常枚举的步进值
     */
    String RESOURCE_EXCEPTION_STEP_CODE = "17";

    /**
     * 资源前缀标识
     */
    String RESOURCE_CACHE_KEY = "GUNS_RESOURCE_CACHES";

    /**
     * 资源汇报的监听器的顺序
     */
    Integer REPORT_RESOURCE_LISTENER_SORT = 200;

    /**
     * 视图类型的控制器url路径开头
     */
    String VIEW_CONTROLLER_PATH_START_WITH = "/view";

}
