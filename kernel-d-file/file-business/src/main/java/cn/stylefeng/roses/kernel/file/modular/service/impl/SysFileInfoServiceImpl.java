package cn.stylefeng.roses.kernel.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.FileInfoApi;
import cn.stylefeng.roses.kernel.file.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.enums.FileLocationEnum;
import cn.stylefeng.roses.kernel.file.exception.FileException;
import cn.stylefeng.roses.kernel.file.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import cn.stylefeng.roses.kernel.file.modular.mapper.SysFileInfoMapper;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.file.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.file.util.DownloadUtil;
import cn.stylefeng.roses.kernel.file.util.PicFileTypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static cn.stylefeng.roses.kernel.file.constants.FileConstants.DEFAULT_BUCKET_NAME;
import static cn.stylefeng.roses.kernel.file.constants.FileConstants.FILE_POSTFIX_SEPARATOR;

/**
 * 文件信息表 服务实现类
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
@Service
@Slf4j
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService, FileInfoApi {

    @Resource
    private FileOperatorApi fileOperatorApi;

    @Override
    public Long uploadFile(MultipartFile file) {

        // 生成文件的唯一id
        Long fileId = IdWorker.getId();

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        // 获取文件后缀，不包含点
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
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024))
                .setScale(0, BigDecimal.ROUND_HALF_UP));

        //计算文件大小信息
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // 存储文件信息
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setId(fileId);
        sysFileInfo.setFileLocation(FileLocationEnum.LOCAL.getCode());
        sysFileInfo.setFileBucket(DEFAULT_BUCKET_NAME);
        sysFileInfo.setFileObjectName(finalFileName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        this.save(sysFileInfo);

        // 返回文件id
        return fileId;
    }

    @Override
    public void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {
        SysFileInfoResponse sysFileInfoResult = this.getFileInfoResult(sysFileInfoRequest.getId());
        String fileName = sysFileInfoResult.getFileOriginName();
        byte[] fileBytes = sysFileInfoResult.getFileBytes();
        DownloadUtil.download(fileName, fileBytes, response);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysFileInfoRequest sysFileInfoRequest) {

        // 查询文件的信息
        SysFileInfo sysFileInfo = this.getById(sysFileInfoRequest.getId());

        // 删除文件记录
        this.removeById(sysFileInfoRequest.getId());

        // 删除具体文件
        this.fileOperatorApi.deleteFile(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName());
    }

    @Override
    public void previewByFileId(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        byte[] fileBytes;

        // 根据文件id获取文件信息结果集
        SysFileInfoResponse sysFileInfoResult = this.getFileInfoResult(sysFileInfoRequest.getId());

        // 获取文件后缀
        String fileSuffix = sysFileInfoResult.getFileSuffix().toLowerCase();

        // 获取文件字节码
        fileBytes = sysFileInfoResult.getFileBytes();

        // 如果是图片类型，则直接输出
        if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix)) {
            renderPreviewFile(response, fileBytes);
        } else {
            // 不支持别的文件预览
            throw new FileException(FileExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
        }
    }

    @Override
    public void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // 获取文件字节码
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(sysFileInfoRequest.getFileBucket(), sysFileInfoRequest.getFileObjectName());
        } catch (Exception e) {
            log.error(">>> 获取文件流异常，具体信息为：{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // 如果是图片类型，则直接输出
        if (PicFileTypeUtil.getFileImgTypeFlag(sysFileInfoRequest.getFileObjectName())) {
            renderPreviewFile(response, fileBytes);
        } else {
            // 不支持别的文件预览
            throw new FileException(FileExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
        }
    }

    @Override
    public SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest) {
        return this.querySysFileInfo(sysFileInfoRequest);
    }

    @Override
    public PageResult<SysFileInfo> page(SysFileInfoRequest sysFileInfoRequest) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = createWrapper(sysFileInfoRequest);
        Page<SysFileInfo> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysFileInfo> list(SysFileInfoRequest sysFileInfoRequest) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        return this.list(queryWrapper);
    }

    @Override
    public SysFileInfoResponse getFileInfoResult(Long fileId) {

        SysFileInfoResponse sysFileInfoResult = new SysFileInfoResponse();

        // 查询库中文件信息
        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setId(fileId);
        SysFileInfo sysFileInfo = this.querySysFileInfo(sysFileInfoRequest);

        // 获取文件字节码
        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(DEFAULT_BUCKET_NAME, sysFileInfo.getFileObjectName());
        } catch (Exception e) {
            log.error(">>> 获取文件流异常，具体信息为：{}", e.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR);
        }

        // 设置文件字节码
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResult);
        sysFileInfoResult.setFileBytes(fileBytes);

        return sysFileInfoResult;
    }

    @Override
    public SysFileInfoResponse getFileInfoWithoutContent(Long fileId) {

        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setId(fileId);

        // 获取文件的基本信息
        SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);

        // 转化实体
        SysFileInfoResponse sysFileInfoResponse = new SysFileInfoResponse();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResponse);

        return sysFileInfoResponse;
    }

    /**
     * 获取文件信息表
     *
     * @author fengshuonan
     * @date 2020/11/29 13:40
     */
    private SysFileInfo querySysFileInfo(SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfo sysFileInfo = this.getById(sysFileInfoRequest.getId());
        if (ObjectUtil.isEmpty(sysFileInfo)) {
            String userTip = StrUtil.format(FileExceptionEnum.NOT_EXISTED.getUserTip(), sysFileInfoRequest.getId());
            throw new FileException(FileExceptionEnum.NOT_EXISTED, userTip);
        }
        return sysFileInfo;
    }

    /**
     * 创建组织架构的通用条件查询wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysFileInfo> createWrapper(SysFileInfoRequest sysFileInfoRequest) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysFileInfoRequest)) {

            // 拼接文件存储位置条件
            if (ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileLocation())) {
                queryWrapper.like(SysFileInfo::getFileLocation, sysFileInfoRequest.getFileLocation());
            }

            // 拼接文件仓库条件
            if (ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileBucket())) {
                queryWrapper.like(SysFileInfo::getFileBucket, sysFileInfoRequest.getFileBucket());
            }

            // 拼接文件名称（上传时候的文件名）
            if (ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileOriginName())) {
                queryWrapper.like(SysFileInfo::getFileOriginName, sysFileInfoRequest.getFileOriginName());
            }

            // 拼接文件名称（存储到系统的文件名）
            if (ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileObjectName())) {
                queryWrapper.like(SysFileInfo::getFileObjectName, sysFileInfoRequest.getFileObjectName());
            }

        }

        return queryWrapper;
    }

    /**
     * 渲染被预览的文件到servlet的response流中
     *
     * @author fengshuonan
     * @date 2020/11/29 17:13
     */
    private void renderPreviewFile(HttpServletResponse response, byte[] fileBytes) {
        try {
            // 设置contentType
            response.setContentType(MediaType.IMAGE_PNG_VALUE);

            // 获取outputStream
            ServletOutputStream outputStream = response.getOutputStream();

            // 输出字节流
            IoUtil.write(outputStream, true, fileBytes);
        } catch (IOException e) {
            String userTip = StrUtil.format(FileExceptionEnum.WRITE_BYTES_ERROR.getUserTip(), e.getMessage());
            throw new FileException(FileExceptionEnum.WRITE_BYTES_ERROR, userTip);
        }
    }

}
