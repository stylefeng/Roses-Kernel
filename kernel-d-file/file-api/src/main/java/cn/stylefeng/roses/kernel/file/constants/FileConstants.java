package cn.stylefeng.roses.kernel.file.constants;

/**
 * 文件模块的常量
 *
 * @author fengshuonan
 * @date 2020/10/26 10:28
 */
public interface FileConstants {

    /**
     * 文件模块的名称
     */
    String FILE_MODULE_NAME = "kernel-d-file";

    /**
     * 异常枚举的步进值
     */
    String FILE_EXCEPTION_STEP_CODE = "09";

    /**
     * 文件分割符
     */
    String FILE_POSTFIX_SEPARATOR = ".";

    /**
     * 默认文件存储的位置
     */
    String DEFAULT_BUCKET_NAME = "defaultBucket";

    /**
     * 服务默认部署的环境地址，不要改这个常量，改系统配置表中的配置 SYS_SERVER_DEPLOY_HOST
     */
    String DEFAULT_SERVER_DEPLOY_HOST = "http://localhost:8080";

    /**
     * 文件鉴权的时间，默认两小时
     */
    Long DEFAULT_FILE_TIMEOUT_SECONDS = 7200L;

    /**
     * Guns中文件预览的接口
     */
    String FILE_PREVIEW_URL = "/sysFileInfo/preview";

}
