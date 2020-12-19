package cn.stylefeng.roses.kernel.file.modular.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.file.exception.FileException;
import cn.stylefeng.roses.kernel.file.exception.enums.FileExceptionEnum;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.file.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static cn.stylefeng.roses.kernel.file.constants.FileConstants.FILE_PREVIEW_URL;

/**
 * 文件信息管理
 *
 * @author fengshuonan
 * @date 2020/11/29 11:17
 */
@RestController
@ApiResource(name = "文件信息相关接口")
public class SysFileInfoController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * 上传文件
     *
     * @author fengshuonan
     * @date 2020/11/29 11:17
     */
    @PostResource(name = "上传文件", path = "/sysFileInfo/upload")
    public ResponseData upload(@RequestPart("file") MultipartFile file) {
        Long fileId = sysFileInfoService.uploadFile(file);
        return new SuccessResponseData(fileId);
    }

    /**
     * 下载文件
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "下载文件", path = "/sysFileInfo/download")
    public void download(@Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {
        sysFileInfoService.download(sysFileInfoRequest, response);
    }

    /**
     * 删除文件信息
     *
     * @author fengshuonan
     * @date 2020/11/29 11:19
     */
    @PostResource(name = "删除文件信息", path = "/sysFileInfo/delete")
    public ResponseData delete(@RequestBody @Validated(SysFileInfoRequest.delete.class) SysFileInfoRequest sysFileInfoRequest) {
        sysFileInfoService.delete(sysFileInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 文件预览
     * <p>
     * 支持通过文件id预览，也支持直接通过bucket和obj名称预览
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "文件预览", path = FILE_PREVIEW_URL)
    public void preview(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

        // 请求参数不能为空
        if (sysFileInfoRequest == null) {
            String userTip = StrUtil.format(FileExceptionEnum.PREVIEW_EMPTY_ERROR.getUserTip(), "");
            throw new FileException(FileExceptionEnum.PREVIEW_EMPTY_ERROR, userTip);
        }

        // 文件id不为空，则根据文件id预览
        if (ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileId())) {
            sysFileInfoService.previewByFileId(sysFileInfoRequest, response);
        }

        // 文件bucketName和objectName不为空，则根据bucket预览
        if (ObjectUtil.isAllNotEmpty(sysFileInfoRequest.getFileBucket(), sysFileInfoRequest.getFileObjectName())) {
            sysFileInfoService.previewByBucketAndObjName(sysFileInfoRequest, response);
        }

        // 提示用户信息不全
        String userTip = StrUtil.format(FileExceptionEnum.PREVIEW_EMPTY_ERROR.getUserTip(), sysFileInfoRequest);
        throw new FileException(FileExceptionEnum.PREVIEW_EMPTY_ERROR, userTip);
    }

    /**
     * 查看详情文件信息表
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "查看详情文件信息表", path = "/sysFileInfo/detail")
    public ResponseData detail(@Validated(SysFileInfoRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData(sysFileInfoService.detail(sysFileInfoRequest));
    }

    /**
     * 分页查询文件信息表
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "分页查询文件信息表", path = "/sysFileInfo/page")
    public ResponseData page(SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData(sysFileInfoService.page(sysFileInfoRequest));
    }

    /**
     * 获取全部文件信息表
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "获取全部文件信息表", path = "/sysFileInfo/list")
    public ResponseData list(SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData(sysFileInfoService.list(sysFileInfoRequest));
    }

}