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
package cn.stylefeng.roses.kernel.log.api.pojo.manage;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 日志管理的查询参数
 *
 * @author fengshuonan
 * @date 2020/10/28 11:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogManagerRequest extends BaseRequest {

    /**
     * 单条日志id
     */
    @NotNull(message = "日志id不能为空", groups = {detail.class})
    @ChineseDescription("单条日志id")
    private Long logId;

    /**
     * 查询的起始时间
     */
    @NotBlank(message = "起始时间不能为空", groups = {delete.class})
    @ChineseDescription("查询的起始时间")
    private String beginDate;

    /**
     * 查询日志的结束时间
     */
    @ChineseDescription("查询的结束时间")
    @NotBlank(message = "结束时间不能为空", groups = {delete.class})
    private String endDate;

    /**
     * 日志的名称，一般为业务名称
     */
    @ChineseDescription("日志名称")
    private String logName;

    /**
     * 服务名称，一般为spring.application.name
     */
    @NotBlank(message = "服务名称不能为空", groups = {delete.class})
    @ChineseDescription("服务名称")
    private String appName;

    /**
     * 当前服务器的ip
     */
    @ChineseDescription("当前服务器ip")
    private String serverIp;

    /**
     * 客户端请求的用户id
     */
    @ChineseDescription("客户端请求的用户id")
    private Long userId;

    /**
     * 客户端的ip
     */
    @ChineseDescription("客户端的ip")
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    @ChineseDescription("当前用户的请求url")
    private String requestUrl;

}
