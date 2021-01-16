package cn.stylefeng.roses.kernel.log.api.pojo.manage;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 日志管理的查询参数
 *
 * @author fengshuonan
 * @date 2020/10/28 11:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogManagerParam extends BaseRequest {

    /**
     * 单条日志id
     */
    private Long logId;

    /**
     * 查询的起始时间
     */
    @NotBlank(message = "起始时间不能为空", groups = {delete.class})
    private String beginDateTime;

    /**
     * 查询日志的结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = {delete.class})
    private String endDateTime;

    /**
     * 日志的名称，一般为业务名称
     */
    private String logName;

    /**
     * 服务名称，一般为spring.application.name
     */
    @NotBlank(message = "服务名称不能为空", groups = {delete.class})
    private String appName;

    /**
     * 当前服务器的ip
     */
    private String serverIp;

    /**
     * 客户端请求的用户id
     */
    private Long userId;

    /**
     * 客户端的ip
     */
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    private String requestUrl;

}
