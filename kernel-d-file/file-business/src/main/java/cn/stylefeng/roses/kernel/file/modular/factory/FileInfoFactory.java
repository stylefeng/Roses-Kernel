package cn.stylefeng.roses.kernel.file.modular.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.file.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.enums.FileStatusEnum;
import cn.stylefeng.roses.kernel.file.exception.FileException;
import cn.stylefeng.roses.kernel.file.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import cn.stylefeng.roses.kernel.file.pojo.request.SysFileInfoRequest;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import static cn.stylefeng.roses.kernel.file.constants.FileConstants.DEFAULT_BUCKET_NAME;
import static cn.stylefeng.roses.kernel.file.constants.FileConstants.FILE_POSTFIX_SEPARATOR;

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

        // 存储文件
        byte[] bytes;
        try {
            bytes = file.getBytes();
            fileOperatorApi.storageFile(DEFAULT_BUCKET_NAME, finalFileName, bytes);
        } catch (IOException e) {
            String userTip = StrUtil.format(FileExceptionEnum.ERROR_FILE.getUserTip(), e.getMessage());
            throw new FileException(FileExceptionEnum.ERROR_FILE, userTip);
        }

        // 计算文件大小kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));

        // 计算文件大小信息
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // 封装存储文件信息（上传替换公共信息）
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileLocation(FileLocationEnum.LOCAL.getCode());
        sysFileInfo.setFileBucket(DEFAULT_BUCKET_NAME);
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
