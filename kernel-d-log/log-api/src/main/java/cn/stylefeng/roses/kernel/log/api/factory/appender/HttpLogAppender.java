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
package cn.stylefeng.roses.kernel.log.api.factory.appender;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志信息追加，用来追加http接口请求信息
 *
 * @author fengshuonan
 * @date 2020/10/27 17:45
 */
public class HttpLogAppender {

    /**
     * 追加请求信息到logRecordDTO
     *
     * @author fengshuonan
     * @date 2020/10/27 18:22
     */
    public static void appendHttpLog(LogRecordDTO logRecordDTO) {

        HttpServletRequest request;
        try {
            request = HttpServletUtil.getRequest();
        } catch (Exception e) {
            // 如果不是http环境，则直接返回
            return;
        }

        // 设置clientIp
        logRecordDTO.setClientIp(HttpServletUtil.getRequestClientIp(request));

        // 设置请求的url
        logRecordDTO.setRequestUrl(request.getServletPath());

        // 设置http的请求方法
        logRecordDTO.setHttpMethod(request.getMethod());

        // 解析http头，获取userAgent信息
        UserAgent userAgent = HttpServletUtil.getUserAgent(request);

        if (userAgent == null) {
            return;
        }

        // 设置浏览器标识
        if (ObjectUtil.isNotEmpty(userAgent.getBrowser())) {
            logRecordDTO.setClientBrowser(userAgent.getBrowser().getName());
        }

        // 设置浏览器操作系统
        if (ObjectUtil.isNotEmpty(userAgent.getOs())) {
            logRecordDTO.setClientOs(userAgent.getOs().getName());
        }

    }

}
