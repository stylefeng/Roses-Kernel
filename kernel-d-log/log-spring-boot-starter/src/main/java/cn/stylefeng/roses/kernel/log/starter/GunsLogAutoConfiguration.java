package cn.stylefeng.roses.kernel.log.starter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.enums.LogSaveTypeEnum;
import cn.stylefeng.roses.kernel.log.api.expander.LogConfigExpander;
import cn.stylefeng.roses.kernel.log.api.pojo.log.SysLogProperties;
import cn.stylefeng.roses.kernel.log.api.threadpool.LogManagerThreadPool;
import cn.stylefeng.roses.kernel.log.db.DbLogManagerServiceImpl;
import cn.stylefeng.roses.kernel.log.db.DbLogRecordServiceImpl;
import cn.stylefeng.roses.kernel.log.db.service.impl.SysLogServiceImpl;
import cn.stylefeng.roses.kernel.log.file.FileLogManagerServiceImpl;
import cn.stylefeng.roses.kernel.log.file.FileLogRecordServiceImpl;
import cn.stylefeng.roses.kernel.log.modular.requestapi.aop.RequestApiLogRecordAop;
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
     * 系统日志的的配置
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
     * @author liuhanqing
     * @date 2020/12/20 13:02
     */
    @Bean
    public RequestApiLogRecordAop requestApiLogRecordAop(SysLogProperties sysLogProperties) {

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
        return new RequestApiLogRecordAop(new DbLogRecordServiceImpl(new LogManagerThreadPool(), new SysLogServiceImpl()));
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

}