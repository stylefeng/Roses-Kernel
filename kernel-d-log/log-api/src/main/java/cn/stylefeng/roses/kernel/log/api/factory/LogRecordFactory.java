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
package cn.stylefeng.roses.kernel.log.api.factory;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.log.api.constants.LogConstants;
import cn.stylefeng.roses.kernel.log.api.context.ServerInfoContext;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;

import java.util.Date;

/**
 * 日志记录创建工厂，用来new LogRecordDTO，并填充一些信息
 * <p>
 * 一般用法为用本类去创建一个对象，再用其他的填充器去填充其他信息，例如认证，http接口信息等
 *
 * @author fengshuonan
 * @date 2020/10/28 17:28
 */
public class LogRecordFactory {

    /**
     * 创建日志记录
     *
     * @author fengshuonan
     * @date 2020/10/28 17:31
     */
    public static LogRecordDTO createLogRecord(String name, Object content) {
        LogRecordDTO logRecordDTO = new LogRecordDTO();

        //设置全局id
        logRecordDTO.setLogId(IdUtil.getSnowflake(1, 1).nextId());

        // 设置日志名称
        logRecordDTO.setLogName(name);

        // 设置日志内容
        logRecordDTO.setLogContent(content);

        // 设置appName
        String applicationName = null;
        try {
            // 修改直接从上下文环境中获取spring.application.name
            // 解决获取applicationName为空问题
            applicationName = SpringUtil.getApplicationContext().getEnvironment().getProperty("spring.application.name");
        } catch (Exception e) {
            applicationName = LogConstants.LOG_DEFAULT_APP_NAME;
        }
        logRecordDTO.setAppName(applicationName);

        // 设置当前时间
        logRecordDTO.setDateTime(new Date());

        // 设置server ip
        logRecordDTO.setServerIp(ServerInfoContext.getServerIp());

        return logRecordDTO;
    }

}
