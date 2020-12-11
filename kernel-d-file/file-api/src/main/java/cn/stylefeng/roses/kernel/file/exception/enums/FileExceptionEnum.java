package cn.stylefeng.roses.kernel.file.exception.enums;

import cn.stylefeng.roses.kernel.file.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 文件操作相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/26 11:29
 */
@Getter
public enum FileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 阿里云文件操作异常
     */
    ALIYUN_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "01", "阿里云文件操作异常，具体信息为：{}"),

    /**
     * 腾讯云文件操作异常
     */
    TENCENT_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "02", "腾讯云文件操作异常，具体信息为：{}"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "03", "本地文件不存在，具体信息为：{}"),

    /**
     * MinIO文件操作异常
     */
    MINIO_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "04", "MinIO文件操作异常，具体信息为：{}"),

    /**
     * 上传文件操作异常
     */
    ERROR_FILE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "05", "上传文件操作异常，具体信息为：{}"),

    /**
     * 该条记录不存在
     */
    NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "06", "该文件信息不存在"),

    /**
     * 获取文件流错误
     */
    FILE_STREAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "07", "获取文件流错误"),

    /**
     * 下载文件错误
     */
    DOWNLOAD_FILE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "08", "下载文件错误，具体信息为：{}"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_NOT_SUPPORT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "09", "预览文件异常，您预览的文件类型不支持或文件出现错误"),

    /**
     * 预览文件参数存在空值
     */
    PREVIEW_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "10", "预览文件参数存在空值，请求参数为：{}"),

    /**
     * 渲染文件流字节出错
     */
    WRITE_BYTES_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "10", "渲染文件流字节出错，具体信息为：{}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FileExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
