package cn.stylefeng.roses.kernel.log.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 日志的名称，一般为业务名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 日志记录的内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 服务名称，一般为spring.application.name
     */
    @TableField(value = "app_name")
    private String appName;

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
     * 操作发生的时间
     */
    @TableField(value = "date_time")
    private Date dateTime;

    /**
     * 当前服务器的ip
     */
    @TableField(value = "server_ip")
    private String serverIp;

    /**
     * 客户端请求的token,如果是http请求，并且用户已经登录
     */
    @TableField(value = "token")
    private String token;

    /**
     * 客户端请求的用户id,如果是http请求，并且用户已经登录
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 客户端的ip
     */
    @TableField(value = "client_ip")
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求方式（GET POST PUT DELETE)
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * 浏览器,如果是http请求
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 操作系统,如果是http请求
     */
    @TableField(value = "os")
    private String os;

}