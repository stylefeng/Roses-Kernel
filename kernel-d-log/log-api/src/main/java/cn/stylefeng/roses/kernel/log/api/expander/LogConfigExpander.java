package cn.stylefeng.roses.kernel.log.api.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.log.api.constants.LogFileConstants;

/**
 * 日志记录相关的配置
 *
 * @author fengshuonan
 * @date 2020/10/28 16:11
 */
public class LogConfigExpander {

    /**
     * 获取日志记录的文件存储的位置（windows服务器）
     * <p>
     * 末尾不带斜杠
     *
     * @author fengshuonan
     * @date 2020/10/28 16:14
     */
    public static String getLogFileSavePathWindows() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOG_FILE_SAVE_PATH_WINDOWS", String.class, LogFileConstants.DEFAULT_FILE_SAVE_PATH_WINDOWS);
    }

    /**
     * 获取日志记录的文件存储的位置（linux和mac服务器）
     * <p>
     * 末尾不带斜杠
     *
     * @author fengshuonan
     * @date 2020/10/28 16:14
     */
    public static String getLogFileSavePathLinux() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOG_FILE_SAVE_PATH_LINUX", String.class, LogFileConstants.DEFAULT_FILE_SAVE_PATH_LINUX);
    }

    /**
     * 获取接口api的aop日志记录的aop的顺序
     *
     * @author fengshuonan
     * @date 2020/10/28 17:19
     */
    public static Integer getRequestApiLogAopSort() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_REQUEST_API_LOG_AOP_SORT", Integer.class, LogFileConstants.DEFAULT_API_LOG_AOP_SORT);
    }

}
