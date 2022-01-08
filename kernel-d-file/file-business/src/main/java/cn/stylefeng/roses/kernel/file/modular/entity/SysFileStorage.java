package cn.stylefeng.roses.kernel.file.modular.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 文件存储信息实例类
 *
 * @author fengshuonan
 * @date 2022/01/08 15:53
 */
@TableName("sys_file_storage")
@Data
public class SysFileStorage {

    /**
     * 文件主键id，关联file_info表的主键
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long fileId;

    /**
     * 具体文件的字节信息
     */
    @TableField("file_bytes")
    private byte[] fileBytes;

}