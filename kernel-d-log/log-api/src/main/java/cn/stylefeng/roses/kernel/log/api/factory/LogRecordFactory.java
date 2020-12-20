package cn.stylefeng.roses.kernel.log.api.factory;

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

        // 设置日志名称
        logRecordDTO.setName(name);

        // 设置日志内容
        logRecordDTO.setContent(content);

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
