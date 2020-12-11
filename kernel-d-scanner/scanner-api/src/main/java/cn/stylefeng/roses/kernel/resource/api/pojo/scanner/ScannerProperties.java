package cn.stylefeng.roses.kernel.resource.api.pojo.scanner;

import lombok.Data;

/**
 * 扫描的常量
 *
 * @author fengshuonan
 * @date 2018-01-03 21:39
 */
@Data
public class ScannerProperties {

    /**
     * 资源扫描开关
     */
    private Boolean open;

    /**
     * 被扫描应用的名称
     */
    private String appName;

    /**
     * 应用的编码
     */
    private String appCode;

    /**
     * 链接符号
     */
    private String linkSymbol = "$";

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充，请不要设置此值，这个值和网关资源过滤有关）
     * <p>
     * 修复一个项目启动的时候会误删别的项目资源的问题
     *
     * @since 2.2.12
     */
    private String projectCode;

}
