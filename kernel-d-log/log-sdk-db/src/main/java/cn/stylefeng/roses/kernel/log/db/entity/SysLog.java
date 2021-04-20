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
