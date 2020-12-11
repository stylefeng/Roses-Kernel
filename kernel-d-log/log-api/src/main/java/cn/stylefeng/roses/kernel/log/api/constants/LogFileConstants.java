package cn.stylefeng.roses.kernel.log.api.constants;

/**
 * 日志文件的常量
 *
 * @author fengshuonan
 * @date 2020/10/28 15:56
 */
public interface LogFileConstants {

    /**
     * 记录日志文件名前缀，如果没有appName的话
     * <p>
     * 例如：app-logs-2020-10-28.log
     */
    String DEFAULT_LOG_FILE_NAME = "app-logs";

    /**
     * 文件拼接符号
     */
    String FILE_CONTRACT_SYMBOL = "-";

    /**
     * 日志文件的后缀名
     */
    String FILE_SUFFIX = ".log";

    /**
     * 默认文件存储路径（windows的）
     */
    String DEFAULT_FILE_SAVE_PATH_WINDOWS = "d:/logfiles";

    /**
     * 默认文件存储路径（linux和windows的的）
     */
    String DEFAULT_FILE_SAVE_PATH_LINUX = "/tmp/logfiles";

    /**
     * 默认api日志记录的aop的顺序
     */
    Integer DEFAULT_API_LOG_AOP_SORT = 500;

}
