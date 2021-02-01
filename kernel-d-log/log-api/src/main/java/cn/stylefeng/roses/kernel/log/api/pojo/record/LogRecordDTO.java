package cn.stylefeng.roses.kernel.log.api.pojo.record;

import lombok.Data;

import java.util.Date;

/**
 * 日志记录需要的参数
 *
 * @author fengshuonan
 * @date 2020/10/27 16:31
 */
@Data
public class LogRecordDTO {

    /**
     * 日志id
     */
    private Long logId;

    /**
     * 日志的名称，一般为业务名称
     */
    private String logName;

    /**
     * 日志记录的内容
     */
    private Object logContent;

    /**
     * 服务名称，一般为spring.application.name
     */
    private String appName;

    /**
     * http或方法的请求参数体
     */
    private String requestParams;

    /**
     * http或方法的请求结果
     */
    private String requestResult;

    /**
     * 操作发生的时间
     */
    private Date dateTime;

    /**
     * 当前服务器的ip
     */
    private String serverIp;

    /**
     * 客户端请求的token
     * <p>
     * 如果是http请求，并且用户已经登录，可以带这项
     */
    private String token;

    /**
     * 客户端请求的用户id
     * <p>
     * 如果是http请求，并且用户已经登录，可以带这项
     */
    private Long userId;

    /**
     * 客户端的ip
     * <p>
     * 如果是http请求，可以带这项
     */
    private String clientIp;

    /**
     * 当前用户请求的requestUrl
     * <p>
     * 如果是http请求，可以带这项
     */
    private String requestUrl;

    /**
     * 请求方式（GET POST PUT DELETE)
     * <p>
     * 如果是http请求，可以带这项
     */
    private String httpMethod;

    /**
     * 浏览器
     * <p>
     * 如果是http请求，可以带这项
     */
    private String clientBrowser;

    /**
     * 操作系统
     * <p>
     * 如果是http请求，可以带这项
     */
    private String clientOs;

}
