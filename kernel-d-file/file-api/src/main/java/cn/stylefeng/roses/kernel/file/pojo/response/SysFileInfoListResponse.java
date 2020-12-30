package cn.stylefeng.roses.kernel.file.pojo.response;

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
     * 文件应用Code名称
     */
    private String fileAppCodeName;

    /**
     * 文件名称（上传时候的文件名）
     */
    private String fileOriginName;

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
