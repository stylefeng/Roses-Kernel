package cn.stylefeng.roses.kernel.file.pojo.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileInfoRequest extends BaseRequest {

    /**
     * 主键id
     */
    @NotNull(message = "fileId不能为空", groups = {delete.class, detail.class})
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
     * 文件仓库（文件夹）
     */
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件全名）
     */
    private String fileOriginName;

    /**
     * 文件后缀
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
     * 校验组，预览图片
     */
    public @interface preview {
    }

}
