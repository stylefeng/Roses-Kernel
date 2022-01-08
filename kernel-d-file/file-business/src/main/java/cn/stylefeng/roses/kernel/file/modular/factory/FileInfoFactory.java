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
package cn.stylefeng.roses.kernel.file.modular.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.api.enums.FileStatusEnum;
import cn.stylefeng.roses.kernel.file.api.exception.FileException;
import cn.stylefeng.roses.kernel.file.api.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.api.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileStorageService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static cn.stylefeng.roses.kernel.file.api.constants.FileConstants.FILE_POSTFIX_SEPARATOR;

/**
 * 文件信息组装工厂
 *
 * @author fengshuonan
 * @date 2020/12/30 22:16
 */
public class FileInfoFactory {

    /**
     * 封装附件信息
     *
     * @author majianguo
     * @date 2020/12/27 12:55
     */
    public static SysFileInfo createSysFileInfo(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

        FileOperatorApi fileOperatorApi = SpringUtil.getBean(FileOperatorApi.class);
        SysFileStorageService fileStorageService = SpringUtil.getBean(SysFileStorageService.class);

        // 生成文件的唯一id
        Long fileId = IdWorker.getId();

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀（不包含点）
        String fileSuffix = null;
        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = StrUtil.subAfter(originalFilename, FILE_POSTFIX_SEPARATOR, true);
        }

        // 生成文件的最终名称
        String finalFileName = fileId + FILE_POSTFIX_SEPARATOR + fileSuffix;

        // 桶名
        String fileBucket = FileConfigExpander.getDefaultBucket();

        // 存储文件
        byte[] bytes;
        try {
            bytes = file.getBytes();
            if (StrUtil.isNotEmpty(sysFileInfoRequest.getFileBucket())) {
                fileBucket = sysFileInfoRequest.getFileBucket();
            }
            // 如果是存在数据库库里，单独处理一下
            if (FileLocationEnum.DB.getCode().equals(sysFileInfoRequest.getFileLocation())) {
                fileStorageService.saveFile(fileId, bytes);
            } else {
                fileOperatorApi.storageFile(fileBucket, finalFileName, bytes);
            }
        } catch (IOException e) {
            throw new FileException(FileExceptionEnum.ERROR_FILE, e.getMessage());
        }

        // 计算文件大小kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));

        // 计算文件大小信息
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // 封装存储文件信息（上传替换公共信息）
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(fileId);
        // 如果是存在数据库库里，单独处理一下
        if (FileLocationEnum.DB.getCode().equals(sysFileInfoRequest.getFileLocation())) {
            sysFileInfo.setFileLocation(FileLocationEnum.DB.getCode());
        } else {
            sysFileInfo.setFileLocation(fileOperatorApi.getFileLocationEnum().getCode());
        }
        sysFileInfo.setFileBucket(fileBucket);
        sysFileInfo.setFileObjectName(finalFileName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        sysFileInfo.setFileStatus(FileStatusEnum.NEW.getCode());
        sysFileInfo.setSecretFlag(sysFileInfoRequest.getSecretFlag());

        // 返回结果
        return sysFileInfo;
    }

}
