/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.file.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
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
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long fileId;

    /**
     * 文件编码,本号升级的依据
     * <p>
     * 解决一个文件多个版本问题，多次上传文件编码不变
     */
    @TableField("file_code")
    @ChineseDescription("文件编码")
    private Long fileCode;

    /**
     * 文件版本，从1开始
     */
    @TableField("file_version")
    @ChineseDescription("文件版本")
    private Integer fileVersion;

    /**
     * 当前状态：0-历史版，1-最新版
     */
    @TableField("file_status")
    @ChineseDescription("当前状态：0-历史版，1-最新版")
    private String fileStatus;

    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    @TableField("file_location")
    @ChineseDescription("文件存储位置")
    private Integer fileLocation;

    /**
     * 文件仓库（文件夹）
     */
    @TableField("file_bucket")
    @ChineseDescription("文件仓库(文件夹)")
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件全名）
     */
    @TableField("file_origin_name")
    @ChineseDescription("文件名称（上传时候的文件全名）")
    private String fileOriginName;

    /**
     * 文件后缀，例如.txt
     */
    @TableField("file_suffix")
    @ChineseDescription("文件后缀")
    private String fileSuffix;

    /**
     * 文件大小kb为单位
     */
    @TableField("file_size_kb")
    @ChineseDescription("文件大小")
    private Long fileSizeKb;

    /**
     * 文件大小信息，计算后的
     */
    @TableField("file_size_info")
    @ChineseDescription("文件大小信息，计算后的")
    private String fileSizeInfo;

    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    @TableField("file_object_name")
    @ChineseDescription("存储到bucket中的名称，主键id+.后缀")
    private String fileObjectName;

    /**
     * 存储路径
     */
    @TableField("file_path")
    @ChineseDescription("存储路径")
    private String filePath;

    /**
     * 是否为机密文件
     */
    @TableField("secret_flag")
    @ChineseDescription("是否为机密文件")
    private String secretFlag;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除")
    private String delFlag;

}
