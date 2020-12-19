package cn.stylefeng.roses.kernel.file.pojo.response;

import lombok.Data;

/**
 * 文件信息结果集
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@Data
public class SysFileInfoResponse {

    /**
     * 主键id
     */
    private Long fileId;

    /**
     * 文件编码，解决一个文件多个版本问题，多次上传文件编码不变
     */
    private Long fileCode;

    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    private Integer fileLocation;

    /**
     * 文件仓库
     */
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件名）
     */
    private String fileOriginName;

    /**
     * 文件后缀，例如.txt
     */
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    private Long fileSizeKb;

    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    private String fileObjectName;

    /**
     * 存储路径
     */
    private String filePath;

    /**
     * 文件的字节
     */
    private byte[] fileBytes;

}
