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
package cn.stylefeng.roses.kernel.log.starter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.LogRecordApi;
import cn.stylefeng.roses.kernel.log.api.enums.LogSaveTypeEnum;
import cn.stylefeng.roses.kernel.log.api.expander.LogConfigExpander;
import cn.stylefeng.roses.kernel.log.api.pojo.log.SysLogProperties;
import cn.stylefeng.roses.kernel.log.api.threadpool.LogManagerThreadPool;
import cn.stylefeng.roses.kernel.log.db.DbLogManagerServiceImpl;
import cn.stylefeng.roses.kernel.log.db.DbLogRecordServiceImpl;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import cn.stylefeng.roses.kernel.log.db.service.impl.SysLogServiceImpl;
import cn.stylefeng.roses.kernel.log.file.FileLogManagerServiceImpl;
import cn.stylefeng.roses.kernel.log.file.FileLogRecordServiceImpl;
import cn.stylefeng.roses.kernel.log.requestapi.RequestApiLogRecordAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 17:12
 */
@Configuration
public class GunsLogAutoConfiguration {

    /**
     * 日志配置的前缀
     */
    public static final String SYS_LOG_PREFIX = "sys-log";


    /**
     * 系统日志service
     *
     * @return sysLogService
     * @author liuhanqing
     * @date 2020/12/28 22:09
     */
    @Bean
    @ConditionalOnMissingBean(SysLogService.class)
    @ConditionalOnProperty(prefix = SYS_LOG_PREFIX, name = "type", havingValue = "db")
    public SysLogService sysLogService() {
        return new SysLogServiceImpl();
    }

    /**
     * 系统日志的配置
     *
     * @author liuhanqing
     * @date 2020/12/20 14:17
     */
    @Bean
    @ConfigurationProperties(prefix = SYS_LOG_PREFIX)
    public SysLogProperties sysLogProperties() {
        return new SysLogProperties();
    }

    /**
     * 每个请求接口记录日志的AOP
     * 根据配置文件初始化日志记录器
     * 日志存储类型：db-数据库，file-文件，默认存储在数据库中
     *
     * @param sysLogProperties 系统日志配置文件
     * @param sysLogService    系统日志service
     * @author liuhanqing
     * @date 2020/12/20 13:02
     */
    @Bean
    public RequestApiLogRecordAop requestApiLogRecordAop(SysLogProperties sysLogProperties, SysLogServiceImpl sysLogService) {

        // 如果类型是文件
        if (StrUtil.isNotBlank(sysLogProperties.getType())
                && LogSaveTypeEnum.FILE.getCode().equals(sysLogProperties.getType())) {

            // 修改为从sys_config中获取日志存储位置
            String fileSavePath = "";
            if (SystemUtil.getOsInfo().isWindows()) {
                fileSavePath = LogConfigExpander.getLogFileSavePathWindows();
            } else {
                fileSavePath = LogConfigExpander.getLogFileSavePathLinux();
            }

            return new RequestApiLogRecordAop(new FileLogRecordServiceImpl(fileSavePath, new LogManagerThreadPool()));
        }

        // 其他情况用数据库存储日志
        return new RequestApiLogRecordAop(new DbLogRecordServiceImpl(new LogManagerThreadPool(), sysLogService));
    }

    /**
     * 日志管理器
     *
     * @param sysLogProperties 系统日志配置文件
     * @author liuhanqing
     * @date 2020/12/20 18:53
     */
    @Bean
    public LogManagerApi logManagerApi(SysLogProperties sysLogProperties) {

        // 如果类型是文件
        if (StrUtil.isNotBlank(sysLogProperties.getType())
                && LogSaveTypeEnum.FILE.getCode().equals(sysLogProperties.getType())) {

            // 修改为从sys_config中获取日志存储位置
            String fileSavePath = "";
            if (SystemUtil.getOsInfo().isWindows()) {
                fileSavePath = LogConfigExpander.getLogFileSavePathWindows();
            } else {
                fileSavePath = LogConfigExpander.getLogFileSavePathLinux();
            }

            return new FileLogManagerServiceImpl(fileSavePath);
        }

        // 其他情况用数据库存储日志
        return new DbLogManagerServiceImpl();
    }

    /**
     * 日志记录的api
     *
     * @author fengshuonan
     * @date 2021/3/4 22:16
     */
    @Bean
    public LogRecordApi logRecordApi(SysLogServiceImpl sysLogService) {
        return new DbLogRecordServiceImpl(new LogManagerThreadPool(), sysLogService);
    }

}
