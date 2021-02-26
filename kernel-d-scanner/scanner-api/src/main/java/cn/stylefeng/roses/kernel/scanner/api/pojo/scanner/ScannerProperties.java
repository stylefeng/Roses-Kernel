package cn.stylefeng.roses.kernel.scanner.api.pojo.scanner;

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
     * 扫描到的资源的url是否要带appCode属性，此值默认为false
     * <p>
     * 也就是资源的url上不会带appCode属性，一般在微服务的系统中需要把此值设为true
     */
    private Boolean urlWithAppCode = false;

    /**
     * 项目编码（如果您不设置的话，默认使用spring.application.name填充，一般不用手动设置此值）
     */
    private String appCode;

    /**
     * 扫描到的url是否要带context-path，默认为true也就是会带上
     * <p>
     * 如果设置为true，则资源的url属性会带当前项目的context-path
     * <p>
     * 如果设置为false，则资源url属性不会带context-path
     * <p>
     * 如果urlWithAppCode开关和urlWithContextPath都开了，生成的url会是如下： /appCode/contextPath/xxx
     */
    private Boolean urlWithContextPath = true;

    /**
     * 项目的context-path
     */
    private String contextPath;

    /**
     * 链接符号，一般不要修改此符号
     */
    private String linkSymbol = "$";

}
