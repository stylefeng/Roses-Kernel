package cn.stylefeng.roses.kernel.file.api.pojo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 附件列表返回类
 *
 * @author fengshuonan
 * @date 2020/12/30 21:24
 */
@Data
public class SysFileInfoListResponse implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 主键id
     */
    private Long fileId;

    /**
     * 文件编码
     * <p>
     * 解决一个文件多个版本问题，多次上传文件编码不变
     * <p>
     * 版本号升级的依据，code相同id不同视为同一个文件的不同版本
     */
    private Long fileCode;


    /**
     * 文件仓库（文件夹）
     */
    private String fileBucket;


    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    private String fileObjectName;

    /**
     * 是否为机密文件
     */
    private String secretFlag;

    /**
     * 文件应用Code名称
     */
    private String fileAppCodeName;

    /**
     * 文件名称（上传时候的文件名）
     */
    private String fileOriginName;


    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    private Integer fileLocation;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小信息，计算后的
     */
    private String fileSizeInfo;

    /**
     * 文件版本
     */
    private Integer fileVersion;

    /**
     * 创建人
     */
    private Long createAccountId;

    /**
     * 创建人部门id
     */
    private Long createDeptId;

    /**
     * 创建人姓名
     */
    private String createUserName;

}
