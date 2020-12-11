package cn.stylefeng.roses.kernel.file.modular.service;

import cn.stylefeng.roses.kernel.file.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.pojo.response.SysFileInfoResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.modular.entity.SysFileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息表 服务类
 *
 * @author stylefeng
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file 要上传的文件
     * @return 文件id
     * @author fengshuonan
     * @date 2020/11/29 13:38
     */
    Long uploadFile(MultipartFile file);

    /**
     * 文件下载
     *
     * @param sysFileInfoRequest 文件下载参数
     * @param response           响应结果
     * @author fengshuonan
     * @date 2020/11/29 13:39
     */
    void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 删除文件信息表
     *
     * @param sysFileInfoRequest 删除参数
     * @author fengshuonan
     * @date 2020/11/29 13:44
     */
    void delete(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件预览，通过参数中传递文件id
     *
     * @param sysFileInfoRequest 文件预览参数
     * @param response           响应结果
     * @author fengshuonan
     * @date 2020/11/29 13:45
     */
    void previewByFileId(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 文件预览，通过参数中传递fileBucket和fileObjectName
     *
     * @param sysFileInfoRequest 文件预览参数
     * @param response           响应结果
     * @author fengshuonan
     * @date 2020/11/29 13:45
     */
    void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 查看详情文件信息表
     *
     * @param sysFileInfoRequest 查看参数
     * @return 文件信息
     * @author fengshuonan
     * @date 2020/11/29 14:08
     */
    SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 分页查询文件信息表
     *
     * @param sysFileInfoRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/29 14:09
     */
    PageResult<SysFileInfo> page(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 查询所有文件信息表
     *
     * @param sysFileInfoRequest 查询参数
     * @return 文件信息列表
     * @author fengshuonan
     * @date 2020/11/29 14:15
     */
    List<SysFileInfo> list(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件id
     * @return 文件信息结果集
     * @author fengshuonan
     * @date 2020/11/29 14:16
     */
    SysFileInfoResponse getFileInfoResult(Long fileId);

}
