package cn.stylefeng.roses.kernel.file.modular.controller;

import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.file.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.stylefeng.roses.kernel.file.constants.FileConstants.*;

/**
 * 文件信息管理
 * <p>
 * 该模块简要说明：
 * 1.文件支持版本朔源，每次操作均会产生数据
 * 2.文件支持版本回滚，升级后可选择某一版本进行回退
 * <p>
 * 文件管理接口有两种使用方式：
 * 1.合同文件场景：文件必须保持原样，合同内容升级不影响已签署合同，业务需要关联文件ID<br>
 * 文件升级不会对之前的数据造成影响
 * 2.UI文件场景：文件升级后业务所有关联的文件全部升级，业务需要关联文件CODE<br>
 * <p>
 * 可能你今天上线，昨天很丑的banner图变好看了~
 *
 * @author majianguo
 * @date 2020/12/27 13:39
 */
@RestController
@ApiResource(name = "文件信息相关接口")
public class SysFileInfoController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * 上传文件
     *
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @PostResource(name = "上传文件", path = "/sysFileInfo/upload")
    public ResponseData upload(@RequestPart("file") MultipartFile file, @Validated(SysFileInfoRequest.add.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.uploadFile(file, sysFileInfoRequest);
        return new SuccessResponseData(fileUploadInfoResult);
    }

    /**
     * 私有文件预览
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "私有文件预览", path = FILE_PRIVATE_PREVIEW_URL)
    public void privatePreview(@Validated(SysFileInfoRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.Y.getCode());
        this.sysFileInfoService.preview(sysFileInfoRequest, response);
    }

    /**
     * 公有文件预览
     *
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有文件预览", path = FILE_PUBLIC_PREVIEW_URL, requiredPermission = false, requiredLogin = false)
    public void publicPreview(@Validated(SysFileInfoRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N.getCode());
        this.sysFileInfoService.preview(sysFileInfoRequest, response);
    }

    /**
     * 通用文件预览，通过传bucket名称和object名称
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "文件预览，通过bucketName和objectName", path = FILE_PREVIEW_BY_OBJECT_NAME, requiredPermission = false)
    public void previewByBucketNameObjectName(@Validated(SysFileInfoRequest.previewByObjectName.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoService.previewByBucketAndObjName(sysFileInfoRequest, response);
    }

    /**
     * 私有文件下载
     *
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "私有文件下载", path = "/sysFileInfo/privateDownload")
    public void privateDownload(@Validated(SysFileInfoRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.Y.getCode());
        this.sysFileInfoService.download(sysFileInfoRequest, response);
    }

    /**
     * 公有文件下载
     *
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有文件下载", path = "/sysFileInfo/publicDownload", requiredLogin = false, requiredPermission = false)
    public void publicDownload(@Validated(SysFileInfoRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N.getCode());
        this.sysFileInfoService.download(sysFileInfoRequest, response);
    }

    /**
     * 替换文件
     * <p>
     * 注意：调用本接口之后还需要调用确认接口，本次替换操作才会生效
     *
     * @author majianguo
     * @date 2020/12/16 15:34
     */
    @PostResource(name = "替换文件", path = "/sysFileInfo/update")
    public ResponseData update(@RequestPart("file") MultipartFile file, @Validated(SysFileInfoRequest.edit.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.updateFile(file, sysFileInfoRequest);
        return new SuccessResponseData(fileUploadInfoResult);
    }

    /**
     * 版本回退
     *
     * @author majianguo
     * @date 2020/12/16 15:34
     */
    @PostResource(name = "替换文件", path = "/sysFileInfo/versionBack")
    public ResponseData versionBack(@Validated(SysFileInfoRequest.versionBack.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.versionBack(sysFileInfoRequest);
        return new SuccessResponseData(fileUploadInfoResult);
    }

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIds 附件IDS
     * @return 附件返回类
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "根据附件IDS查询附件信息", path = "/sysFileInfo/getFileInfoListByFileIds")
    public ResponseData getFileInfoListByFileIds(@RequestParam(value = "fileIds") String fileIds) {
        List<SysFileInfoResponse> list = this.sysFileInfoService.getFileInfoListByFileIds(fileIds);
        return new SuccessResponseData(list);
    }

    /**
     * 公有打包下载文件
     *
     * @author majianguo
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有打包下载文件", path = "/sysFileInfo/publicPackagingDownload", requiredPermission = false, requiredLogin = false)
    public void publicPackagingDownload(@RequestParam(value = "fileIds") String fileIds) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        this.sysFileInfoService.packagingDownload(fileIds, YesOrNotEnum.N.getCode(), response);
    }

    /**
     * 私有打包下载文件
     *
     * @author majianguo
     * @date 2020/12/27 13:18
     */
    @GetResource(name = "私有打包下载文件", path = "/sysFileInfo/privatePackagingDownload")
    public void privatePackagingDownload(@RequestParam(value = "fileIds") String fileIds) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        this.sysFileInfoService.packagingDownload(fileIds, YesOrNotEnum.Y.getCode(), response);
    }

    /**
     * 删除文件信息（真删除文件信息）
     *
     * @author fengshuonan
     * @date 2020/11/29 11:19
     */
    @PostResource(name = "删除文件信息（真删除文件信息）", path = "/sysFileInfo/deleteReally")
    public ResponseData deleteReally(@RequestBody @Validated(SysFileInfoRequest.delete.class) SysFileInfoRequest sysFileInfoRequest) {
        this.sysFileInfoService.deleteReally(sysFileInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 分页查询文件信息表
     *
     * @author fengshuonan
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "分页查询文件信息表", path = "/sysFileInfo/fileInfoListPage")
    public ResponseData fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData(this.sysFileInfoService.fileInfoListPage(sysFileInfoRequest));
    }

    /**
     * 确认替换附件
     * <p>
     * 在替换接口替换文件以后，需要调用本接口替换操作才会生效
     *
     * @author majianguo
     * @date 2020/12/27 13:18
     */
    @PostResource(name = "确认替换附件", path = "/sysFileInfo/confirmReplaceFile")
    public ResponseData confirmReplaceFile(@RequestBody List<Long> fileIdList) {
        this.sysFileInfoService.confirmReplaceFile(fileIdList);
        return new SuccessResponseData();
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

}