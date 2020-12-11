package cn.stylefeng.roses.kernel.file.modular.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("sys_file_info")
public class SysFileInfo extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件存储位置（1:阿里云，2:腾讯云，3:minio，4:本地）
     */
    @TableField("file_location")
    private Integer fileLocation;

    /**
     * 文件仓库
     */
    @TableField("file_bucket")
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件名）
     */
    @TableField("file_origin_name")
    private String fileOriginName;

    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    @TableField("file_size_kb")
    private Long fileSizeKb;

    /**
     * 文件大小信息，计算后的
     */
    @TableField("file_size_info")
    private String fileSizeInfo;

    /**
     * 存储到bucket的名称（文件唯一标识id）
     */
    @TableField("file_object_name")
    private String fileObjectName;

    /**
     * 存储路径
     */
    @TableField("file_path")
    private String filePath;

}
