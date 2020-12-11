package cn.stylefeng.roses.kernel.log.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.log.api.LogRecordApi;

/**
 * 日志操作api的获取
 *
 * @author fengshuonan
 * @date 2020/10/27 16:19
 */
public class LogRecordContext {

    /**
     * 获取日志操作api
     *
     * @author fengshuonan
     * @date 2020/10/27 16:19
     */
    public static LogRecordApi me() {
        return SpringUtil.getBean(LogRecordApi.class);
    }

}
