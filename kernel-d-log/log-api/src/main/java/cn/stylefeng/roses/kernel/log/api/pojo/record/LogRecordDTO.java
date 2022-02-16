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
package cn.stylefeng.roses.kernel.log.api.pojo.record;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
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
    @ChineseDescription("日志id")
    private Long logId;

    /**
     * 日志的名称，一般为业务名称
     */
    @ChineseDescription("日志名称")
    private String logName;

    /**
     * 日志记录的内容
     */
    @ChineseDescription("日志记录内容")
    private Object logContent;

    /**
     * 服务名称，一般为spring.application.name
     */
    @ChineseDescription("服务名称")
    private String appName;

    /**
     * http或方法的请求参数体
     */
    @ChineseDescription("http或方法的请求参数体")
    private String requestParams;

    /**
     * http或方法的请求结果
     */
    @ChineseDescription("http或方法的请求结果")
    private String requestResult;

    /**
     * 操作发生的时间
     */
    @ChineseDescription("操作发生的时间")
    private Date dateTime;

    /**
     * 当前服务器的ip
     */
    @ChineseDescription("当前服务器的ip")
    private String serverIp;

    /**
     * 客户端请求的token
     * <p>
     * 如果是http请求，并且用户已经登录，可以带这项
     */
    @ChineseDescription("客户端请求的token")
    private String token;

    /**
     * 客户端请求的用户id
     * <p>
     * 如果是http请求，并且用户已经登录，可以带这项
     */
    @ChineseDescription("客户端请求的用户id")
    private Long userId;

    /**
     * 客户端的ip
     * <p>
     * 如果是http请求，可以带这项
     */
    @ChineseDescription("客户端的ip")
    private String clientIp;

    /**
     * 当前用户请求的requestUrl
     * <p>
     * 如果是http请求，可以带这项
     */
    @ChineseDescription("当前用户请求的requestUrl")
    private String requestUrl;

    /**
     * 请求方式（GET POST PUT DELETE)
     * <p>
     * 如果是http请求，可以带这项
     */
    @ChineseDescription("请求方式")
    private String httpMethod;

    /**
     * 浏览器
     * <p>
     * 如果是http请求，可以带这项
     */
    @ChineseDescription("浏览器")
    private String clientBrowser;

    /**
     * 操作系统
     * <p>
     * 如果是http请求，可以带这项
     */
    @ChineseDescription("操作系统")
    private String clientOs;

    /**
     * 创建时间
     */
    @ChineseDescription("创建时间")
    private Date createTime;

}
