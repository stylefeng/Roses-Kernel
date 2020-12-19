package cn.stylefeng.roses.kernel.log.db.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志记录
 *
 * @author luojie
 * @date 2020/11/2 15:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_log")
public class SysLog extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;

    /**
     * 日志的名称，一般为业务名称
     */
    @TableField(value = "log_name")
    private String logName;

    /**
     * 日志记录的内容
     */
    @TableField(value = "log_content")
    private String logContent;

    /**
     * 服务名称，一般为spring.application.name
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 当前用户请求的url
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * http或方法的请求参数体
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * http或方法的请求结果
     */
    @TableField(value = "request_result")
    private String requestResult;

    /**
     * 当前服务器的ip
     */
    @TableField(value = "server_ip")
    private String serverIp;

    /**
     * 客户端的ip
     */
    @TableField(value = "client_ip")
    private String clientIp;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 请求http方法
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * 客户浏览器标识
     */
    @TableField(value = "client_browser")
    private String clientBrowser;

    /**
     * 客户操作系统
     */
    @TableField(value = "client_os")
    private String clientOs;

}